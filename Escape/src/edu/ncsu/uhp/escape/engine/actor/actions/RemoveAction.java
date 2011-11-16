package edu.ncsu.uhp.escape.engine.actor.actions;


import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.Actor;

/**
 * Action thrown when an Observer is removed
 * 
 * @author Brandon Walker
 *
 */
public class RemoveAction extends Action<ActionObserver<?>> {
	public static final String DIE_ACTION = "ACTOR_DIES";

	public RemoveAction(ActionObserver<?> source, ActionObserver<?> target) {
		this(source, target, Action.STANDARD_PRIORITY);
		// TODO Auto-generated constructor stub
	}

	public RemoveAction(ActionObserver<?> source, ActionObserver<?> target, int priority) {
		super(source, source, DIE_ACTION, target, priority);
		// TODO Auto-generated constructor stub
	}

}
