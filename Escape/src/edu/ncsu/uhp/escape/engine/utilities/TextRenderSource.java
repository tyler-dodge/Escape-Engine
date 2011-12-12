package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLUtils;

public class TextRenderSource extends RenderSource {
	private Point  offsets;
	private float height;
	private String text;
	private static final int TEXT_RESOLUTION = 100;

	public TextRenderSource(int id, String text, float height, Point offsets) {
		super(id);
		this.text = text;
		// this.res = context.getResources();
		this.height=height;
		this.offsets = offsets;
	}

	public Image loadData(Context context, GL10 gl) {
		// http://stackoverflow.com/questions/1339136/draw-text-in-opengl-es-android
		int[] textures = new int[1];
		// Create an empty, mutable bitmap
		Paint textPaint = new Paint();
		textPaint.setTextSize(40);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
		textPaint.setTextAlign(Paint.Align.LEFT);
		Rect bounds = new Rect();
		textPaint.getTextBounds(text.toCharArray(), 0, text.length(), bounds);

		Bitmap bitmap = Bitmap.createBitmap(bounds.width()+2, bounds.height()+2,
				Bitmap.Config.ARGB_8888);
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap); 
		bitmap.eraseColor(Color.TRANSPARENT);

		// Draw the text
		// draw the text centered
		canvas.drawText(text, -bounds.left+1, -bounds.top+1, textPaint);
		// Generate one texture pointer...
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);


		// Use the Android GLUtils to specify a two-dimensional texture image
		// from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		Point aspectDimensions = new Point(height* bounds.width() / bounds.height(), height
				, 0);
		Image newImage = new Image(textures[0], aspectDimensions, offsets);

		// Clean up
		bitmap.recycle();
		return newImage;
	}
}
