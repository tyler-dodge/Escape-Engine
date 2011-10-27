package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class ImageSource extends RenderSource {
	// private Resources res;
	private Point dimensions, offsets;
	private int drawableId;
	private Context context;

	public ImageSource(Context context, int id, int drawableId,
			Point dimensions, Point offsets) {
		super(id);
		this.context = context;
		this.drawableId = drawableId;
		// this.res = context.getResources();
		this.dimensions = dimensions;
		this.offsets = offsets;
	}

	public Image loadData(GL10 gl) {
		// reference from
		// http://obviam.net/index.php/texture-mapping-opengl-android-displaying-images-using-opengl-and-squares/
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				drawableId);
		int[] textureArray = new int[1];
		gl.glGenTextures(1, textureArray, 0);
		int texturePointer = textureArray[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texturePointer);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
		Image newImage = new Image(texturePointer, dimensions, offsets);
		return newImage;
	}
	
	public Point getOffsets(){
		return offsets;
	}
	
}
