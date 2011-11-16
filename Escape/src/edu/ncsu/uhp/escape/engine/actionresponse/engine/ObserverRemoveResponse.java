package edu.ncsu.uhp.escape.engine.actionresponse.engine;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.RemoveAction;

/**
 * Engine responder to handle the Actor Die Action. Only the engine should hold this
 * response.
 * 
 * @author Tyler Dodge
 *
 */
public class ObserverRemoveResponse<DataType extends Engine> extends
		SingleEvalActionResponseDecorator<DataType> {

	public ObserverRemoveResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof RemoveAction) {
			RemoveAction removeAction = (RemoveAction) action;
			ActionObserver<?> actor = removeAction.getData();
			owner.removeActionObserver(actor);
		}
		return false;
	}

}
