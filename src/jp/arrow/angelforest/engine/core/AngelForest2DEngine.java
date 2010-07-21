package jp.arrow.angelforest.engine.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class AngelForest2DEngine {
	public static final int one = 0x10000;
	protected static GL10 gl;

	protected IntBuffer vertexBuffer;
	protected IntBuffer colorBuffer;
	
	private boolean isDeleted = false;
	
	// 初期化
	public static void init(GL10 gl, int w, int h) {
		AngelForest2DEngine.gl = gl;
		// ビューポート変換
		gl.glViewport(0, 0, w, h);

		// 投影変換
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(-w / 2, w / 2, -h / 2, h / 2, -100, 100);
		gl.glTranslatef(-w / 2, h / 2, 0);

		// モデリング変換
		gl.glMatrixMode(GL10.GL_MODELVIEW);

		// クリア色の設定
		gl.glClearColor(0.3f, 0.3f, 0.3f, 1);

		// パフォーマンス設定
//		gl.glShadeModel(GL10.GL_FLAT);	//頂点などをグラデーションにしたいときはコメントアウトする。
		gl.glDisable(GL10.GL_DEPTH_TEST);
		// 片面のみのテクスチャーに設定
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glFrontFace(GL10.GL_CCW);

		// 頂点配列の設定
//		gl.glVertexPointer(3, GL10.GL_FIXED, 0, makeByteBuffer(panelVertices));
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//
//		// UVの設定
//		gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, makeByteBuffer(panelUVs));

		// ブレンドの設定
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_BLEND);

		// ポイントの設定(アンチエイリアス関連)
		// gl.glEnable(GL10.GL_POINT_SMOOTH);
		gl.glDisable(GL10.GL_POINT_SMOOTH);

		// その他設定（パフォーマンス）
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_LIGHTING);
	}

	/**
	 * creates Byte Buffer.
	 * 
	 * @param arr
	 * @return
	 */
	public static IntBuffer makeByteBuffer(int[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		IntBuffer ib = bb.asIntBuffer();
		ib.put(arr);
		ib.position(0);
		return ib;
	}
	
	/**
	 * creates Float Buffer.
	 * 
	 * @param arr
	 * @return
	 */
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	/**
	 * draws the polygon.
	 * It can control width, height, and angle.
	 * 
	 * @param gl
	 */
	abstract public void draw(int x, int y, float w, float h, float angle);

	/**
	 * draws the polygon.
	 * It can control angle only.
	 * 
	 * @param gl
	 */
	public void draw(int x, int y, float angle) {
		draw(x, y, -1.0f, -1.0f, angle);
	}
	
	/**
	 * draws the polygon.
	 * It controls xy only.
	 * 
	 * @param gl
	 */
	public void draw(int x, int y) {
		draw(x, y, -1.0f, -1.0f, 0.0f);
	}
	
	/**
	 * set the isDelete flag to true.
	 */
	public void delete() {
		if(!isDeleted) {
			isDeleted = true;
		}
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public static GL10 getGl() {
		return gl;
	}
}
