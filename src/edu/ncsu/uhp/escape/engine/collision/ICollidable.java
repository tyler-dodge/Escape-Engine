package edu.ncsu.uhp.escape.engine.collision;

import java.util.List;

import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public interface ICollidable {
	public Point getPosition();
	public IRotation getRotation();
	public List<ICollision> getCollisions();
}
