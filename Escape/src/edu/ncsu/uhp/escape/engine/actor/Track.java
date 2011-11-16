package edu.ncsu.uhp.escape.engine.actor;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
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
public class Track extends Npc<Track> {


	private ArrayList<Point> trackPoints;
	private static IRotation rotation = new ZAxisRotation(0f);

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
	public Track(Point position, RenderSource source, ArrayList<Point> trackPoints) {
		super(position, rotation, source, null);
		this.trackPoints = trackPoints;
		setCollision(calculateCollisionFromPoints(trackPoints));
	}
	
	public static List<ICollision> calculateCollisionFromPoints(ArrayList<Point> trackPoints){
		List<ICollision> collision = new ArrayList<ICollision>();
		BoxCollision boxCollision;
		Point origin;
		Point dimension;
		for(int i = 0; i < trackPoints.size() - 1; i++){
			dimension = new Point(Math.abs(trackPoints.get(i).getX() - trackPoints.get(i+1).getX()), Math.abs(trackPoints.get(i).getY() - trackPoints.get(i+1).getY()), Math.abs(trackPoints.get(i).getZ() - trackPoints.get(i+1).getZ()));
			
			if(trackPoints.get(i).getX() != trackPoints.get(i + 1).getX()){
				origin = (trackPoints.get(i).getX() < trackPoints.get(i + 1).getX())? trackPoints.get(i) : trackPoints.get(i + 1);
			}
			else{
				origin = (trackPoints.get(i).getY() < trackPoints.get(i + 1).getY())? trackPoints.get(i) : trackPoints.get(i + 1); 
			}
			dimension = dimension.add(new Point(5, 5, 5));
			origin = origin.add(new Point(-2.5f, -2.5f ,0));
			
			boxCollision = new BoxCollision(dimension, origin);
			collision.add(boxCollision);
		}
		
		return collision;
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

	@Override
	public Point move(IRotation direction) {
		return null;
	}
}
