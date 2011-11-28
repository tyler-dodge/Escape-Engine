package edu.ncsu.uhp.escape.engine.collision;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

/**
 * Collision Detection
 * 
 * @author Tim Mervine
 * 
 */
public class OB_BroadPhase implements IBroadCollision {
	private Point dimension;
	private Point offsets;
	private RenderSource source;

	/**
	 * Constructs a Collision Box from the Points dimension and offsets.
	 * 
	 * @param dimension
	 * @param offsets
	 */
	public OB_BroadPhase(Point dimension, Point offsets) {
		this.dimension = dimension;
		this.offsets = offsets;
		this.source = new ColorBoxSource(hashCode(), 0, 0, 255, 255, dimension,
				offsets);
	}

	/**
	 * gets dimension of collision box
	 * 
	 * @return dimension
	 */
	public Point getDimension() {
		return dimension;
	}

	/**
	 * gets offset of collision box
	 * 
	 * @return offsets
	 */
	public Point getOffsets() {
		return offsets;
	}

	/**
	 * Checks for collision
	 */
	public boolean doesCollideBroad(Point thisPosition, IRotation thisRotation,
			ICollision checkCollide, Point checkPosition,
			IRotation checkRotation) {
		boolean collide = false;
		OB_BroadPhase boxCheckCollision = null;

		if (checkCollide instanceof OB_BroadPhase) {
			boxCheckCollision = (OB_BroadPhase) checkCollide;

		} else if (checkCollide instanceof MultiPhaseCollision) {
			boxCheckCollision = (OB_BroadPhase) ((MultiPhaseCollision) checkCollide).broad;
		}
		if (boxCheckCollision != null) {
			Point thisCorner = this.dimension;
			Point checkOffsets = checkPosition.subtract(thisPosition
					.add(offsets)).add(boxCheckCollision.offsets);
			Point checkCorner = boxCheckCollision.dimension
					.add(checkOffsets);
			boolean topX = checkOffsets.getX() >= 0 || checkCorner.getX() >= 0;
			boolean topY = checkOffsets.getY() >= 0 || checkCorner.getY() >= 0;
			boolean topZ = checkOffsets.getZ() >= 0 || checkCorner.getZ() >= 0;
			boolean cornerX = checkCorner.getX() <= thisCorner.getX() || checkOffsets.getX() <= thisCorner.getX();
			boolean cornerY = checkCorner.getY() <= thisCorner.getY() || checkOffsets.getY() <= thisCorner.getY();
			boolean cornerZ = checkCorner.getZ() <= thisCorner.getZ() || checkOffsets.getZ() <= thisCorner.getZ();
			if (((topX && cornerX) && (topY && cornerY) && (topZ && cornerZ))
					|| ((!topX && !cornerX) && (!topY && !cornerY) && (!topZ && !cornerZ))) {
				collide = true;
			}
		}
		return collide;
	}

	public boolean doesCollide(Point thisPosition, IRotation thisRotation,
			ICollision checkCollide, Point checkPosition,
			IRotation checkRotation) {
		return doesCollideBroad(thisPosition, thisRotation, checkCollide,
				checkPosition, checkRotation);
	}

	public IRenderable getRenderable(Context context, GL10 gl) {
		return this.getRenderable(context, gl);
	}
}