package edu.ncsu.uhp.escape.engine.utilities;

import java.util.ArrayList;

import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.Npc;
import edu.ncsu.uhp.escape.engine.actor.actions.MoveAction;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * NodalTrack holds a list of points for an object to move on and a iterator for the current point
 * it is moving towards. The travel method calculates the direction and pushes the actor towards it.
 * @author Brandon Walker
 *
 */
public class NodalTrack {

	private ArrayList<Point> points;
	private int trackPosition;
	
	public static NodalTrack getInstanceForClass(Npc<?> actor, Point target){
		return new NodalTrack(TrackPointDictionary.getInstance().getClassPointList(actor, target));
	}
	
	public static NodalTrack getInstanceForObject(Npc<?> actor, Point target){
		return new NodalTrack(TrackPointDictionary.getInstance().getUniquePointList(actor, target));
	}
	
	public static NodalTrack getInstanceForTrackLevel(String level){
		return new NodalTrack(TrackPointDictionary.getInstance().getLevelPointList(level));
	}
	
	/**
	 * Calculates the direction to travel in to reach a certain point. If the angle is the same as the 
	 * actor's current direction, it will move the actor closer to the point. If the angle changes, as 
	 * in it has passed or is on the point, the iterator increases, moving the actor towards the next 
	 * point. If the iterator reaches the end of the list by hitting the last point, trackPosition increases
	 * to points.size() and movement stops. 
	 * 
	 * Therefore all tracks either need to end on a destination that will either destroy (collide) the object 
	 * or have it move again, either by creating a new NodalTrack or moving it otherwise.
	 * 
	 * @param actor The actor to Move
	 */
	public void travel(Actor<?> actor){
		//Currently no 3d support
		
		if(points != null && trackPosition <= points.size()){
	
			Point targetPoint = points.get(trackPosition);
			Point currentPoint = actor.getPosition();
			
			if(targetPoint.equals(currentPoint)){
				trackPosition ++;
				targetPoint = points.get(trackPosition);
			}
			
			// Really really really really
			// Really really really really
			// Really really really really
			// Really really really really
			// Really really really really
			// lazy rotational movement
			float relX = currentPoint.getX() - targetPoint.getX();
			float relY = currentPoint.getY() - targetPoint.getY();
			float angle = (float) Math.atan(relY / relX) + 3.14f;
			if (relX == 0 && relY == 0) {
				angle = 0;
			} else if (relY == 0) {
				if (relX > 0) {
					angle = 0;
				} else {
					angle = 3.14f;
				}
			} else if (relX == 0) {
				if (relY > 0) {
					angle = 1.57f;
				} else
					angle = 4.71f;
			}
			if (relX < 0) {
				angle += 3.14;
			}
			
			if(Math.abs(angle - ((ZAxisRotation) actor.getRotation()).getAngle()) < .1f){
				trackPosition ++;
				travel(actor);
				return;
			}
			
			actor.pushAction(new MoveAction(actor, actor.getPosition(), new ZAxisRotation(angle)));
		}
	}
	
	public NodalTrack(ArrayList<Point> points){
		this.points = points;
	}
	
	public ArrayList<Point> getPoints(){
		return points;
	}
	
	public void setPoints(ArrayList<Point> points){
		this.points = points;
	}
	
	public void setPointsByTrackPointDictionaryForObject(Npc<?> actor, Point target){
		this.points = TrackPointDictionary.getInstance().getUniquePointList(actor, target);
	}
	
	public void setPointsByTrackPointDictionaryForClass(Npc<?> actor, Point target){
		this.points = TrackPointDictionary.getInstance().getClassPointList(actor, target);
	}
	
	public int getTrackPosition(){
		return trackPosition;
	}
	
	public void setTrackPosition(int trackPosition){
		this.trackPosition = trackPosition;
	}
}