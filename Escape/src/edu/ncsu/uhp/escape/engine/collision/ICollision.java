package edu.ncsu.uhp.escape.engine.collision;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

public interface ICollision {
	public abstract boolean doesCollide(Point thisPosition,
			IRotation thisRotation, ICollision checkCollide,
			Point checkPosition, IRotation checkRotation);
	public abstract IRenderable getRenderable(Context context,GL10 gl);
}
