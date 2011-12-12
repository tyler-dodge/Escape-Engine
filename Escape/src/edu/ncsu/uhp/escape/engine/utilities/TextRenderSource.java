package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLUtils;

public class TextRenderSource extends RenderSource {
	private Point dimensions, offsets;
	private String text;
	private static final int TEXT_RESOLUTION = 100;

	public TextRenderSource(int id, String text, Point dimensions, Point offsets) {
		super(id);
		this.text = text;
		// this.res = context.getResources();
		this.dimensions = dimensions;
		this.offsets = offsets;
	}

	public Image loadData(Context context, GL10 gl) {
		// http://stackoverflow.com/questions/1339136/draw-text-in-opengl-es-android
		int[] textures = new int[1];
		// Create an empty, mutable bitmap
		Bitmap bitmap = Bitmap.createBitmap((int) dimensions.getX() * TEXT_RESOLUTION,
				(int) dimensions.getY() * TEXT_RESOLUTION, Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);

		// Draw the text
		Paint textPaint = new Paint();
		textPaint.setTextSize(12);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
		// draw the text centered
		canvas.drawText(text, 0, 0, textPaint);

		// Generate one texture pointer...
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);

		// Use the Android GLUtils to specify a two-dimensional texture image
		// from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		Image newImage = new Image(textures[0], dimensions, offsets);

		// Clean up
		bitmap.recycle();
		return newImage;
	}
}
