package jp.arrow.angelforest.yukkuridefender.pages;

import java.io.File;

import jp.arrow.angelforest.yukkuridefender.R;
import jp.arrow.angelforest.yukkuridefender.FlickDefenderActivity;
import jp.arrow.angelforest.yukkuridefender.GameParameters;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
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
		
		//Reset status button
		Button resetStatusButton = (Button)(activity.findViewById(R.id.ButtonStatusReset));
		resetStatusButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GameParameters.getInstance().currentTotalMoney = 0;
				GameParameters.OPTION_BULLET_NUMBER = 0;
				GameParameters.OPTION_BULLET_SIZE = 0.0d;
				GameParameters.OPTION_BULLET_POWER = 0;
				GameParameters.OPTION_BULLET_LIFE = 0;
				GameParameters.OPTION_LIFE = 0;
				//save status
				String db_dir = Environment.getDataDirectory() + "/data/" + GameParameters.PACKAGE_NAME + "/databases/";
				File db_file = new File(db_dir);
				db_file.delete();
//				FlickDefenderActivity.saveCurrentStatus();
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
