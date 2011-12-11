package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;

/**
 * Action thrown when a projectile hits an actor
 * 
 * @author Brandon Walker
 *
 */
public class ProjectileHitAction extends Action<Integer> {
	public static final String PROJECTILE_HIT_ACTION = "PROJECTILE_HIT";

	public ProjectileHitAction(ActionObserver<?> source,
			ActionObserver<?> target, int data) {
		super(source, target, PROJECTILE_HIT_ACTION, data);
	}

}
