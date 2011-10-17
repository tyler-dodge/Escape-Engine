package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;

/**
 * If an actor contains this response, it dies after it reaches the specified 
 * amount of engine ticks.
 * 
 * @author Tyler Dodge
 *
 */
public class DieAfterTicksResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {
	private int totalTicks = 0;
	private final int maxTicks;

	public DieAfterTicksResponse(IActionResponse<DataType> responder,
			int maxTicks) {
		super(responder);
		this.maxTicks = maxTicks;

	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof EngineTickAction && totalTicks++>=maxTicks) {
			owner.pushAction(new DieAction(owner, owner));
		}
		return false;
	}

}