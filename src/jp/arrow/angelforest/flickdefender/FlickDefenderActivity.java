package jp.arrow.angelforest.flickdefender;

import jp.arrow.angelforest.engine.core.AngelforestRenderer;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class FlickDefenderActivity extends Activity {
    private GLSurfaceView mGLSurfaceView;

    private AngelforestRenderer renderer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mGLSurfaceView = new GLSurfaceView(this);

        renderer = new FlickDefenderRenderer(this);
        mGLSurfaceView.setRenderer(renderer);

        setContentView(mGLSurfaceView);

    }

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
    }}