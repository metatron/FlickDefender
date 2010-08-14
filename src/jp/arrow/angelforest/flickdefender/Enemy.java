package jp.arrow.angelforest.flickdefender;

import java.util.RandomAccess;

import android.util.Log;

public class Enemy extends Bullet {
	public float MAX_ACCEL = 25.0f;
	private float accel;

	private float goalX;
	private float goalY;

	public Enemy() {
		reset();
	}

	@Override
	public void reset() {
		super.reset();

		x = (float) ((FlickDefenderLogic.SCREEN_WIDTH - 1) * Math.random() + 1);
		y = 10.0f * (float) Math.random();
		goalX = (float) ((FlickDefenderLogic.SCREEN_WIDTH - 1) * Math.random() + 1);
		goalY = (float) ((100) * Math.random() + 400);

		accel = (float) ((MAX_ACCEL - 1) * Math.random() + 1);

		double rad = Math.atan2(goalY - y, goalX - x);
		double frad = (rad + 2 * Math.PI) % (2 * Math.PI);

		vx = (float) (accel * Math.cos(frad));
		vy = (float) (accel * Math.sin(frad));

		isDead = false;
	}
}
