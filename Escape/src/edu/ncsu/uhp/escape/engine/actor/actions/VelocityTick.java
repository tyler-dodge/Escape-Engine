package edu.ncsu.uhp.escape.engine.actor.actions;

import java.util.*;

import edu.ncsu.uhp.escape.engine.ActionObserver;

/**
 * Tick action if the object is moving automatically.
 * 
 * @author Tyler Dodge
 *
 */
public class VelocityTick extends Action<Date> {
	public static final String VELOCITY_TICK_ACTION = "VELOCITY_TICK";

	public VelocityTick(ActionObserver<?> source, Date data) {
		super(source, VELOCITY_TICK_ACTION, data);
	}

}
