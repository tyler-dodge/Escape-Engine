package edu.ncsu.uhp.escape.engine.collision;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

/**
 * Collision Detection
 * 
 * @author Tyler Dodge
 * 
 */
public class AABB_NarrowPhase implements INarrowCollision {
	private Point dimension;
	private Point offsets;

	private class ProjectedEdge {
		public ProjectedEdge(float startX, float startY, float endX, float endY) {
			if (startX > endX) {
				this.startX = endX;
				this.endX = startX;
			} else {
				this.startX = startX;
				this.endX = endX;
			}
			if (startY > endY) {
				this.startY = endY;
				this.endY = startY;
			} else {
				this.startY = startY;
				this.endY = endY;
			}
		}

		@Override
		public String toString() {
			return String.format("[Edge x: %.2f-%.2f, y: %.2f-%.2f]", startX,
					endX, startY, endY);
		}

		public float startX, startY;
		public float endX, endY;
	}

	/**
	 * Constructs a Collision Box from the Points dimension and offsets.
	 * 
	 * @param dimension
	 * @param offsets
	 */
	public AABB_NarrowPhase(Point dimension, Point offsets) {
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
	public Point[] getPoints(Point position, IRotation rotation) {
		Point[] vertices = new Point[4];
		vertices[0] = this.offsets;
		vertices[1] = new Point(0, this.dimension.getY(), 0).add(this.offsets);
		vertices[2] = new Point(this.dimension.getX(), this.dimension.getY(), 0)
				.add(this.offsets);
		vertices[3] = new Point(this.dimension.getX(), 0, 0).add(this.offsets);
		for (int i = 0; i < vertices.length; i++) {
			rotation.apply(vertices[i]);
			vertices[i] = vertices[i].add(position);
		}
		return vertices;
	}

	private ProjectedEdge[] getOriginProjectedEdges() {
		ProjectedEdge[] edges = new ProjectedEdge[4];
		edges[0] = new ProjectedEdge(0f, this.dimension.getX(), 0, 0);
		edges[1] = new ProjectedEdge(0f, 0, 0, this.dimension.getY());
		edges[2] = new ProjectedEdge(0f, this.dimension.getX(),
				this.dimension.getY(), this.dimension.getY());
		edges[3] = new ProjectedEdge(this.dimension.getX(),
				this.dimension.getX(), 0f, this.dimension.getY());
		return edges;
	}

	private ProjectedEdge[] getProjectedEdges(Point position,
			IRotation rotation, Point relativePosition,
			IRotation relativeRotation, Point relativeOffset) {
		ProjectedEdge[] edges = new ProjectedEdge[4];
		Point[] points = getPoints(position, rotation);
		for (int i = 0; i < 4; i++) {
			points[i] = points[i].subtract(relativePosition);
			points[i] = relativeRotation.negative().apply(points[i]);
			points[i] = points[i].subtract(relativeOffset);
		}
		for (int i = 0; i < 4; i++) {
			Point point1 = points[i];
			Point point2;
			if (i + 1 == 4)
				point2 = points[0];
			else
				point2 = points[i + 1];
			edges[i] = new ProjectedEdge(point1.getX(), point1.getY(),
					point2.getX(), point2.getY());
		}
		return edges;
	}

	private boolean doesEdgeIntersect(ProjectedEdge edge1) {
		boolean Xintersects = (edge1.startX >= 0 && edge1.startX <= dimension
				.getX())
				|| (edge1.endX >= 0 && edge1.endX <= dimension.getX())
				|| (dimension.getX() >= edge1.startX && dimension.getX() <= edge1.endX);
		boolean Yintersects = (edge1.startY >= 0 && edge1.startY <= dimension
				.getY())
				|| (edge1.endY >= 0 && edge1.endY <= dimension.getY())
				|| (dimension.getY() >= edge1.startY && dimension.getY() <= edge1.endY);
		return Xintersects && Yintersects;

	}

	/**
	 * Checks for collision
	 */
	public boolean doesCollideNarrow(Point thisPosition,
			IRotation thisRotation, ICollision checkCollide,
			Point checkPosition, IRotation checkRotation) {
		boolean collide = false;
		AABB_NarrowPhase boxCheckCollision = null;
		if (checkCollide instanceof MultiPhaseCollision) {
			boxCheckCollision = (AABB_NarrowPhase) ((MultiPhaseCollision) checkCollide).narrow;
		} else if (checkCollide instanceof AABB_NarrowPhase) {
			boxCheckCollision = (AABB_NarrowPhase) checkCollide;
		}
		if (boxCheckCollision != null) {
			ProjectedEdge[] checkEdges = boxCheckCollision.getProjectedEdges(
					checkPosition, checkRotation, thisPosition, thisRotation,
					offsets);
			for (int x = 0; x < 4; x++) {
				ProjectedEdge checkEdge = checkEdges[x];
				if (doesEdgeIntersect(checkEdge)) {
					return true;
				}
			}
		}
		return collide;
	}
}