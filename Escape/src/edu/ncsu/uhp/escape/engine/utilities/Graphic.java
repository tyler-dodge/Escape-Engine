package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class Graphic {
	private Point position;
	private Point dimensions;
	private IRotation rotation;
	private RenderSource source;

	public Graphic(Point position, IRotation rotation,
			RenderSource source) {
		this.position = position;
		this.dimensions = dimensions;
		this.source = source;
		this.rotation = rotation;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}


	public IRotation getRotation() {
		return rotation;
	}

	public void setRotation(IRotation rotation) {
		this.rotation = rotation;
	}

	public IRenderable getRenderable(Context context, GL10 gl) {
		return source.getData(context, gl);
	}
}
