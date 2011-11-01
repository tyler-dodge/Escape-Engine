package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
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
		 SingleEvalActionResponseDecorator<DataType> {

	public MoveOnTickResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof EngineTickAction) {
			owner.move(owner.getRotation());
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + "Engine Tick ";
	}

}
