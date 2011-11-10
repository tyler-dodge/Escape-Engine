package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class ColorBoxSource  extends RenderSource {
	private Point dimensions, offsets;
	private int r, g, b, a;

	public ColorBoxSource(int id, int r, int g, int b, int a, Point dimensions,
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
	public IRenderable loadData(GL10 gl) {

		return new ColorBox(r, g, b, a, dimensions, offsets);
	}

}