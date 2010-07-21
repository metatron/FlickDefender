package jp.arrow.angelforest.engine.core;

import javax.microedition.khronos.opengles.GL10;

/**
 * creates triangle.
 * 
 * @author merabi
 *
 */
public class TrianglePolygon extends AngelForest2DEngine {
	public TrianglePolygon() {
		int vertices[] = {
				0, 0, 0,
				one*80, 0, 0,
				one*80, one*80, 0
		};
		
		int color[] = {
				one, 0, 0, one,
				0, one, 0, one,
				0, 0, one, one
		};

		vertexBuffer = makeByteBuffer(vertices);
		colorBuffer = makeByteBuffer(color);
	}
	
	@Override
	public void draw(int x, int y, float w, float h, float angle) {
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
	}
}
