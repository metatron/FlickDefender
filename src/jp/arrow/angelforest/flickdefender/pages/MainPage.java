package jp.arrow.angelforest.flickdefender.pages;

import jp.arrow.angelforest.engine.core.AngelForestOpenGLActivity;
import jp.arrow.angelforest.flickdefender.R;
import jp.arrow.angelforest.flickdefender.R.id;
import jp.arrow.angelforest.flickdefender.R.layout;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPage {
	public static MainPage getInstance(Activity activity) {
		activity.setContentView(R.layout.main);
		return new MainPage(activity);
	}
	
	public MainPage(final Activity activity) {
		//start button pressed
		Button startBtn = (Button)(activity.findViewById(R.id.Button01));
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//start the game
				((AngelForestOpenGLActivity)(activity)).initGame();
			}
		});
		
		//option
		Button optionBtn = (Button)(activity.findViewById(R.id.Button02));
		optionBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//goto option screen
				OptionPage.getInstance(activity);
			}
		});
	}
}
