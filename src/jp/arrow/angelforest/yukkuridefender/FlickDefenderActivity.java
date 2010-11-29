package jp.arrow.angelforest.yukkuridefender;

import java.util.Date;

import com.admob.android.ads.AdManager;

import jp.angelforest.engine.util.SQLiteEngine;
import jp.arrow.angelforest.engine.core.AngelForestOpenGLActivity;
import jp.arrow.angelforest.engine.core.AngelforestRenderer;
import jp.arrow.angelforest.yukkuridefender.db.CurrentStatusTableAccess;
import jp.arrow.angelforest.yukkuridefender.db.ScoreHistory;
import jp.arrow.angelforest.yukkuridefender.db.ScoreHistoryTableAccess;
import jp.arrow.angelforest.yukkuridefender.pages.MainPage;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlickDefenderActivity extends AngelForestOpenGLActivity {
	private static CurrentStatusTableAccess currentStatusTableAccess;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//for admob test
//		AdManager.setTestDevices( new String[] { "1DB097E28D5E52D384EB6FEE9812FAD6" } );
		
		//get telephony info
		TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		GameParameters.UID = telManager.getSimSerialNumber();
		
		//get package name
		GameParameters.PACKAGE_NAME = this.getPackageName();
		
		//db init
		currentStatusTableAccess = new CurrentStatusTableAccess(this);
		
		//load current status
		String[][] currentData = currentStatusTableAccess.selectData();
		if (currentData.length > 0) {
			GameParameters.getInstance().currentTotalMoney = Integer.parseInt(currentData[0][1]);
			GameParameters.OPTION_BULLET_NUMBER = Integer.parseInt(currentData[0][2]);
			GameParameters.OPTION_BULLET_SIZE = Double.parseDouble(currentData[0][3]);
			GameParameters.OPTION_BULLET_POWER = Integer.parseInt(currentData[0][4]);
			GameParameters.OPTION_BULLET_LIFE = Integer.parseInt(currentData[0][5]);
			GameParameters.OPTION_LIFE = Integer.parseInt(currentData[0][6]);
		}
		
		//main
		MainPage.getInstance(this);
	}
	
	@Override
	public void initGame() {
		super.initGame();
		
		//init gameparameters
		//number
		GameParameters.getInstance().maxBulletAllowed = GameParameters.getInstance().getBulletNumByLevel() + GameParameters.OPTION_BULLET_NUMBER;
		//size
		GameParameters.getInstance().currentBulletSize = GameParameters.BULLET_SIZE_RATIO_ORIGINAL + (GameParameters.OPTION_BULLET_SIZE)/100d;
		//power
		GameParameters.getInstance().currentBulletPower = GameParameters.BULLET_POWER_ORIGINAL + GameParameters.OPTION_BULLET_POWER;
		//bulletlife
		GameParameters.getInstance().currentBulletLife = GameParameters.BULLET_LIFE_ORIGINAL + GameParameters.OPTION_BULLET_LIFE;
		//hp
		GameParameters.getInstance().currentHp = GameParameters.HP_ORIGINAL + GameParameters.OPTION_LIFE;
		
		//Enemy setting
		GameParameters.getInstance().currentEnemyHp = GameParameters.ENEMY_HP_BASE * GameParameters.getInstance().currentLvl;
		GameParameters.getInstance().currentEnemyStr = GameParameters.ENEMY_STR_BASE * GameParameters.getInstance().currentLvl;
	}

	@Override
	protected void setRenderer() {
		renderer = new FlickDefenderRenderer(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		//save status
		try {
			saveCurrentStatus();
		}
		catch(SQLException se) {
			Log.e(null, "couldn't find db!");
		}
		
		System.exit(1);
	}
	
	public static void saveCurrentStatus() {
		//save current status
		String[] data = new String[]{
				GameParameters.UID, 
				String.valueOf(GameParameters.getInstance().currentTotalMoney),
				String.valueOf(GameParameters.OPTION_BULLET_NUMBER),
				String.valueOf(GameParameters.OPTION_BULLET_SIZE),
				String.valueOf(GameParameters.OPTION_BULLET_POWER),
				String.valueOf(GameParameters.OPTION_BULLET_LIFE),
				String.valueOf(GameParameters.OPTION_LIFE)};
		currentStatusTableAccess.saveOrUpdateData(data);
	}
}