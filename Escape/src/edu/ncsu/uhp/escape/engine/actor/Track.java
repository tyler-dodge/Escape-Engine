package edu.ncsu.uhp.escape.engine.actor;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.MovementResponse;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.map.Map;
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
public class Track extends ActionObserver<Track> implements ICollidable {

	private RenderSource source;
	private List<ICollision> collision;
	private ArrayList<Point> trackPoints;
	private static IRotation rotation = new ZAxisRotation(0);
	public static final int ACTION_CAPACITY = 100;

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
	public Track(RenderSource source, ArrayList<Point> trackPoints) {
		super(ACTION_CAPACITY);
		this.source = source;
		this.trackPoints = trackPoints;
		this.collision = calculateCollisionFromPoints(trackPoints);
	}

	private List<ICollision> calculateCollisionFromPoints(ArrayList<Point> trackPoints){
		List<ICollision> collision = new ArrayList<ICollision>();
		Point offset = ((ImageSource) source).getOffsets();
		BoxCollision boxCollision;
		/*for(int i = 0; i < trackPoints.size() - 2;){
			boxCollision = new BoxCollision(newPoint(trackPoints.get(i).))
		}
		
		*/
		return null;
	}
	
	public List<ICollision> getCollisions() {
		return this.collision;
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

	public Point getPosition() {
		return trackPoints.get(0);
	}

	public IRotation getRotation() {
		return rotation;
	}

	@Override
	public Track asDataType() {
		return this;
	}

	@Override
	public IActionResponse<Track> createDefaultResponse() {
		return new BaseActionResponse<Track>();
	}
}
