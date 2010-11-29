package jp.arrow.angelforest.yukkuridefender;

public class CharacterStatus {
	protected int hp=1;
	protected int str=1;

	protected int originalHp=1;
	protected int originalStr=1;
	
	public void initCharStatus() {
		hp = originalHp;
		str = originalStr;
	}
	
	public void setOriginal(int hp, int str) {
		originalHp = hp;
		originalStr = str;
		initCharStatus();
	}
	
	public int getStr() {
		return str;
	}
}
