package edu.ncsu.uhp.escape.game.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;

/**
 * Action thrown when a projectile hits an actor
 * 
 * @author Brandon Walker
 *
 */
public class LoseHealthAction extends Action<Integer> {
	public static final String HEALTH_ACTION = "HEALTH_LOST";

	public LoseHealthAction(ActionObserver<?> source,
			ActionObserver<?> target, int data) {
		super(source, target, HEALTH_ACTION, data);
	}

}
