package edu.ncsu.uhp.escape.engine.utilities;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Interface used to represent rotations. All rotations should be immutable and
 * only contain references to IRotations of its own type.
 * 
 * @author Tyler Dodge
 * 
 */
public interface IRotation {
	/**
	 * Rotates this rotation by another rotation and returns the result. Should
	 * only implement special case if rotation is the same type as IRotation,
	 * otherwise should rotate by matrix.
	 * 
	 * @param rotation
	 * @return the resulting rotation
	 */
	public IRotation rotate(IRotation rotation);

	/**
	 * Applies the rotation to the point
	 * 
	 * @param point
	 * @return the resultant point
	 */
	public Point apply(Point point);

	public IRotation negative();

	public float[] toGlMatrix();
}
