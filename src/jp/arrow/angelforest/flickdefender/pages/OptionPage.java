package jp.arrow.angelforest.flickdefender.pages;

import jp.arrow.angelforest.flickdefender.GameParameters;
import jp.arrow.angelforest.flickdefender.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

public class OptionPage {
	public static OptionPage getInstance(Activity activity) {
		activity.setContentView(R.layout.option);
		return new OptionPage(activity);
	}
	
	public OptionPage(final Activity activity) {
		//Radiobuttons
		
		//Normal Lvl
		RadioButton normalButton = (RadioButton)(activity.findViewById(R.id.NormalRadioButton));
		normalButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameParameters.getInstance().currentLvl = GameParameters.GAME_LVL_NORMAL;
				GameParameters.getInstance().maxBulletAllowed = GameParameters.BULLET_MAX_NORMAL;
			}
		});
		
		//Hard Lvl
		RadioButton hardButton = (RadioButton)(activity.findViewById(R.id.HardRadioButton));
		hardButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameParameters.getInstance().currentLvl = GameParameters.GAME_LVL_HARD;
				GameParameters.getInstance().maxBulletAllowed = GameParameters.BULLET_MAX_HARD;
			}
		});
		
		//Hardest Lvl
		RadioButton hardestButton = (RadioButton)(activity.findViewById(R.id.HardestRadioButton));
		hardestButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameParameters.getInstance().currentLvl = GameParameters.GAME_LVL_HARDEST;
				GameParameters.getInstance().maxBulletAllowed = GameParameters.BULLET_MAX_HARDEST;
			}
		});

		//Lunatic Lvl
		RadioButton lunaticButton = (RadioButton)(activity.findViewById(R.id.LunaticRadioButton));
		lunaticButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameParameters.getInstance().currentLvl = GameParameters.GAME_LVL_LUNATIC;
				GameParameters.getInstance().maxBulletAllowed = GameParameters.BULLET_MAX_LUNATIC;
			}
		});
		
		//ok button pressed
		Button okBtn = (Button)(activity.findViewById(R.id.OptionOKButton));
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainPage.getInstance(activity);
			}
		});
	}
}
