package edu.ncsu.uhp.escape.engine.actionresponse.engine;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateObserverAction;

/**
 * Engine responder to handle the Observer Created Action. Only the engine should hold this
 * response.
 * 
 * @author Brandon Walker
 *
 */
public class CreateObserverResponse<DataType extends Engine> extends
		SingleEvalActionResponseDecorator<DataType> {

	public CreateObserverResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof CreateObserverAction) {
			CreateObserverAction createAction = (CreateObserverAction) action;
			ActionObserver<?> observer = createAction.getData();
			owner.addActionObserver(observer);
		}
		return false;
	}
}
