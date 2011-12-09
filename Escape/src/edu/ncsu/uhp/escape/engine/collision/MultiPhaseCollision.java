package edu.ncsu.uhp.escape.engine.collision;

import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;


public abstract class MultiPhaseCollision implements ICollision{
	public IBroadCollision broad;
	public INarrowCollision narrow;
	public MultiPhaseCollision(IBroadCollision broad, INarrowCollision narrow){
		this.broad = broad;
		this.narrow = narrow;
	}
	
	public boolean doesCollide(Point thisPosition, IRotation thisRotation,
			ICollision checkCollide, Point checkPosition,
			IRotation checkRotation) {
		boolean collide = false;
		if (broad.doesCollideBroad(thisPosition, thisRotation, checkCollide, checkPosition, checkRotation)){
			if(narrow.doesCollideNarrow(thisPosition, thisRotation, checkCollide, checkPosition, checkRotation)){
				collide = true;
			}
		}
		return collide;
	}
}