package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;

/**
 * Response to a push action, and sets the objects new position.
 * 
 * @author Brandon Walker
 *
 */
public class PushResponse<DataType extends Actor<?>> extends
		ActionResponseDecorator<DataType> {

	public PushResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		boolean superResponse = super.evalAction(owner, action);
		if (action.getTarget().equals(owner)) {
			if (action instanceof PushAction) {
				PushAction pushAction = (PushAction) action;
				owner.setPosition(pushAction.getTargetPosition());
			}
		}
		return superResponse;
	}
	@Override
	public String toString() {
		return super.toString()+"Push ";
	}

}
