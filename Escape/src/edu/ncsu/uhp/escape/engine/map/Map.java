package edu.ncsu.uhp.escape.engine.map;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;
import edu.ncsu.uhp.escape.engine.utilities.IRenderable;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Abstract container class for map that holds a maps dimensions.
 * 
 * @author Tyler Dodge and Bethany Vohlers
 *
 */
public abstract class Map<DataType> extends ActionObserver<Map<DataType>> {

	public static final int ACTION_CAPACITY = 100;

	private Point dimensions;

	public Map(Point dimensions) {
		super(ACTION_CAPACITY);
		this.dimensions = dimensions;
	}

	public Point getDimensions() {
		return dimensions;
	}
	
	/**
	 * Abstract method for collision detection with map.
	 * 
	 * @param checkCollision is the object to check collision with
	 * @return true or false if it collided.
	 */
	public abstract boolean doesCollide(ICollidable checkCollision);

	public abstract IRenderable getRenderable(Context context, GL10 gl);

	
}