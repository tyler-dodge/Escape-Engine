package edu.ncsu.uhp.escape.engine.collision;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

public interface IBroadCollision extends ICollision {
	public abstract boolean doesCollideBroad(Point thisPosition,
			IRotation thisRotation, ICollision checkCollide,
			Point checkPosition, IRotation checkRotation);
}
