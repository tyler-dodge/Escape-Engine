package edu.ncsu.uhp.escape.engine.actor.actions;

import java.util.*;

import edu.ncsu.uhp.escape.engine.ActionObserver;

/**
 * Action thrown if gravity works on an object.
 * 
 * @author Brandon Walker
 *
 */
public class GravityAction extends Action<Date> {
	public static final String GRAVITY_ACTION = "GRAVITY_TICK";

	public GravityAction(ActionObserver<?> target) {
		super(target, target, GRAVITY_ACTION, new Date());
	}

}
