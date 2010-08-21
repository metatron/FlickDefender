package jp.angelforest.engine.util;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * This calculates the width and height ratio, based on the
 * size 320x480.
 * it returns the double[2] array.
 * 
 * @author metatron_kaether
 *
 */
public class SizeConvertRatio {
	public static final double DEFAULT_WIDTH = 320d;
	public static final double DEFAULT_HEIGHT = 480d;
	
	private static SizeConvertRatio sizeConvertRatio;
	private static double[] ratio;
	
	public static SizeConvertRatio getInstance() {
		if(sizeConvertRatio == null) {
			ratio = new double[2];
			return new SizeConvertRatio();
		}
		return sizeConvertRatio;
	}
	
	public void calculate(Context context) {
        Display disp =
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).
            getDefaultDisplay();
        ratio[0] = (double)(disp.getWidth())/DEFAULT_WIDTH;
        ratio[1] = (double)(disp.getHeight())/DEFAULT_HEIGHT;
	}
	
	public static double[] getRatio() {
		return ratio;
	}
}
