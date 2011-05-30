package edu.ncsu.uhp.escape.engine.collision;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

public interface INarrowCollision {
	public abstract boolean doesCollideNarrow(Point thisPosition,
			IRotation thisRotation, ICollision checkCollide,
			Point checkPosition, IRotation checkRotation);
}
