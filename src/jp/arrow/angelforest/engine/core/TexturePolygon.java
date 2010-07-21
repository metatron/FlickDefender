package jp.arrow.angelforest.engine.core;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.GLUtils;

/**
 * テクスチャ読み込み
 */
public class TexturePolygon extends AngelForest2DEngine {
	private Texture texture;

	// テクスチャUV情報
	private final static int[] panelUVs = new int[] { 
			0, 0, // 左上
			0, one, // 左下
			one, 0, // 右上
			one, one, // 右下
	};
	
	public TexturePolygon(Context context, int resourceid) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceid);
		makeTexture(bitmap);
		bitmap.recycle();
	}
    
	private void makeTexture(Bitmap bitmap) {
		//recreating bitmap.
		//it needs to be 2^n and has to be bigger than width or height.
		int w = (bitmap.getWidth() > bitmap.getHeight()) ? bitmap.getWidth()
				: bitmap.getHeight();
		int size = 32;
		for (; size < 1024; size *= 2) {
			if (w <= size)
				break;
		}
		Bitmap result = resizeBitmap(bitmap, size, size);

		int[] textureName = new int[1];

		// テクスチャの設定
		gl.glGenTextures(1, textureName, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName[0]);
		// gl.glTexImage2D(GL10.GL_TEXTURE_2D,0,GL10.GL_RGBA,
		// result.getWidth(),result.getHeight(),
		// 0,GL10.GL_RGBA,GL10.GL_UNSIGNED_BYTE,bb);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, result, 0);

		// テクスチャオブジェクトの生成
		texture = new Texture(gl);
		texture.name = textureName[0];
		texture.width = bitmap.getWidth();
		texture.height = bitmap.getHeight();
		texture.size = size;
		texture.setIntVertexBuffer(makeByteBuffer(resizeMatrix(texture)));
		texture.setIntUvBuffer(makeByteBuffer(panelUVs));

		result.recycle();
		bitmap.recycle();
	}

	/**
	 * creating square bitmap canvas and draw image on theere.
	 * (it seems that png image needs to be rewrite to bitmap
	 * in order to display it...)
	 * 
	 * @param bmp
	 * @param w
	 * @param h
	 * @return
	 */
	private Bitmap resizeBitmap(Bitmap bmp, int w, int h) {
		Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(result);
		//draw bitmap on the center of the canvas
		int dw = (w-bmp.getWidth())/2;
		int dh = (h-bmp.getHeight())/2;
		canvas.drawBitmap(bmp, dw, dh, null);

//		BitmapDrawable drawable = new BitmapDrawable(bmp);
//		drawable.setBounds(0, 0, bmp.getWidth(), bmp.getHeight());
//		drawable.draw(canvas);
		return result;
	}
	
	/**
	 * Creating vertices for the polygon.
	 * initial polygon will be drawn as a square
	 * and is placed at the center (0, 0).
	 * 
	 * @param texture
	 * @return
	 */
	private int[] resizeMatrix(Texture texture) {
		final int[] panelVertices = new int[] { 
			-one*texture.size/2, one*texture.size/2, 0,// 左上
			-one*texture.size/2, -one*texture.size/2, 0,// 左下
			one*texture.size/2, one*texture.size/2, 0,// 右上
			one*texture.size/2, -one*texture.size/2, 0 // 右下
		};
		
		return panelVertices;
	}
	
	@Override
	public void draw(int x, int y, float w, float h, float angle) {
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.name);

		//もう一度ﾎﾟｲﾝﾄをバッファに入れとく必要がある。そうでないとどのポリゴンに対してテクスチャを貼るか分からなくなる。
		gl.glVertexPointer(3,GL10.GL_FIXED,0,texture.getIntVertexBuffer()); 
		
		//これをｵﾝにすると各ポイントのカラーも一緒にオーバーレイされてしまう。
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texture.getIntUvBuffer());
		//textureはオンにする
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glPushMatrix(); //LIFOで適応される。Push, Popで適応できるのは拡大縮小、回転、移動関連のみ
		gl.glTranslatex(x*one, -y*one, 0);
		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		//w & h 
//		w = w * texture.size / texture.width;
//		h = h * texture.size / texture.height;
		gl.glScalef(w, h, 1);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
	}
	
	@Override
	public void delete() {
		texture.delete();
		super.delete();
	}
}
