package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateRubbleAction;
import edu.ncsu.uhp.escape.engine.actor.actions.ProjectileHitAction;

/**
 * Response to a projectile hit action that throws the create rubble action
 * 
 * @author Brandon Walker
 *
 */
public class TreeBurnAfterImpactResponse<DataType extends Actor<?>>
	extends SingleEvalActionResponseDecorator<DataType> {
	
	public TreeBurnAfterImpactResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		//TODO: Switch to respond to a projectile hit action
		if (action instanceof ProjectileHitAction && action.getTarget().equals(owner)) {
			owner.pushAction(new CreateRubbleAction(owner, "Rubble", owner.getRotation()));
		}
		return false;
	}
}