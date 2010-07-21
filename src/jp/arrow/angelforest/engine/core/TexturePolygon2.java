package jp.arrow.angelforest.engine.core;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.content.Context;

public class TexturePolygon2 extends AngelForest2DEngine {
	int textid;
	
	Texture texture;
	
	// テクスチャ頂点情報
	private final static int[] panelVertices = new int[] { 
		-one, one, 0,// 左上
		-one, -one, 0,// 左下
		one, one, 0,// 右上
		one, -one, 0 // 右下
	};

	// テクスチャUV情報
	private final static int[] panelUVs = new int[] { 
			0, 0, // 左上
			0, one, // 左下
			one, 0, // 右上
			one, one, // 右下
	};
	
	public TexturePolygon2(Context countext, int resource) {
		textid = TextureLoader.loadTexture(gl, countext, resource);
		
		// テクスチャオブジェクトの生成
		texture = new Texture(gl);
		texture.name = textid;
		texture.width = 128;
		texture.height = 128;
		texture.setIntVertexBuffer(makeByteBuffer(panelVertices));
		texture.setIntUvBuffer(makeByteBuffer(panelUVs));
	}

	@Override
	public void draw(int x, int y, float w, float h, float angle) {
        // 色をセット
        gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);

        // モデルビュー行列を指定
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 現在選択されている行列(モデルビュー行列)に、単位行列をセット
        gl.glLoadIdentity();
        // Magic offsets to promote consistent rasterization.
        gl.glTranslatef(0.375f, 0.375f, 0.0f);

        // テクスチャユニット0番をアクティブに
        gl.glActiveTexture(GL10.GL_TEXTURE0);
        // テクスチャIDに対応するテクスチャをバインド
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textid);
        // 2Dテクスチャを有効に
        gl.glEnable(GL10.GL_TEXTURE_2D);

        // 座標と、幅・高さを指定
        int[] rect = {0, 128, 128, -128};

        // バインドされているテクスチャのどの部分を使うかを指定
        ((GL11)gl).glTexParameteriv(GL10.GL_TEXTURE_2D,
                GL11Ext.GL_TEXTURE_CROP_RECT_OES, rect, 0);
        // 2次元座標を指定して、テクスチャを描画
        ((GL11Ext)gl).glDrawTexiOES(0, 0, 0, 128, 128);
	}
	
	public void draw2(int x, int y, float w, float h, float angle) {
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
		gl.glRotatef(angle, 0.0f,0.0f, 1.0f);
		//w & h 
//		w = w * texture.size / texture.width;
//		h = h * texture.size / texture.height;
		gl.glScalef(w, h, 1);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();
	}

}
