package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class ColorSource extends RenderSource {
	private Point dimensions, offsets;
	private int r, g, b, a;

	public ColorSource(int id, int r, int g, int b, int a, Point dimensions,
			Point offsets) {
		super(id);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.dimensions = dimensions;
		this.offsets = offsets;
	}

	@Override
	public IRenderable loadData(Context context,GL10 gl) {

		return new ColorQuad(r, g, b, a, dimensions, offsets);
	}

}
