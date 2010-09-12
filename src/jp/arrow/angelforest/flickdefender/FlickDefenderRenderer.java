package jp.arrow.angelforest.flickdefender;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.net.wifi.WifiConfiguration.Status;
import android.util.Log;
import android.view.MotionEvent;
import jp.angelforest.engine.util.SizeConvertRatio;
import jp.arrow.angelforest.engine.core.AngelforestRenderer;
import jp.arrow.angelforest.engine.core.TexturePolygon;

public class FlickDefenderRenderer extends AngelforestRenderer {
	private float startX;
	private float startY;

	private Bullet bullet;
	private Context context;
	
	private HashMap<Integer, TexturePolygon> textPolyMap = new HashMap<Integer, TexturePolygon>();

	public FlickDefenderRenderer(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void draw(GL10 gl) {
		// TODO text display is very fuckin heavy!!
		FlickDefenderLogic.getInstance(context).displayText(gl);

		if (FlickDefenderLogic.getStatus() == FlickDefenderLogic.GAME_STARTED) {
			// FlickDefenderLogic.getInstance(context).detectCollision(bullet);
			FlickDefenderLogic.getInstance(context).detectCollisionPerBullet();
			FlickDefenderLogic.getInstance(context).addEnemy(chooseEnemyTexture());
			FlickDefenderLogic.getInstance(context).drawEnemies();

			FlickDefenderLogic.getInstance(context).drawBullets();

			FlickDefenderLogic.getInstance(context).drawExplodes();

			FlickDefenderLogic.getInstance(context).tickTimer();
		}

		try {
			Thread.sleep(AngelforestRenderer.GAME_REFRESHRATE);
		} catch (InterruptedException e) {
		}
	}
	
	private TexturePolygon chooseEnemyTexture() {
		if(System.currentTimeMillis()%2 == 0) {
			return textPolyMap.get(R.drawable.yukkuri_reimu);
		}
		return textPolyMap.get(R.drawable.yukkuri_marisa);
	}

	/**
	 * Initialize all the textures such as bullet, enemies.
	 * 
	 */
	@Override
	public void initTextures(GL10 gl) {
		//init texture map
		textPolyMap.put(R.drawable.bulletball, new TexturePolygon(context, R.drawable.bulletball));
		textPolyMap.put(R.drawable.yukkuri_reimu, new TexturePolygon(context, R.drawable.yukkuri_reimu));
		textPolyMap.put(R.drawable.yukkuri_marisa, new TexturePolygon(context, R.drawable.yukkuri_marisa));
		
		bullet = new Bullet(textPolyMap.get(R.drawable.bulletball), SizeConvertRatio.getRatio());
		FlickDefenderLogic.getInstance(context).init();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return onTouchDownEvent(event);

		case MotionEvent.ACTION_UP:
			return onTouchUpEvent(event);

		case MotionEvent.ACTION_MOVE:
			return onTouchDragEvent(event);
		}

		return true;
	}

	public boolean onTouchDownEvent(MotionEvent event) {
		if (FlickDefenderLogic.getStatus() != FlickDefenderLogic.GAME_STARTED) {
			FlickDefenderLogic.getInstance(context).init();
			FlickDefenderLogic.setStatus(FlickDefenderLogic.GAME_STARTED);
			return true;
		}

		startX = event.getX();
		startY = event.getY();

		bullet = new Bullet(textPolyMap.get(R.drawable.bulletball), SizeConvertRatio.getRatio());
		bullet.reset();
		bullet.setX(startX);
		bullet.setY(startY);

		FlickDefenderLogic.getInstance(context).addBullet(bullet);

		return true;
	}

	public boolean onTouchDragEvent(MotionEvent event) {
		return true;
	}

	public boolean onTouchUpEvent(MotionEvent event) {
		// calculate the velocity
		double time = event.getEventTime() - event.getDownTime();
		time /= 100;
		// Log.e(null, "down time: " + time);

		// Log.e(null, "start: " + startX + ", " + startY + " end: " +
		// event.getX() + ", " + event.getY());

		double dist = Math.sqrt(Math.pow(event.getX() - startX, 2)
				+ Math.pow(event.getY() - startY, 2));
		double velocity = dist / time;
		// Log.e(null, "dist: " + dist + "velo: " + velocity);

		double rad = Math.atan2(event.getY() - startY, event.getX() - startX);
		// double rad = Math.atan((event.getY()-startY)/(event.getX()-startX));
		double fdeg = (Math.toDegrees(rad) + 360) % 360;
		double frad = (rad + 2 * Math.PI) % (2 * Math.PI);
		// Log.e(null, "degree: " + fdeg + ", " + frad);

		double vx = velocity * Math.cos(frad);
		double vy = velocity * Math.sin(frad);
		// Log.e(null, "vxy: " + vx + ", " + vy);

		// set velocity to touched character
		bullet.setVx((int) vx);
		bullet.setVy((int) vy);
		// flickingCharacter.setVx((float)vx);
		// flickingCharacter.setVy((float)vy);
		//
		// //start flick
		// if(vx > 0 || vy > 0) {
		// flickingCharacter.setStatus(FlickCharacter.STATUS_FLICK_START);
		// }

		return true;
	}

	public HashMap<Integer, TexturePolygon> getTextPolyMap() {
		return textPolyMap;
	}
}
