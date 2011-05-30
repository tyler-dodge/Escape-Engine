package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Represents the action that an actor moves
 * 
 * @author Tyler Dodge
 * 
 */
public class MoveAction extends Action<Point> {
	public static final String MOVE_ACTION = "ACTOR_MOVES";

	private IRotation direction;

	public MoveAction(ActionObserver<?> source, Point originalPosition, IRotation direction) {
		this(source, originalPosition, direction, Action.STANDARD_PRIORITY);
	}

	public MoveAction(ActionObserver<?> source, Point originalPosition,
			IRotation direction, int priority) {
		super(source, MOVE_ACTION, originalPosition, priority);
		this.direction = direction;
	}

	public Point getOriginalPosition() {
		return this.getData();
	}

	public IRotation getDirection() {
		return direction;
	}

}
