package jp.arrow.angelforest.yukkuridefender.pages;

import jp.arrow.angelforest.yukkuridefender.R;
import jp.arrow.angelforest.yukkuridefender.GameParameters;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShopPage {
	private static double size = 0;
	private static int number = 0;
	private static int power = 0;
	private static int bulletLife = 0;
	private static int life = 0;
	private static int money = 0;
	private static int originalTotalMoney = 0;
	
	public static ShopPage getInstance(Activity activity) {
		activity.setContentView(R.layout.shop);
		
		return new ShopPage(activity);
	}
	
	private void init(Activity activity) {
		//current money
		money = GameParameters.getInstance().currentTotalMoney + GameParameters.getInstance().currentTotalScore;
		originalTotalMoney = money;
		TextView moneyText = (TextView)(activity.findViewById(R.id.TextViewTotalMoney));
		moneyText.setText(String.valueOf(money));
		//reset currentTotalScore
		GameParameters.getInstance().currentTotalScore = 0;
		
		//number text
		number = GameParameters.OPTION_BULLET_NUMBER;
		TextView numberText = (TextView)(activity.findViewById(R.id.TextViewBulletNumber));
		numberText.setText(String.valueOf(number));
		
		//size text
		size = GameParameters.OPTION_BULLET_SIZE;
		TextView sizeText = (TextView)(activity.findViewById(R.id.TextViewBulletSize));
		sizeText.setText(String.valueOf(size));
		
		//power text
		power = GameParameters.OPTION_BULLET_POWER;
		TextView powerText = (TextView)(activity.findViewById(R.id.TextViewBulletPower));
		powerText.setText(String.valueOf(power));

		//bulletLife text
		bulletLife = GameParameters.OPTION_BULLET_LIFE;
		TextView bulletLifeText = (TextView)(activity.findViewById(R.id.TextViewBulletLife));
		bulletLifeText.setText(String.valueOf(bulletLife));
		
		//power text
		life = GameParameters.OPTION_LIFE;
		TextView lifeText = (TextView)(activity.findViewById(R.id.TextViewLife));
		lifeText.setText(String.valueOf(life));
	}
	
	public ShopPage(final Activity activity) {
		//init
		init(activity);
		
		//Adding Bullet Number
		Button addBulletNumberButton = (Button)(activity.findViewById(R.id.ButtonAddBulletNumber));
		addBulletNumberButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (money >= GameParameters.OPTION_BULLET_NUMBER_PRICE &&
						GameParameters.OPTION_BULLET_NUMBER < GameParameters.OPTION_BULLET_NUMBER_MAX) {
					//add number
					number++;
					//decrement currenTotalScore
					money -= GameParameters.OPTION_BULLET_NUMBER_PRICE;
					//set textview
					TextView numberText = (TextView)(activity.findViewById(R.id.TextViewBulletNumber));
					numberText.setText(String.valueOf(number));
					
					//set money text
					ShopPage.setMoneyTextView(activity, money);
				}
			}
		});
		
		//Adding Bullet Size
		Button addBulletButtonSize = (Button)(activity.findViewById(R.id.ButtonAddBulletSize));
		addBulletButtonSize.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (money >= GameParameters.OPTION_BULLET_SIZE_PRICE &&
						GameParameters.OPTION_BULLET_SIZE < GameParameters.OPTION_BULLET_SIZE_MAX) {
					//add size
					size++;
					//decrement currenTotalScore
					money -= GameParameters.OPTION_BULLET_SIZE_PRICE;
					//set textview
					TextView sizeText = (TextView)(activity.findViewById(R.id.TextViewBulletSize));
					sizeText.setText(String.valueOf(size));
					
					//set money text
					ShopPage.setMoneyTextView(activity, money);
				}
			}
		});
		
		//Adding Bullet Power
		Button addBulletButtonPower = (Button)(activity.findViewById(R.id.ButtonAddBulletPower));
		addBulletButtonPower.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (money >= GameParameters.OPTION_BULLET_POWER_PRICE &&
						GameParameters.OPTION_BULLET_POWER < GameParameters.OPTION_BULLET_POWER_MAX) {
					//add power
					power++;
					//decrement currenTotalScore
					money -= GameParameters.OPTION_BULLET_POWER_PRICE;
					//set textview
					TextView powerText = (TextView)(activity.findViewById(R.id.TextViewBulletPower));
					powerText.setText(String.valueOf(power));
					
					//set money text
					ShopPage.setMoneyTextView(activity, money);
				}
			}
		});
		
		//Adding Bullet Life
		Button addBulletButtonLife = (Button)(activity.findViewById(R.id.ButtonAddBulletLife));
		addBulletButtonLife.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (money >= GameParameters.OPTION_BULLET_LIFE_PRICE &&
						GameParameters.OPTION_BULLET_LIFE < GameParameters.OPTION_BULLET_LIFE_MAX) {
					//add power
					bulletLife++;
					//decrement currenTotalScore
					money -= GameParameters.OPTION_BULLET_LIFE_PRICE;
					//set textview
					TextView bulletLifeText = (TextView)(activity.findViewById(R.id.TextViewBulletLife));
					bulletLifeText.setText(String.valueOf(bulletLife));
					
					//set money text
					ShopPage.setMoneyTextView(activity, money);
				}
			}
		});
		
		//Adding life
		Button addLifeButton = (Button)(activity.findViewById(R.id.ButtonAddLife));
		addLifeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (money >= GameParameters.OPTION_LIFE_PRICE &&
						GameParameters.OPTION_LIFE < GameParameters.OPTION_LIFE_MAX) {
					//add life
					life++;
					//decrement currenTotalScore
					money -= GameParameters.OPTION_LIFE_PRICE;
					//set textview
					TextView lifeText = (TextView)(activity.findViewById(R.id.TextViewLife));
					lifeText.setText(String.valueOf(life));
					
					//set money text
					ShopPage.setMoneyTextView(activity, money);
				}
			}
		});

		//OK button
		Button addBulletShopOK = (Button)(activity.findViewById(R.id.ButtonShopOK));
		addBulletShopOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//set current status
				GameParameters.OPTION_BULLET_NUMBER = number;
				GameParameters.OPTION_BULLET_SIZE = size;
				GameParameters.OPTION_BULLET_POWER = power;
				GameParameters.OPTION_BULLET_LIFE = bulletLife;
				GameParameters.OPTION_LIFE = life;
				
				//set current money
				GameParameters.getInstance().currentTotalMoney = money;

				//return to Main
				MainPage.getInstance(activity);
			}
		});
		
		//Cancel
		Button addBulletShopCancel = (Button)(activity.findViewById(R.id.ButtonShopCancel));
		addBulletShopCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//set original
				GameParameters.getInstance().currentTotalMoney = originalTotalMoney;
				
				//return to Main
				MainPage.getInstance(activity);
			}
		});
	}//ShopPage
	
	private static void setMoneyTextView(final Activity activity, int money) {
		TextView moneyText = (TextView)(activity.findViewById(R.id.TextViewTotalMoney));
		moneyText.setText(String.valueOf(money));
	}

}
