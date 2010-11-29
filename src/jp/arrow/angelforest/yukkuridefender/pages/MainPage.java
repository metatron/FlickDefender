package jp.arrow.angelforest.yukkuridefender.pages;

import jp.arrow.angelforest.engine.core.AngelForestOpenGLActivity;
import jp.arrow.angelforest.yukkuridefender.R;
import jp.arrow.angelforest.yukkuridefender.R.id;
import jp.arrow.angelforest.yukkuridefender.R.layout;
import jp.arrow.angelforest.yukkuridefender.FlickDefenderActivity;
import jp.arrow.angelforest.yukkuridefender.GameParameters;
import android.app.Activity;
import android.util.Log;
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
		Button startBtn = (Button)(activity.findViewById(R.id.StartGameButton));
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//start the game
				((FlickDefenderActivity)(activity)).initGame();
			}
		});
		
		//shop
		Button shopBtn = (Button)(activity.findViewById(R.id.StartShopButton));
		shopBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//goto shop screen
//				Log.e(null, "score: " + GameParameters.getInstance().currentTotalScore);
				ShopPage.getInstance(activity);
			}
		});
		
		//option
		Button optionBtn = (Button)(activity.findViewById(R.id.StartOptionButton));
		optionBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//goto option screen
				OptionPage.getInstance(activity);
			}
		});
	}
}
