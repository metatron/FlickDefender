package jp.arrow.angelforest.flickdefender;

import jp.arrow.angelforest.engine.core.AngelForestOpenGLActivity;
import jp.arrow.angelforest.engine.core.AngelforestRenderer;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class FlickDefenderActivity extends AngelForestOpenGLActivity {

	@Override
	protected void setRenderer() {
		renderer = new FlickDefenderRenderer(this);
	}
}