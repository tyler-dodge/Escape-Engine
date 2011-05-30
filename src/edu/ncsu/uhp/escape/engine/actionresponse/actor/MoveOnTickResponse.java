package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;

/**
 * If an actor has this response, it will move automatically in accordance to 
 * engine ticks. Most projectiles will have this response.
 * 
 * @author Tyler Dodge
 *
 */
public class MoveOnTickResponse<DataType extends Actor<?>> extends
		ActionResponseDecorator<DataType> {

	public MoveOnTickResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		boolean superResponse = super.evalAction(owner, action);
		if (action instanceof EngineTickAction) {
			owner.move(owner.getRotation());
		}
		return superResponse;
	}

	@Override
	public String toString() {
		return super.toString() + "Engine Tick ";
	}

}
