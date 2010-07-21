package jp.arrow.angelforest.engine.core;

import javax.microedition.khronos.opengles.GL10;

public class SquarePolygon extends AngelForest2DEngine {
	public SquarePolygon(int[] panelVertices, int[] color) {
		vertexBuffer = makeByteBuffer(panelVertices);
		colorBuffer = makeByteBuffer(color);
	}

	public SquarePolygon() {
		// vertex info
		int[] panelVertices = new int[] { 
			-one, one, 0,// 左上
			-one, -one, 0,// 左下
			one, one, 0,// 右上
			one, -one, 0 // 右下
		};

		int[] color = {
				one, 0, 0, one,
				0, one, 0, one,
				0, 0, one, one,
				one, 0, one, one
		};

		vertexBuffer = makeByteBuffer(panelVertices);
		colorBuffer = makeByteBuffer(color);
	}
	
	@Override
	public void draw(int x, int y, float w, float h, float angle) {
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		
		gl.glPushMatrix();
		gl.glTranslatex(x*one, -y*one, 0);
		gl.glRotatef(angle, 0.0f,0.0f, 1.0f);
		gl.glScalef(w, h, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glPopMatrix();
	}

}
