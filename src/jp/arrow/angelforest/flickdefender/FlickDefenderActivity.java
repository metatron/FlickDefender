package jp.arrow.angelforest.flickdefender;

import jp.arrow.angelforest.engine.core.AngelForestOpenGLActivity;
import jp.arrow.angelforest.engine.core.AngelforestRenderer;
import jp.arrow.angelforest.flickdefender.pages.MainPage;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlickDefenderActivity extends AngelForestOpenGLActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainPage.getInstance(this);
	}

	@Override
	protected void setRenderer() {
		renderer = new FlickDefenderRenderer(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		System.exit(1);
	}
}