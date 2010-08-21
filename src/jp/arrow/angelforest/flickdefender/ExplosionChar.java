package jp.arrow.angelforest.flickdefender;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.hardware.Camera.Size;

import jp.arrow.angelforest.engine.core.TexturePolygon;

public class ExplosionChar {
	public static final int EXPLOSION_INIT = 0;
	public static final int EXPLOSION_STARTED = 1;
	public static final int EXPLOSION_FINISHED = 2;

	private static ArrayList<TexturePolygon> textureList = null;
	private static int textureNum;
	private int currentPos = 0;
	private int status = EXPLOSION_INIT;
	private int x;
	private int y;

	public ExplosionChar(Context context, int x, int y) {
		if (textureList == null) {
			textureList = new ArrayList<TexturePolygon>();
			textureList.add(new TexturePolygon(context, R.drawable.explode_0));
			textureList.add(new TexturePolygon(context, R.drawable.explode_1));
			textureList.add(new TexturePolygon(context, R.drawable.explode_2));
			textureList.add(new TexturePolygon(context, R.drawable.explode_3));
			textureList.add(new TexturePolygon(context, R.drawable.explode_4));
			textureList.add(new TexturePolygon(context, R.drawable.explode_5));
			textureNum = textureList.size();
		}
		status = EXPLOSION_INIT;
		this.x = x;
		this.y = y;
		currentPos = 0;
	}

	public synchronized void draw() {
		if (status != EXPLOSION_FINISHED) {
			textureList.get(currentPos).draw(x, y);
		}
	}

	public void incrementPos() {
		if (currentPos < textureList.size() - 1) {
			currentPos++;
			status = EXPLOSION_STARTED;
		} else {
			status = EXPLOSION_FINISHED;
		}
	}

	public boolean isFinished() {
		if (status == EXPLOSION_FINISHED) {
			return true;
		}
		return false;
	}

	public int getStatus() {
		return status;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCurrentPos() {
		return currentPos;
	}
}
