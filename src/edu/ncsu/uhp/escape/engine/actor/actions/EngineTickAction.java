package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;

/**
 * Action thrown when the engine ticks.
 * 
 * @author Brandon Walker
 *
 */
public class EngineTickAction extends Action<Integer> {
	public static final String TICK_ACTION = "ENGINE_TICKED";
	
	public EngineTickAction(ActionObserver<?> source) {
		this(source,  Action.STANDARD_PRIORITY);
	}

	public EngineTickAction(ActionObserver<?> source, int priority) {
		super(source, TICK_ACTION, 0, priority);
	}

}
