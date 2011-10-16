package edu.ncsu.uhp.escape.engine.utilities;

import java.util.ArrayList;
import java.util.Arrays;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Level Point List resource, holds the entire track of each level in a series of points
 * @author Brandon Walker
 *
 */
public class LevelPointListResource {

	public static final Point[] FIRST_LEVEL_POINTS = 
		{new Point(10, 10, 0), new Point(0, 10, 0), new Point(10, 0, 0)};
	
	public static ArrayList<Point> getPoints(String level){
		if(level.equals("FIRST")){
			return convertToArrayList(FIRST_LEVEL_POINTS);
		}
		return null;
	}
	
	private static ArrayList<Point> convertToArrayList(Point[] points){
		return new ArrayList<Point>(Arrays.asList(points));
	}
}
