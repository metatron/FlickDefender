package jp.arrow.angelforest.engine.core;

import java.text.DecimalFormat;

public class EngineDebugInfo {
	public static String getMemoryInfo() {
	    DecimalFormat f1 = new DecimalFormat("#,###KB");
	    DecimalFormat f2 = new DecimalFormat("##.#");
	    long free = Runtime.getRuntime().freeMemory() / 1024;
	    long total = Runtime.getRuntime().totalMemory() / 1024;
	    long max = Runtime.getRuntime().maxMemory() / 1024;
	    long used = total - free;
	    double ratio = (used * 100 / (double)total);
	    String info = 
	    "Java Memory Info : Total=" + f1.format(total) + ", " +
	    "Used" + f1.format(used) + " (" + f2.format(ratio) + "%), " +
	    "Avaiable Max="+f1.format(max);
	    return info;
	}

}
