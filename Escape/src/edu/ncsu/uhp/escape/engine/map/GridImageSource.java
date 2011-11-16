package edu.ncsu.uhp.escape.engine.map;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.opengl.GLUtils;
import edu.ncsu.uhp.escape.engine.utilities.IRenderable;
import edu.ncsu.uhp.escape.engine.utilities.Image;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Logical container for a grid of renderables
 * 
 * @author Tyler Dodge and Bethany Vohlers
 *
 */
public class GridImageSource extends RenderSource {

	// private Resources res;
	private Point tileDimensions, offsets;
	private ResourceGrid resourceIds;
	private int tileSizePx;
	private final int TEXTURE_BLOCK_SIZE;
	private static final int DEFAULT_TEXTURE_BLOCK_SIZE = 512;

	public GridImageSource(Context context, int id, ResourceGrid resources,
			Point offsets, Point tileDimensions, int tileSizePx) {
		this(context, id, resources, offsets, tileDimensions, tileSizePx,
				DEFAULT_TEXTURE_BLOCK_SIZE);
	}

	public GridImageSource(Context context, int id, ResourceGrid resources,
			Point offsets, Point tileDimensions, int tileSizePx,
			int textureBlockSize) {
		super(id);
		this.tileSizePx = tileSizePx;
		this.resourceIds = resources;
		this.tileDimensions = tileDimensions;
		this.offsets = offsets;
		this.TEXTURE_BLOCK_SIZE = textureBlockSize;
		double power = Math.log(textureBlockSize) / Math.log(2);
		if (power != Math.floor(power)) {
			throw new IllegalArgumentException(
					"Texture block Size must be a power of 2");
		}
	}

	@Override
	public IRenderable loadData(Context context,GL10 gl) {
		// gl.glEnable(GL10.GL_ALPHA);

		gl.glEnable(GL10.GL_TEXTURE_2D);
		int width = resourceIds.getWidth();
		int height = resourceIds.getHeight();
		int arraySize = (width * tileSizePx / TEXTURE_BLOCK_SIZE)
				* (height * tileSizePx / TEXTURE_BLOCK_SIZE);
		if (arraySize <= 0)
			arraySize = 1;
		int[] textureArray = new int[arraySize];
		gl.glGenTextures(arraySize, textureArray, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		Bitmap bitmap = null;
		Image[] images = new Image[arraySize];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int index = x * tileSizePx / TEXTURE_BLOCK_SIZE + y
						* tileSizePx / TEXTURE_BLOCK_SIZE * width * tileSizePx
						/ TEXTURE_BLOCK_SIZE;
				float tileDimensionX = tileDimensions.getX();
				float tileDimensionY = tileDimensions.getY();
				float offsetX = x / (TEXTURE_BLOCK_SIZE / tileSizePx)
						* tileDimensionX;
				float offsetY = y / (TEXTURE_BLOCK_SIZE / tileSizePx)
						* tileDimensionY;
				int image_offsetX = (x * tileSizePx) % TEXTURE_BLOCK_SIZE;
				int image_offsetY = (y * tileSizePx) % TEXTURE_BLOCK_SIZE;
				if (images[index] == null) {

					Bitmap texture = Bitmap.createBitmap(TEXTURE_BLOCK_SIZE,
							TEXTURE_BLOCK_SIZE, Config.ARGB_8888);
					gl.glBindTexture(GL10.GL_TEXTURE_2D, textureArray[index]);
					gl.glTexParameterf(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
					gl.glTexParameterf(GL10.GL_TEXTURE_2D,
							GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, texture, 0);
					Image image = new Image(textureArray[index],
							tileDimensions.multiply(TEXTURE_BLOCK_SIZE
									/ tileSizePx), new Point(offsetX, offsetY,
									0));
					images[index] = image;
				} else
					gl.glBindTexture(GL10.GL_TEXTURE_2D, textureArray[index]);
				bitmap = BitmapFactory.decodeResource(context.getResources(),
						resourceIds.getId(x, y));
				GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, image_offsetX,
						image_offsetY, bitmap);
				bitmap.recycle();
				int error = gl.glGetError();
				if (error != 0) {
					error++;
				}
			}
		}
		LinkedList<IRenderable> imageList = new LinkedList<IRenderable>();
		for (Image image : images) {
			imageList.add(image);
		}

		return new CompositeRenderable(tileDimensions, offsets, imageList);
	}
}
