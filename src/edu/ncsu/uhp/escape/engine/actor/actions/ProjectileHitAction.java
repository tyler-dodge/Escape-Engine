package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Action thrown when a projectile hits an actor
 * 
 * @author Brandon Walker
 *
 */
public class ProjectileHitAction extends Action<Point> {
	public static final String PROJECTILE_HIT_ACTION = "PROJECTILE_HIT";

	public ProjectileHitAction(ActionObserver<?> source,
			ActionObserver<?> target, Point data) {
		super(source, target, PROJECTILE_HIT_ACTION, data);
	}

}
