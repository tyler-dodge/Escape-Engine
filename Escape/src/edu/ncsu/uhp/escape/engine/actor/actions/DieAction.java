package edu.ncsu.uhp.escape.engine.actor.actions;


import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.Actor;

/**
 * Action thrown when an Actor Dies
 * 
 * @author Tyler Dodge
 *
 */
public class DieAction extends Action<Actor<?>> {
	public static final String DIE_ACTION = "ACTOR_DIES";

	public DieAction(ActionObserver<?> source, Actor<?> target) {
		this(source, target, Action.STANDARD_PRIORITY);
		// TODO Auto-generated constructor stub
	}

	public DieAction(ActionObserver<?> source, Actor<?> target, int priority) {
		super(source, source, DIE_ACTION, target, priority);
		// TODO Auto-generated constructor stub
	}

}
