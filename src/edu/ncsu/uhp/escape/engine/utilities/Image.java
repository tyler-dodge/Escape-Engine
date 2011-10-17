package edu.ncsu.uhp.escape.engine.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import edu.ncsu.uhp.escape.engine.raster.IRenderParameters;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class Image implements IRenderable {
	private Point dimensions;
	private Point offsets;
	private int textureId;
	private FloatBuffer glData;
	private int vertexCount;
	private int textureCoordCount;
	private final int VERTEX_COUNT = 4;
	private float offsetX, offsetY, offsetZ;
	private int VBO_ID = -1;
	private boolean isLoaded = false;

	public Image(int textureId, Point dimensions, Point offsets) {
		if (dimensions == null)
			throw new IllegalArgumentException("Dimensions cannot be null");
		this.dimensions = dimensions;
		this.textureId = textureId;
		if (offsets == null)
			throw new IllegalArgumentException("Offsets cannot be null");
		setOffsets(offsets);
	}

	private void load() {
		if (!isLoaded) {
			FloatBuffer textureCoords = getTextureCoords();
			FloatBuffer vertices = getVertexBuffer(dimensions);
			vertexCount = vertices.capacity();
			textureCoordCount = textureCoords.capacity();
			glData = ByteBuffer
					.allocateDirect((vertexCount + textureCoordCount) * 4)
					.order(ByteOrder.nativeOrder()).asFloatBuffer();
			glData.put(vertices);
			glData.put(textureCoords);
			glData.position(0);
		}
		isLoaded = true;
	}

	private FloatBuffer getVertexBuffer(Point dimensions) {
		float dimenX = dimensions.getX();
		float dimenY = dimensions.getY();
		float[] quadPoints = { 0.0f, dimenY, 0.0f, dimenX, dimenY, 0.0f, 0.0f,
				0.0f, 0.0f, dimenX, 0.0f, 0.0f };

		FloatBuffer quad = ByteBuffer.allocateDirect(quadPoints.length * 4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		quad.put(quadPoints);
		quad.position(0);
		return quad;
	}

	public FloatBuffer getTextureCoords() {
		float[] texCoords = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texCoords.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer newTextureCoords = byteBuffer.asFloatBuffer();
		newTextureCoords.put(texCoords);
		newTextureCoords.position(0);

		return newTextureCoords;
	}

	public void drawGL10(GL10 gl) {
		load();
		gl.glPushMatrix();
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glData.position(0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glTranslatef(offsetX, offsetY, offsetZ);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, glData.position(0));
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, glData.position(vertexCount));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glPopMatrix();
	}

	public void drawGL11(GL11 gl) {
		if (VBO_ID == -1) {
			load();
			int[] ids = new int[1];
			gl.glGenBuffers(1, ids, 0);
			VBO_ID = ids[0];
			gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, VBO_ID);
			gl.glBufferData(GL11.GL_ARRAY_BUFFER,
					(vertexCount + textureCoordCount) * 4, glData.position(0),
					GL11.GL_STATIC_DRAW);

		}
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, VBO_ID);
		gl.glPushMatrix();
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		glData.position(0);
		gl.glTranslatef(offsetX, offsetY, offsetZ);

		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, 0);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, vertexCount * 4);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glPopMatrix();
	}

	public Point getDimensions() {
		return dimensions;
	}

	public Point getOffsets() {
		return offsets;
	}

	public void setOffsets(Point newOffsets) {
		this.offsets = newOffsets;
		offsetX = offsets.getX();
		offsetY = offsets.getY();
		offsetZ = offsets.getZ();

	}

	public IRenderParameters getRenderParameters() {
		// TODO Auto-generated method stub
		return null;
	}
}
