package jp.arrow.angelforest.yukkuridefender;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import jp.arrow.angelforest.engine.core.SquarePolygon;
import jp.arrow.angelforest.engine.core.TexturePolygon;

public class Bullet extends CharacterStatus {
	protected int w;
	protected int h;
	
	//for collision detection
	protected int r;

	protected float x;
	protected float y;
	protected float vx;
	protected float vy;

//	private SquarePolygon square;
	protected TexturePolygon textPoly = null;

	protected boolean isDead = false;

	public Bullet(TexturePolygon textPoly, double[] enlargeRatio) {
		// for temporary
//		square = new SquarePolygon();
		w = 10;
		h = 10;
		w = (int)(w*enlargeRatio[0]);
		h = (int)(h*enlargeRatio[1]);
		
		r = 13;
		if(enlargeRatio[0] < enlargeRatio[1]) {
			r = (int)(r*enlargeRatio[0]);
		}
		else {
			r = (int)(r*enlargeRatio[1]);
		}
		
		//resize it
		w = (int)(w*GameParameters.getInstance().currentBulletSize);
		h = (int)(h*GameParameters.getInstance().currentBulletSize);
		r = (int)(r*GameParameters.getInstance().currentBulletSize);

		// loading texture
		this.textPoly = textPoly; //new TexturePolygon(context, R.drawable.bulletball);
		
		//init hp and str
		setOriginal(GameParameters.getInstance().currentBulletLife, GameParameters.getInstance().currentBulletPower);
	}

	public void draw() {
		if (!isDead) {
//			textPoly.draw((int)x, (int)y);
			textPoly.draw((int)x, (int)y, (float)GameParameters.getInstance().currentBulletSize, (float)GameParameters.getInstance().currentBulletSize, 0.0f);			
//			square.draw((int) x, (int) y, w, h, 0);
		}
	}

	public void move() {
		if (!isDead) {
			x += vx;
			y += vy;
		}
	}

	public void reset() {
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
		isDead = false;
		
		//init status
		initCharStatus();
	}

	public synchronized void detectDead(Context context) {
		if ((x > FlickDefenderLogic.SCREEN_WIDTH || y > FlickDefenderLogic.SCREEN_HEIGHT)
				|| (x < 0 || y < 0)) {
			isDead = true;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getR() {
		return r;
	}
}
