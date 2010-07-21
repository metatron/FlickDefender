package jp.arrow.angelforest.engine.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.MotionEvent;

public class AngelforestRenderer implements Renderer {
	public static final int GAME_REFRESHRATE = 1000;
	private SquarePolygon square = new SquarePolygon();
	
	public AngelforestRenderer(Context context) {
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig eglconf) {
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		AngelForest2DEngine.init(gl, width, height);
		System.gc();
	}
	
	int count=5;
	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		square.draw(10, 10, 10, 10, 30);
//		Log.e(null, "count: " + count);
		
		
		try {
			Thread.sleep(GAME_REFRESHRATE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
}
