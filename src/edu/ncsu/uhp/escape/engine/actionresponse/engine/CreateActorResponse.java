package edu.ncsu.uhp.escape.engine.actionresponse.engine;

import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;

/**
 * Engine responder to handle the Actor Created Action. Only the engine should hold this
 * response.
 * 
 * @author Tyler Dodge
 *
 */
public class CreateActorResponse<DataType extends Engine> extends
		SingleEvalActionResponseDecorator<DataType> {

	public CreateActorResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof CreateActorAction) {
			CreateActorAction createAction = (CreateActorAction) action;
			Actor<?> actor = createAction.getData();
			owner.addActor(actor);

		}
		return false;
	}
}
