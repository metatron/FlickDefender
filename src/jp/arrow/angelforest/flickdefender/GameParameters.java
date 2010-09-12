package jp.arrow.angelforest.flickdefender;

public class GameParameters {
	//how many enemies simultateously
	public static int GAME_LVL_NORMAL = 1;
	public static int GAME_LVL_HARD = 3;
	public static int GAME_LVL_HARDEST = 5;
	public static int GAME_LVL_LUNATIC = 9;
	
	//max bullet allowed per lvl
	public static int BULLET_MAX_NORMAL = 2;
	public static int BULLET_MAX_HARD = 4;
	public static int BULLET_MAX_HARDEST = 4;
	public static int BULLET_MAX_LUNATIC = 10;
	
	
	private static GameParameters parameters;
	
	//current game lvl
	public int currentLvl = GAME_LVL_NORMAL;
	//max bullet allowed
	public int maxBulletAllowed = BULLET_MAX_NORMAL;
	
	public static GameParameters getInstance() {
		if(parameters == null) {
			parameters = new GameParameters();
		}
		
		return parameters;
	}
}
