package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.ProjectileHitAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;

/**
 * Response of a push action from an enemy and kills any actor with this response.
 * 
 * @author Brandon Walker
 *
 */
public class DieAfterCollisionResponse<DataType extends Actor<?>> extends SingleEvalActionResponseDecorator<DataType> {
	
	public DieAfterCollisionResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof PushAction && action.getTarget().equals(owner)) {
			if(action.getSource() instanceof Enemy<?>){
				owner.pushAction(new DieAction(owner, owner));
			}
		}
		return false;
	}

}
