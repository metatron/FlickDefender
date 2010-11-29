package jp.arrow.angelforest.yukkuridefender;

import java.util.RandomAccess;

import jp.arrow.angelforest.engine.core.TexturePolygon;

import android.content.Context;
import android.util.Log;

public class Enemy extends Bullet {
	public float MAX_ACCEL = 25.0f;
	private float accel;

	private float goalX;
	private float goalY;
	
	protected int score=GameParameters.ENEMY_SCORE;

	public Enemy(TexturePolygon textPoly, double[] enlargeRatio) {
		super(textPoly, enlargeRatio);
		w = 15;
		h = 15;
		w = (int)(w*enlargeRatio[0]);
		h = (int)(h*enlargeRatio[1]);
		
		r = 20;
		if(enlargeRatio[0] < enlargeRatio[1]) {
			r = (int)(r*enlargeRatio[0]);
		}
		else {
			r = (int)(r*enlargeRatio[1]);
		}
		
		reset();
	}
	
	@Override
	public void draw() {
		if (!isDead) {
			textPoly.draw((int)x, (int)y);
//			square.draw((int) x, (int) y, w, h, 0);
		}
	}


	@Override
	public void reset() {
		super.reset();

		x = (float) ((FlickDefenderLogic.SCREEN_WIDTH - 1) * Math.random() + 1);
		y = 10.0f * (float) Math.random();
		goalX = (float) ((FlickDefenderLogic.SCREEN_WIDTH - 1) * Math.random() + 1);
		goalY = (float) ((100) * Math.random() + (FlickDefenderLogic.SCREEN_WIDTH-100));

		accel = (float) ((MAX_ACCEL - 1) * Math.random() + 1);

		double rad = Math.atan2(goalY - y, goalX - x);
		double frad = (rad + 2 * Math.PI) % (2 * Math.PI);

		vx = (float) (accel * Math.cos(frad));
		vy = (float) (accel * Math.sin(frad));
		
		//init hp and str
		setOriginal(GameParameters.getInstance().currentEnemyHp, GameParameters.getInstance().currentEnemyStr);
		
		isDead = false;
	}
	
	public int getScore() {
		return score;
	}
}
