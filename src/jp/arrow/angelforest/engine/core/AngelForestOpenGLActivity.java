package jp.arrow.angelforest.engine.core;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Abstract Activity for AngelForestOpenGL Engine. Those app that wants to use
 * AngelForestOpenGL Engine must be extended from this class.
 * 
 * The only thing extended class must do is to create Renderer class, and set it
 * at setRenderer method.
 * 
 * @author horikawa_yoji
 * 
 */
public abstract class AngelForestOpenGLActivity extends Activity {
	private GLSurfaceView mGLSurfaceView;

	protected AngelforestRenderer renderer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// full screen
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mGLSurfaceView = new GLSurfaceView(this);

		// renderer = new FlickDefenderRenderer(this);
		setRenderer();

		mGLSurfaceView.setRenderer(renderer);

		setContentView(mGLSurfaceView);

	}

	/**
	 * set renderer. renderer must be implemented with AngelforestRenderer.
	 * 
	 */
	protected abstract void setRenderer();

	@Override
	protected void onResume() {
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(1);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLSurfaceView.onPause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return renderer.onTouchEvent(event);
	}
}
