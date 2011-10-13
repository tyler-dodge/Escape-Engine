package edu.ncsu.uhp.escape.engine.actor;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.actions.PostMoveAction;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.*;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import java.util.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Base of all Actor classes. Handles management of actions, RenderSource, and
 * collisions
 * 
 * @author Tyler Dodge
 * 
 */
public abstract class Actor<DataType> extends ActionObserver<DataType>
		implements ICollidable {

	// Number of actions an actor can have in its queue
	public static final int ACTION_CAPACITY = 100;

	private Point position;
	private Point velocity;
	private IRotation rotation;
	private RenderSource source;
	private List<ICollision> collision;

	/**
	 * Constructs an actor with position, rotation, source, and an array of
	 * Collision Types. Action isCreated is enqueued to actions.
	 * 
	 * @param position
	 *            Actor's position in 3D space
	 * @param rotation
	 *            Actor's rotation
	 * @param source
	 *            Source for Actor's Renderable
	 * @param collision
	 *            List of Collisions used to determine if actors collide
	 */
	public Actor(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(ACTION_CAPACITY);
		this.position = position;
		this.rotation = rotation;
		this.source = source;
		this.collision = collision;
	}

	public void setRotation(IRotation rotation) {
		this.rotation = rotation;
	}

	public void rotate(IRotation rotation) {
		this.rotation = this.rotation.rotate(rotation);
	}

	public IRotation getRotation() {
		return rotation;
	}

	public Point getPosition() {
		return position;
	}

	public List<ICollision> getCollisions() {
		return this.collision;
	}

	public void setPosition(Point newPosition) {
		this.position = newPosition;
	}

	public Point getVelocity() {
		return velocity;
	}

	public void setVelocity(Point newVelocity) {
		velocity = newVelocity;
	}

	public void applyForce(Point force) {
		velocity.add(force);
	}

	/**
	 * This is the step between move and postmove actions where
	 * velocityTick is called via a velocity response.
	 */
	public void velocityTick() {
		Point originalPosition = this.position;
		position = this.position.add(velocity);
		this.pushAction(new PostMoveAction(this, originalPosition,
				this.position));
	}

	public IRenderable getRenderable(GL10 gl) {
		return source.getData(gl);
	}

	/**
	 * Checks whether or not any of this actor's collision items collide with
	 * the checkActor's collision items.
	 * 
	 * @param checkActor
	 *            the actor to be checked against
	 * @return whether or not this actor collides with checkActor
	 */
	public boolean doesCollide(ICollidable checkActor) {
		Point thisPosition = this.getPosition();
		IRotation thisRotation = this.getRotation();
		Point otherPosition = checkActor.getPosition();
		IRotation otherRotation = checkActor.getRotation();
		// iterates through this actor's collision items
		if (collision == null || checkActor.getCollisions() == null)
			return false;
		for (ICollision collisionItem : collision) {
			// iterates through other actor's collision items
			for (ICollision otherCollisionItem : checkActor.getCollisions()) {
				if (collisionItem.doesCollide(thisPosition, thisRotation,
						otherCollisionItem, otherPosition, otherRotation))
					return true;
			}
		}
		return false;
	}

	/**
	 * Moves the Actor in the specified direction. The Magnitude is determined
	 * by the implementation. Should send a Move Action;
	 * 
	 * @param direction
	 *            direction to be moved
	 * @return the final position of the actor
	 */
	public abstract Point move(IRotation direction);
	
}
