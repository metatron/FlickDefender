package jp.arrow.angelforest.yukkuridefender;

import jp.arrow.angelforest.engine.core.TexturePolygon;

public class BossEnemy extends Enemy {

	public BossEnemy(TexturePolygon textPoly, double[] enlargeRatio) {
		super(textPoly, enlargeRatio);
	}

	@Override
	public void reset() {
		super.reset();
		
		//re-set the boss hp and str
		int hp = GameParameters.getInstance().currentEnemyHp*100;
		int str = GameParameters.getInstance().currentEnemyStr*100;
		setOriginal(hp, str);
		
		//score
		this.score = GameParameters.ENEMY_SCORE*100;
	}
}
