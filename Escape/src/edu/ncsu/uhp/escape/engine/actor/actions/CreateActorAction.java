package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.Actor;

/**
 * Action thrown when an actor is created.
 * 
 * @author Brandon Walker
 *
 */
public class CreateActorAction extends Action<Actor<?>> {
	public static final String CREATE_ACTION = "ACTOR_IS_CREATED";

	public CreateActorAction(ActionObserver<?> source, Actor<?> newActor) {
		super(source, source, CREATE_ACTION, newActor);
	}

}
