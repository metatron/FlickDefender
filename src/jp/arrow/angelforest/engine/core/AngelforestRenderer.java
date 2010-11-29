package jp.arrow.angelforest.engine.core;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Abstract class that is used in AngelForestOpenGLActivity. This class will be
 * attached to Activity by calling setRenderer at AngelForestOpenGLActivity.
 * 
 * Programmer must implement draw method for main screen activity, and touch
 * activity.
 * 
 * @author horikawa_yoji
 * 
 */
public abstract class AngelforestRenderer implements Renderer {
	public static int GAME_REFRESHRATE = 25;
	private Context context;

	public AngelforestRenderer(Context context) {
		this.context = context;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig eglconf) {
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		AngelForest2DEngine.init(gl, width, height);
		initTextures(gl);

		System.gc();
	}
	
	/**
	 * If theres any textures used in the application, use this method to
	 * initialize them. otherwise, NullPointerException will occuer.
	 * 
	 * @param gl
	 */
	public abstract void initTextures(GL10 gl);

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		draw(gl);

		try {
			Thread.sleep(GAME_REFRESHRATE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * When implementing a game, this method will do all the drawings. Think it
	 * as a main screen.
	 * 
	 * @param gl
	 */
	public abstract void draw(GL10 gl);

	/**
	 * This method will handle all the touch event. TouchUp, TouchDown event
	 * will be passed from Activity class.
	 * 
	 * @param event
	 *            MotionEvent
	 * @return
	 */
	public abstract boolean onTouchEvent(MotionEvent event);
	
	/**
	 * This method will handle all the key event.
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 */
	public abstract boolean onKeyDown(int keyCode, KeyEvent event);

	public Context getContext() {
		return context;
	}
}
