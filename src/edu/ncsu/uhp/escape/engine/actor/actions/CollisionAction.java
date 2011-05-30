package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.Actor;

public class CollisionAction extends Action<Actor<?>> {
	public static final String COLLISION_ACTION = "ACTOR_COLLIDED";

		public CollisionAction(ActionObserver<?> source, Actor<?> target, int priority) {
			super(source, source, COLLISION_ACTION, target, priority);
		}

}
