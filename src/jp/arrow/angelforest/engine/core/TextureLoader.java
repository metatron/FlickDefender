package jp.arrow.angelforest.engine.core;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * テクスチャ読み込み
 */
public class TextureLoader {
	protected static final int one = 0x10000;

	/**
     * テクスチャを読み込む
     * @param gl
     * @param context アクティビティを渡す
     * @param resource_id 読み込むリソースのIDを渡す
     * @return 生成したテクスチャのIDを返す
     */
    public static int loadTexture(GL10 gl, Context context, int resource_id)
    {
        int[] textures = new int[1];
        // テクスチャオブジェクトを1つ作成
        gl.glGenTextures(1, textures, 0);

        // 作成されたテクスチャのIDをセット
        int texture_id = textures[0];
        // 2Dテクスチャとしてバインド
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture_id);

        // バインドされているテクスチャに、テクスチャの拡大・縮小方法を指定。高速化のためにニアレストネイバー法を用いる
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        // 拡大は線形補完とする。重いようならニアレストネイバー法へ変更すること
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        // バインドされているテクスチャに、繰り返し方法を指定
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_CLAMP_TO_EDGE);

        // バインドされているテクスチャに、テクスチャの色が下地の色を置き換えるよう指定(GL_REPLACE)
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,
                GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);

        InputStream is = context.getResources()
                .openRawResource(resource_id);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // 例外処理
            }
        }
//a        http://groups.google.com/group/android-developers/browse_thread/thread/2cb496c5da3b6955
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        
        return texture_id;
    }
    
    private static int[] generateVertices(int w, int h) {
    	final int[] panelVertices = new int[] { 
    		-one*w/2, one*h/2, 0,// 左上
    		-one*w/2, -one*h/2, 0,// 左下
    		one*w/2, one*h/2, 0,// 右上
    		one*w/2, -one*h/2, 0 // 右下
    	};
    	
    	return panelVertices;
    }
}
