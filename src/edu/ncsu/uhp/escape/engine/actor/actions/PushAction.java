package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.utilities.math.*;

/**
 * The action that is thrown typically if two actors collide.
 * 
 * @author Tyler Dodge
 *
 */
public class PushAction extends Action<Point> {
	public static final String PUSH_ACTION = "ACTOR_IS_PUSHED";

	public PushAction(ActionObserver<?> source, ActionObserver<?> target,
			Point newPosition) {
		this(source, target, newPosition, Action.STANDARD_PRIORITY);
	}

	public PushAction(ActionObserver<?> source, ActionObserver<?> target,
			Point newPosition, int priority) {
		super(source, target, PUSH_ACTION, newPosition, priority);
	}

	public Point getTargetPosition() {
		return this.getData();
	}

}
