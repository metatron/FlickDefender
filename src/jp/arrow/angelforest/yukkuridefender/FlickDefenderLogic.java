package jp.arrow.angelforest.yukkuridefender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import jp.angelforest.engine.util.SizeConvertRatio;
import jp.arrow.angelforest.engine.core.TextureLoader;
import jp.arrow.angelforest.engine.core.TexturePolygon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class FlickDefenderLogic {
	private static FlickDefenderLogic logic;
	private static Context context;
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;

	public static final int GAME_READY = 0;
	public static final int GAME_STARTED = 1;
	public static final int GAME_OVER = 2;
	private static int status = GAME_READY;

//	private List<Enemy> enemyList = Collections.synchronizedList(new ArrayList<Enemy>());
//	private List<Bullet> bulletList = Collections.synchronizedList(new ArrayList<Bullet>());
//	private List<ExplosionChar> explodeList = Collections.synchronizedList(new ArrayList<ExplosionChar>());

	private List<Enemy> enemyList = new ArrayList<Enemy>();
	private List<Bullet> bulletList = new ArrayList<Bullet>();
	private List<ExplosionChar> explodeList = new ArrayList<ExplosionChar>();

	//for gradually increase the timing of enemy addition.
	public static int DEFAULT_LEVEL_TIMING = 50;
	public static int HIGHTEST_LEVEL_TIMING = 10;
	
	//for the game over detection, it uses millisecond method.
	public static long GAME_OVER_CLICK_ENABLED_TIMING = 1500l;
	private long gameOverStart = 0l;
	
	public static int ADD_ENEMY_TIMING = DEFAULT_LEVEL_TIMING;
	private int tick;

	private static Paint textPaint;
	private TexturePolygon textTexture;

	private static int hp = 0;
	private static int defended = 0;

	public static FlickDefenderLogic getInstance(Context context) {
		if (logic == null) {
			logic = new FlickDefenderLogic(context);
			textPaint = new Paint();
			textPaint.setColor(Color.MAGENTA);
		}

		return logic;
	}

	private FlickDefenderLogic(Context context) {
		FlickDefenderLogic.context = context;
		detectScreenWidthHeight();
	}

	private void detectScreenWidthHeight() {
		Display disp = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		SCREEN_WIDTH = disp.getWidth();
		SCREEN_HEIGHT = disp.getHeight();
	}

	// -------------- logic ----------------//
	public void init() {
		//clear lists
		enemyList.clear();
		bulletList.clear();
		explodeList.clear();
		textTexture = null;
		
		
		hp = GameParameters.getInstance().currentHp;
		defended = 0;
		status = GAME_STARTED; //GAME_READY;
		
		ADD_ENEMY_TIMING = DEFAULT_LEVEL_TIMING;
	}

	public void addEnemy(TexturePolygon textPoly) {
		if (tick % ADD_ENEMY_TIMING == 0) {
			// then add
			int itr = GameParameters.getInstance().currentLvl;
			synchronized (enemyList) {
				for(int i=0; i<itr; i++) {
					enemyList.add(new Enemy(textPoly, SizeConvertRatio.getRatio()));
				}
			}
		}
	}
	
	public void drawEnemies() {
		for (int i = 0; i < enemyList.size(); i++) {
			// for (Enemy enemy : enemyList) {
			Enemy enemy = enemyList.get(i);
			if (enemy != null && !enemy.isDead()) {
				enemy.draw();
				enemy.move();

				// detect dead
				detectHpDown(enemy);
				enemy.detectDead(context);
			} else {
//				synchronized (enemyList) {
					enemyList.remove(enemy);
//				}
			}
		}
	}

	public void addBullet(Bullet bullet) {
		// and add new bullet
		if(bulletList.size() < GameParameters.getInstance().maxBulletAllowed) {
//			synchronized (bulletList) {
				bulletList.add(bullet);
//			}
		}

		// Log.e(null, "size of bulletList: " + bulletList.size());
	}

	public void drawBullets() {
		for (int i = 0; i < bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);

			if (bullet != null && !bullet.isDead()) {
				bullet.draw();
				bullet.move();

				// detect dead
				bullet.detectDead(context);
			} else {
//				synchronized (bulletList) {
					bulletList.remove(bullet);
//				}
			}
		}
	}

	public void drawExplodes() {
		for (int i = 0; i < explodeList.size(); i++) {
			ExplosionChar explode = explodeList.get(i);
			// Log.e(null,"explode (" + i + ") status: " + explode.getStatus() +
			// "; x: " + explode.getX() + "y: " + explode.getY());

			if (explode != null && !explode.isFinished()) {
				explode.draw();
				explode.incrementPos();
			} else {
//				synchronized (explodeList) {
					explodeList.remove(explode);
//				}
			}
		}
	}

	public void addExplode(ExplosionChar explode) {
//		synchronized (explodeList) {
			// after the removal add new one
			explodeList.add(explode);
//		}
	}

	public void detectCollision(Bullet bullet) {
		for(int i=0; i<enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			if (enemy != null && !enemy.isDead()) {
//				boolean iscollide = squareCollisionLogic(enemy, bullet);
				boolean iscollide = circularCollisionLogic(enemy, bullet);
				if (iscollide) {
					// Log.e(null, "collision!");
					// set deads and decrease timing
					bulletEnemyHpDown(bullet, enemy);
//					enemy.setDead(true);
//					bullet.setDead(true);

					// add explode animation
					FlickDefenderLogic.getInstance(context).addExplode(
							new ExplosionChar(context, (int) enemy.getX(),
									(int) enemy.getY()));

					// increment score
					defended++;

					// make the game lvl harder
					if (ADD_ENEMY_TIMING > HIGHTEST_LEVEL_TIMING) {
						ADD_ENEMY_TIMING--;
					}
				}
			}

		}
	}

	/**
	 * for multiple bullet detection, use this method. the bullet will be added
	 * when user touch the screen.
	 * 
	 */
	public void detectCollisionPerBullet() {
//		for (Bullet bullet : bulletList) {
		for (int i=0; i<bulletList.size(); i++) {
			Bullet bullet = bulletList.get(i);
			if (bullet != null && !bullet.isDead()) {
				detectCollision(bullet);
			}
		}
	}

	/**
	 * square collision detection logic. change or add collition detection
	 * logic, such as circular, and change detectColligion method.
	 * 
	 * @param enemy
	 * @param bullet
	 * @return
	 */
	public boolean squareCollisionLogic(Enemy enemy, Bullet bullet) {
		float enemyLeft = enemy.getX() - enemy.getW();
		float enemyRight = enemy.getX() + enemy.getW();
		float enemyTop = enemy.getY() - enemy.getH();
		float enemyBottom = enemy.getY() + enemy.getH();

		float bulletLeft = bullet.getX() - bullet.getW();
		float bulletRight = bullet.getX() + bullet.getW();
		float bulletTop = bullet.getY() - bullet.getH();
		float bulletBottom = bullet.getY() + bullet.getH();

		// testsquare.draw((int)bulletLeft, (int)bulletTop, 1, 1, 0);

		boolean iscollide = enemyLeft < bulletRight && bulletLeft < enemyRight
				&& bulletTop < enemyBottom && enemyTop < bulletBottom;
		// Log.e(null, "bullet: " + bullet.getX() + ", " + bullet.getY()
		// + " enemy: " + enemy.getX() + ", " + enemy.getY() + " :: " +
		// iscollide);

		return iscollide;
	}
	
	public boolean circularCollisionLogic(Enemy enemy, Bullet bullet) {
		if(Math.pow(enemy.getX()-bullet.getX(),2)+Math.pow(enemy.getY()-bullet.getY(),2) < Math.pow(enemy.getR()+bullet.getR(), 2)) {
			return true;
		}
		return false;
	}
	
	public void bulletEnemyHpDown(Bullet bullet, Enemy enemy) {
		int enemyRemHp = enemy.hp - bullet.str;
		int bulletRemHp = bullet.hp - enemy.str;
		
		if (enemyRemHp <= 0) {
			//death
			enemy.setDead(true);
			// get money
			GameParameters.getInstance().currentTotalScore += enemy.getScore();
		}
		else {
			enemy.hp = enemyRemHp;
		}
		
		if (bulletRemHp <= 0) {
			bullet.setDead(true);
		}
		else {
			bullet.hp = bulletRemHp;
		}
	}

	private void detectHpDown(Enemy enemy) {
		if (enemy.getX() > 0 && enemy.getX() < FlickDefenderLogic.SCREEN_WIDTH
				&& enemy.getY() > FlickDefenderLogic.SCREEN_HEIGHT) {
			hp -= enemy.getStr();

			if (hp <= 0) {
				status = GAME_OVER;
				gameOverStart = System.currentTimeMillis();
			}
		}
	}

	public void displayText(GL10 gl) {
		switch (status) {
		case GAME_STARTED:
			displayHP(gl);
			break;
		case GAME_OVER:
			displayGameOver(gl);
			break;
		}
	}

	public synchronized void displayGameOver(GL10 gl) {
		String tickStr = "GAME OVER";
		int w = (int) textPaint.measureText(tickStr);
		int h = (int) (textPaint.descent() - textPaint.ascent());
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawText(tickStr, 0, (int) -textPaint.ascent(), textPaint);
		// load text
		textTexture = new TexturePolygon(context, bitmap);
		textTexture.draw(FlickDefenderLogic.SCREEN_WIDTH / 2,
				FlickDefenderLogic.SCREEN_HEIGHT / 2);
		if(textTexture != null) {
			textTexture.delete();
		}
	}
	
	public boolean checkForRestartGame() {
		if(System.currentTimeMillis()-gameOverStart >= GAME_OVER_CLICK_ENABLED_TIMING) {
			return true;
		}
		return false;
	}

	public void displayHP(GL10 gl) {
		String tickStr = "HP: " + hp;
		String scoreStr = "defended: " + defended;

		int w = (int) textPaint.measureText(tickStr);
		int w2 = (int) textPaint.measureText(scoreStr);

		if (w2 >= w) {
			w = w2;
		}

		int h = (int) (textPaint.descent() - 2 * textPaint.ascent());
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawText(tickStr, 0, (int) -textPaint.ascent(), textPaint);
		canvas.drawText("defended: " + defended, 0, (int) -2
				* textPaint.ascent(), textPaint);

		// load text
		textTexture = new TexturePolygon(context, bitmap);
		textTexture.draw(40, 20);
		textTexture.delete();
	}
	
	public void tickTimer() {
		tick++;

		// if(ADD_ENEMY_TIMING > 1) {
		// ADD_ENEMY_TIMING--;
		// }
	}

	public static int getStatus() {
		return status;
	}

	public static void setStatus(int status) {
		FlickDefenderLogic.status = status;
	}
}
