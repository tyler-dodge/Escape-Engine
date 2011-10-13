package edu.ncsu.uhp.escape.engine.utilities;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ncsu.uhp.escape.engine.actor.Npc;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * TrackPointDictionary implements the FlyWeight design pattern in that it holds 3 different Hashmaps to condense on 
 * TrackPointList references. Since every enemy and potentially every projectile will have a common or reoccuring 
 * trajectory/path, keeping them in a hashmap helps reduce memory and calculation strain.
 * 
 * @author Brandon Walker
 *
 */
public class TrackPointDictionary {
	private static TrackPointDictionary trackPointDictionary;
	private HashMap<String, ArrayList<Point>> levelPointListDictionary;
	private HashMap<Class<?>, ArrayList<Point>> pointListClassDictionary;
	private HashMap<Integer, ArrayList<Point>> pointListUniqueDictionary;
	
	private TrackPointDictionary(){
		pointListClassDictionary = new HashMap<Class<?>, ArrayList<Point>>();
		pointListUniqueDictionary = new HashMap<Integer, ArrayList<Point>>();
		levelPointListDictionary = new HashMap<String, ArrayList<Point>>();
	};
	
	public static TrackPointDictionary getInstance(){
		if(trackPointDictionary == null){
			trackPointDictionary = new TrackPointDictionary();
		}
		return trackPointDictionary;
	}
	
	/**
	 * Either gets or generates a unique path for the actor based on it's target
	 * point.
	 */
	public ArrayList<Point> getUniquePointList(Npc<?> actor, Point point){
		int hashCode = System.identityHashCode(actor);
		ArrayList<Point> points = pointListUniqueDictionary.get(hashCode);
		if(points == null){
			points = calculatePointList(actor, point);
			pointListUniqueDictionary.put(hashCode, points);
		}
		return points;
	}
	
	/**
	 * Either gets or generates a unique path for the entire objects class.
	 * Mainly used for Enemies/effect/etc
	 */
	public ArrayList<Point> getClassPointList(Npc<?> actor, Point point){
		ArrayList<Point> points = pointListClassDictionary.get(actor.getClass());
		if(points == null){
			points = calculatePointList(actor, point);
			pointListClassDictionary.put(actor.getClass(), points);
		}
		return points;
	}
	
	/**
	 * Either gets or generates a path based on the current level
	 */
	public ArrayList<Point> getLevelPointList(String level){
		ArrayList<Point> points = levelPointListDictionary.get(level);
		if(points == null){
			points = LevelPointListResource.getPoints(level);
			levelPointListDictionary.put(level, points);
		}
		return points;
	}
	
	/**
	 * Right now since there is no Z direction, we have no need for calculating the points as there is only 
	 * one dimension of movement in the direction of the actors rotation. In the future when we implement
	 * 3 dimensional movement, there will need to be a parabola calculated for points.
	 */
	@Deprecated
	public ArrayList<Point> calculatePointList(Npc<?> actor, Point point){
		//TODO: Implement trajectory calculation, right now no need for it.
		return null;
	}
}
