package jp.arrow.angelforest.engine.core;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

//テクスチャ
public class Texture {
	public int name; // 名前
	public int width; // 幅
	public int height;// 高さ
	public int size; // サイズ  (テクスチャを投影した正方形の一辺の長さ)
	
	private IntBuffer intVertexBuffer;
	private IntBuffer intUvBuffer;
	
	private GL10 gl;
	
	public Texture(GL10 gl) {
		this.gl = gl;
	}
	
	public IntBuffer getIntVertexBuffer() {
		return intVertexBuffer;
	}
	public void setIntVertexBuffer(IntBuffer intBuffer) {
		this.intVertexBuffer = intBuffer;
	}
	public IntBuffer getIntUvBuffer() {
		return intUvBuffer;
	}
	public void setIntUvBuffer(IntBuffer intUvBuffer) {
		this.intUvBuffer = intUvBuffer;
	}
	
	public void delete() {
		name = 0;
		width = 0;
		height = 0;
		size = 0;
		gl.glDeleteTextures(1, intVertexBuffer);
		intVertexBuffer = null;
		intUvBuffer = null;
	}
}
