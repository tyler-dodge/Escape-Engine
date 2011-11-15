package edu.ncsu.uhp.escape.engine.utilities.math;

/**
 * A vector
 * 
 * @author Tyler Dodge
 * 
 */
public class Point {
	private float x, y, z;

	/**
	 * Constructs a Point from the doubles x, y, and z.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Point getOrigin() {
		return new Point(0, 0, 0);
	}

	/**
	 * gets left side of X decimal
	 * 
	 * @return left side of X decimal
	 */
	public float getX() {
		return x;
	}

	/**
	 * gets left side of Y decimal
	 * 
	 * @return left side of Y decimal
	 */
	public float getY() {
		return y;
	}

	/**
	 * gets left side of Z decimal
	 * 
	 * @return left side of Z decimal
	 */
	public float getZ() {
		return z;
	}

	/**
	 * adds a point to this Point and returns the result.
	 * 
	 * @param point
	 * @return The sum of the two Points
	 */
	public Point add(Point point) {

		return add(point.x, point.y, point.z);
	}

	/**
	 * adds a scalar to each dimension in the Point.
	 * 
	 * @param num
	 *            scalar to be added
	 * @return The sum of the two Points
	 */
	public Point add(float num) {

		return add(num, num, num);
	}

	/**
	 * adds x, y, and z to this Point and returns the result. All the add
	 * methods funnel through this
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return The sum of the two Points
	 */
	public Point add(float newX, float newY, float newZ) {

		return new Point(this.x + newX, this.y + newY, this.z + newZ);
	}

	public Point subtract(Point subtractPoint) {
		return add(-subtractPoint.x, -subtractPoint.y, -subtractPoint.z);
	}

	/**
	 * Multiplies the Point by the scalar.
	 * 
	 * @param scalar
	 * @return Multiplied Point
	 */
	public Point multiply(float scalar) {

		return new Point(this.x * scalar, this.y * scalar, this.z * scalar);
	}

	/**
	 * Divides the Point by the scalar.
	 * 
	 * @param scalar
	 * @exception IllegalArgumentException
	 *                Scalar cannot be zero
	 * @return Divided Point
	 */
	public Point divide(float scalar) throws IllegalArgumentException {
		if (scalar == 0) {
			throw new IllegalArgumentException("scalar cannot be zero");
		}
		return new Point(this.x / scalar, this.y / scalar, this.z / scalar);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
