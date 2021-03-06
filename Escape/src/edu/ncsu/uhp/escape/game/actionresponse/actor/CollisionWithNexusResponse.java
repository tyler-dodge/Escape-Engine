package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Nexus;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;
import edu.ncsu.uhp.escape.game.actor.actions.LoseHealthAction;

/**
 * Response of a collision with the nexus, moves the enemy back to where it started.
 * 
 * @author Brandon Walker
 *
 */
public class CollisionWithNexusResponse<DataType extends Enemy<?>> extends SingleEvalActionResponseDecorator<DataType> {
	
	public CollisionWithNexusResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if(action.getSource() instanceof Nexus && action.getTarget().equals(owner)){
			if (action instanceof PushAction) {
				owner.setPosition(owner.getTrackPoints().getPoints().get(0));
				owner.getTrackPoints().setTrackPosition(0);
				owner.pushAction(new LoseHealthAction(owner, owner, 1));
			}
		}
		return false;
	}

}
