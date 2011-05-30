package edu.ncsu.uhp.escape.engine.utilities;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * The logic container for renderable data that include
 * the renderable, point, and position.
 * 
 * @author Tyler Dodge
 *
 */
public class RenderableData {
	private IRenderable renderable;
	private Point position;
	private IRotation rotation;

	public RenderableData(IRenderable renderable, Point position,
			IRotation rotation) {
		this.renderable = renderable;
		this.position = position;
		this.rotation = rotation;
	}

	public IRenderable getRenderable() {
		return renderable;
	}

	public Point getPosition() {
		return position;
	}

	public IRotation getRotation() {
		return rotation;
	}
}
