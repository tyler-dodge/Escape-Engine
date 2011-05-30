package edu.ncsu.uhp.escape.engine.map;

import android.content.Context;

import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Tile is the basic constructor of maps where each tile has dimensions,
 * offsets, and collision properties.
 * 
 * @author Tyler Dodge and Bethany Vohlers
 *
 */
public class Tile {

	private boolean generateCollision;
	private ICollision collisionBox;
	private Point dimensions;
	private static final Point DEFAULT_DIMENSIONS = new Point(25, 25, 0);
	private static final Point DEFAULT_OFFSETS = new Point(0, 0, 0);
	private int resourceId;

	public static Tile fromSourceId(Context context, int id) {
		ICollision collision = new BoxCollision(DEFAULT_DIMENSIONS,
				DEFAULT_OFFSETS);
		return new Tile(id, DEFAULT_DIMENSIONS, false, collision);
	}

	public int getResourceId() {
		return resourceId;
	}

	Tile(int resourceId, Point dimensions, boolean generateCollision,
			ICollision collisionBox) {
		this.dimensions = dimensions;
		this.resourceId = resourceId;
		this.collisionBox = collisionBox;
	}

	public boolean doesGenerateCollision() {
		return generateCollision;
	}

	public ICollision getCollisionBox() {
		return collisionBox;
	}

	public boolean doesCollide(BoxCollision collider) {
		return false;
	}

	public Point getDimensions() {
		return dimensions;
	}
}
