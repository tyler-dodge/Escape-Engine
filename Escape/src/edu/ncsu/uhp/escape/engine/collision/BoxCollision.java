package edu.ncsu.uhp.escape.engine.collision;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Collision Detection
 * 
 * @author Tim Mervine
 * 
 */
public class BoxCollision extends MultiPhaseCollision {

	/**
	 * Constructs a Collision Box from the Points dimension and offsets.
	 * 
	 * @param dimension
	 * @param offsets
	 */
	public BoxCollision(Point dimension, Point offsets) {
		super(new OB_BroadPhase(dimension, offsets), new AABB_NarrowPhase(
				dimension, offsets));
	}
}