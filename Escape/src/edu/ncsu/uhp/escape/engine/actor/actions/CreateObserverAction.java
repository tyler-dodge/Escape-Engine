package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;

/**
 * Action thrown when an observer is created. This is only used if the actionobserver is not intended to be rendered.
 * If it suppose to, use CreateActorAction.
 * 
 * @author Brandon Walker
 *
 */
public class CreateObserverAction extends Action<ActionObserver<?>> {
	public static final String CREATE_ACTION = "ACTOR_IS_CREATED";

	public CreateObserverAction(ActionObserver<?> source, ActionObserver<?> newObserver) {
		super(source, source, CREATE_ACTION, newObserver);
	}

}
