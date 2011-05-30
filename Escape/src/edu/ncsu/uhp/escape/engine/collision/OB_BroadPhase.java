package edu.ncsu.uhp.escape.engine.collision;

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

	/**
	 * Constructs a Collision Box from the Points dimension and offsets.
	 * 
	 * @param dimension
	 * @param offsets
	 */
	public OB_BroadPhase(Point dimension, Point offsets) {
		this.dimension = dimension;
		this.offsets = offsets;
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
	 * Creates points for the collision box in a 2D system
	 * 
	 * @param position
	 * @param rotation
	 * @return The vertices of the 2D box
	 */
	public Point[] getPoints(Point position) {
		Point[] vertices = new Point[4];
		vertices[0] = new Point(0, 0, 0).add(this.offsets);
		vertices[1] = new Point(0, this.dimension.getY(), 0).add(this.offsets);
		vertices[2] = new Point(this.dimension.getX(), this.dimension.getY(), 0)
				.add(this.offsets);
		vertices[3] = new Point(this.dimension.getX(), 0, 0).add(this.offsets);
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = vertices[i].add(position);
		}
		return vertices;
	}

	/**
	 * Checks for collision
	 */
	public boolean doesCollideBroad(Point thisPosition, IRotation thisRotation,
			ICollision checkCollide, Point checkPosition,
			IRotation checkRotation) {
		Point[] box1 = getPoints(thisPosition);
		boolean collide = false;
		OB_BroadPhase boxCheckCollision = null;

		if (checkCollide instanceof OB_BroadPhase) {
			boxCheckCollision = (OB_BroadPhase) checkCollide;

		} else if (checkCollide instanceof MultiPhaseCollision) {
			boxCheckCollision = (OB_BroadPhase) ((MultiPhaseCollision) checkCollide).broad;
		}
		if (boxCheckCollision != null) {
			Point[] box2 = boxCheckCollision.getPoints(checkPosition);
			float box1x1 = (box1[3].getX() - offsets.getX()) * 1.5f; // Left x
																		// value
			float box1x2 = (box1[0].getX() - offsets.getX()) * 1.5f; // Right x
																		// value
			float box1y1 = (box1[0].getY() - offsets.getY()) * 1.5f; // Bottom y
																		// value
			float box1y2 = (box1[1].getY() - offsets.getY()) * 1.5f; // Top y
																		// value
			float box1z1 = box1[0].getZ() * 1.5f; // Lower z value
			float box1z2 = (box1[0].getZ() + this.dimension.getZ()) * 1.5f; // Higher
																			// z
																			// value
			float box2x1 = (box2[2].getX() - offsets.getX()) * 1.5f; // Left x
																		// value
			float box2x2 = (box2[0].getX() - offsets.getX()) * 1.5f; // Right x
																		// value
			float box2y1 = (box2[3].getY() - offsets.getX()) * 1.5f; // Bottom y
																		// value
			float box2y2 = (box2[1].getY() - offsets.getX()) * 1.5f; // Top y
																		// value
			float box2z1 = box2[0].getZ() * 1.5f; // Lower z value
			float box2z2 = (boxCheckCollision.getDimension().getZ() + box2[0]
					.getZ()) * 1.5f; // Higher z value
			if ((box1z1 <= box2z1 && box2z1 <= box1z2)
					|| (box1z1 <= box2z2 && box2z2 <= box1z2)) {
				if ((box1x2 <= box2x1 && box2x1 <= box1x1)
						|| (box1x2 <= box2x2 && box2x2 <= box1x1)) {
					if ((box1y1 <= box2y1 && box2y1 <= box1y2)
							|| (box1y1 <= box2y2 && box2y2 <= box1y2)) {
						collide = true;
					}
				}
			}
		}
		return collide;
	}
}