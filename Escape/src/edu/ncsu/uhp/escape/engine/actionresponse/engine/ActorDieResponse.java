package edu.ncsu.uhp.escape.engine.actionresponse.engine;

import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;

/**
 * Engine responder to handle the Actor Die Action. Only the engine should hold this
 * response.
 * 
 * @author Tyler Dodge
 *
 */
public class ActorDieResponse<DataType extends Engine> extends
		SingleEvalActionResponseDecorator<DataType> {

	public ActorDieResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof DieAction) {
			DieAction dieAction = (DieAction) action;
			Actor<?> actor = dieAction.getData();
			owner.removeActor(actor);
		}
		return false;
	}

}
