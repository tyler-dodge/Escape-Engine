package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;

/**
 * Response of a push action and kills any actor with this response.
 * 
 * @author Brandon Walker
 *
 */
public class DieAfterCollisionResponse<DataType extends Actor<?>> extends ActionResponseDecorator<DataType> {
	
	public DieAfterCollisionResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof PushAction) {
			owner.pushAction(new DieAction(owner, owner));
		}
		return false;
	}

}
