package edu.ncsu.uhp.escape.engine.collision;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

/**
 * Collision Detection
 * 
 * @author Tim Mervine
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
			return String.format("[Edge x: %.2f-%.2f, y: %.2f-%.2f]", startX,endX,startY,endY);
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
		ZAxisRotation angle = new ZAxisRotation(rotation.toGlMatrix());
		vertices[0] = this.offsets;
		vertices[1] = new Point(0, this.dimension.getY(), 0).add(this.offsets);
		vertices[2] = new Point(this.dimension.getX(), this.dimension.getY(), 0)
				.add(this.offsets);
		vertices[3] = new Point(this.dimension.getX(), 0, 0).add(this.offsets);
		for (int i = 0; i < vertices.length; i++) {
			angle.apply(vertices[i]);
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
			edges[i] = new ProjectedEdge(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		}
		return edges;
	}

	private boolean doEdgesIntersect(ProjectedEdge edge1, ProjectedEdge edge2) {
		return ((edge1.startX >= edge2.startX && edge1.startX <= edge2.endX) || (edge1.endX >= edge2.startX && edge1.endX <= edge2.endX))
				&& ((edge1.startY >= edge2.startY && edge1.startY <= edge2.endY) || (edge1.endY >= edge2.startY && edge1.endY <= edge2.endY));
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
			ProjectedEdge[] thisEdges = this.getOriginProjectedEdges();
			ProjectedEdge[] checkEdges = boxCheckCollision.getProjectedEdges(
					checkPosition, checkRotation, thisPosition, thisRotation,
					offsets);
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					ProjectedEdge thisEdge = thisEdges[x];
					ProjectedEdge checkEdge = checkEdges[y];
					if (doEdgesIntersect(thisEdge, checkEdge)) {
						return true;
					}
				}
			}
		}
		return collide;
	}
}