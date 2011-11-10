package edu.ncsu.uhp.escape.engine.collision;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

public interface ICollision {
	public abstract boolean doesCollide(Point thisPosition,
			IRotation thisRotation, ICollision checkCollide,
			Point checkPosition, IRotation checkRotation);
	public abstract IRenderable getRenderable(GL10 gl);
}
