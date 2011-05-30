package edu.ncsu.uhp.escape.engine.utilities;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class ZAxisRotation implements IRotation {
	private float angle;
	private float[] matrix;

	public ZAxisRotation(float angle) {
		this.angle = angle;
	}

	public static ZAxisRotation getIdentity() {
		return new ZAxisRotation(0);
	}

	public ZAxisRotation(float[] matrix) {
		this.angle = (float) Math.acos(matrix[3]);
		if (matrix[0] < 0)
			this.angle = this.angle + (float) Math.PI;
	}

	public float getAngle() {
		return angle;
	}

	public ZAxisRotation rotate(IRotation rotation) {
		float newAngle;
		if (rotation instanceof ZAxisRotation) {
			ZAxisRotation otherAngles = (ZAxisRotation) rotation;
			newAngle = otherAngles.angle + this.angle;

		} else {
			newAngle = 0;
		}
		return new ZAxisRotation(newAngle);
	}

	public float[] toGlMatrix() {

		if (matrix == null) {
			matrix = new float[16];
			// http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToMatrix/index.htm
			float sa = (float) Math.sin(angle);
			float ca = (float) Math.cos(angle);
			matrix[0] = ca;
			matrix[1] = -sa;
			matrix[2] = 0;
			matrix[3] = 0;

			matrix[4] = sa;
			matrix[5] = ca;
			matrix[6] = 0;
			matrix[7] = 0;

			matrix[8] = 0;
			matrix[9] = 0;
			matrix[10] = 1;
			matrix[11] = 0;

			matrix[12] = 0;
			matrix[13] = 0;
			matrix[14] = 0;
			matrix[15] = 1;
		}
		return matrix;
	}

	public Point apply(Point point) {
		float sa = (float) Math.sin(angle);
		float ca = (float) Math.cos(angle);

		float newX = point.getX() * ca + (point.getY() * sa);
		float newY = point.getX() * (-sa) + (ca * point.getY());
		float newZ = point.getZ();
		return new Point(newX, newY, newZ);
	}

	public IRotation negative() {
		return new ZAxisRotation(-angle);
	}
}
