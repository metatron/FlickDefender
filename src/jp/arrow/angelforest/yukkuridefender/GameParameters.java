package jp.arrow.angelforest.yukkuridefender;

public class GameParameters {
	/**
	 * default params
	 */
	//how many enemies simultateously
	public static int GAME_LVL_NORMAL = 2;
	public static int GAME_LVL_HARD = 4;
	public static int GAME_LVL_HARDEST = 6;
	public static int GAME_LVL_LUNATIC = 11;
	
	//max bullet allowed per lvl
	public static int BULLET_MAX_NORMAL = 2;
	public static int BULLET_MAX_HARD = 2;
	public static int BULLET_MAX_HARDEST = 2;
	public static int BULLET_MAX_LUNATIC = 2;
	
	public static int ENEMY_HP_BASE = 10;
	public int currentEnemyHp = ENEMY_HP_BASE;
	public static int ENEMY_STR_BASE = 10;
	public int currentEnemyStr = ENEMY_STR_BASE;
	public static int ENEMY_SCORE = 100; //money
	public int currentEnemyScore = 100;
	
	public static int BULLET_LIFE_ORIGINAL = 10;
	public int currentBulletLife = 10;
	
	public static int BULLET_POWER_ORIGINAL = 10;
	public int currentBulletPower = 10;
	
	public static double BULLET_SIZE_RATIO_ORIGINAL = 1.0d;
	public double currentBulletSize = 1.0d;
	
	public static int HP_ORIGINAL=100;
	public int currentHp=0;
	
	/**
	 * this is set by shop
	 */
	public static int OPTION_BULLET_NUMBER_PRICE = 500;
	public static int OPTION_BULLET_NUMBER_MAX = 100;
	public static int OPTION_BULLET_NUMBER = 0;
	
	public static int OPTION_BULLET_SIZE_PRICE = 500;
	public static int OPTION_BULLET_SIZE_MAX = 300;
	public static double OPTION_BULLET_SIZE = 0d;
	
	public static int OPTION_BULLET_POWER_PRICE = 500;
	public static int OPTION_BULLET_POWER_MAX = 100;
	public static int OPTION_BULLET_POWER = 0;

	public static int OPTION_BULLET_LIFE_PRICE = 500;
	public static int OPTION_BULLET_LIFE_MAX = 100;
	public static int OPTION_BULLET_LIFE = 0;
	
	public static int OPTION_LIFE_PRICE = 500;
	public static int OPTION_LIFE_MAX = 100;
	public static int OPTION_LIFE = 0;
	
	public static String UID = "";
	
	/**
	 * DB related
	 */
	public static final String DB_NAME = "angelforest_yukkuridefender.db";
	public static String PACKAGE_NAME = "";
	
	/**
	 * class instances
	 */
	private static GameParameters parameters;
	
	//current game lvl
	public int currentLvl = GAME_LVL_NORMAL;
	//max bullet allowed
	public int maxBulletAllowed = BULLET_MAX_NORMAL;
	
	//current total score
	public int currentTotalScore;
	public int currentTotalMoney;
	
	public static GameParameters getInstance() {
		if(parameters == null) {
			parameters = new GameParameters();
		}
		
		return parameters;
	}
	
	public int getBulletNumByLevel() {
		if (currentLvl == GameParameters.GAME_LVL_HARD) {
			return BULLET_MAX_HARD;
		}
		else if (currentLvl == GameParameters.GAME_LVL_HARDEST) {
			return BULLET_MAX_HARDEST;
		}
		else if (currentLvl == GameParameters.GAME_LVL_LUNATIC) {
			return BULLET_MAX_LUNATIC;
		}
		return BULLET_MAX_NORMAL;
	}
}
