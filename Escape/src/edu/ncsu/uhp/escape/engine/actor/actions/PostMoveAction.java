package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Action thrown after a movement is made, typically is used to check collision
 * 
 * @author Tyler Dodge
 *
 */
public class PostMoveAction extends Action<Point> {
	public static final String POST_MOVE_ACTION = "ACTOR_HAS_MOVED";
	private Point finalPosition;

	public PostMoveAction(ActionObserver<?> source, Point originalPosition,
			Point finalPosition) {
		this(source, originalPosition, finalPosition, Action.STANDARD_PRIORITY);
	}

	public PostMoveAction(ActionObserver<?> source, Point originalPosition,
			Point finalPosition, int priority) {
		super(source, POST_MOVE_ACTION, originalPosition, priority);
	}

	public Point getOriginalPosition() {
		return this.getData();
	}

	public Point getFinalPosition() {
		return finalPosition;
	}
}
