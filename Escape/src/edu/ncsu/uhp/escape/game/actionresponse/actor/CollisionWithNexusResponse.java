package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Nexus;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CollisionAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;

/**
 * Response of a collision with the nexus, moves the enemy back to where it started.
 * 
 * @author Brandon Walker
 *
 */
public class CollisionWithNexusResponse<DataType extends Enemy<?>> extends ActionResponseDecorator<DataType> {
	
	public CollisionWithNexusResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof PushAction) {
			if(action.getSource() instanceof Nexus){
				owner.setPosition(owner.getTrackPoints().getPoints().get(0));
				owner.getTrackPoints().setTrackPosition(0);
				//TODO: Health goes down here		
			}
		}
		return true;
	}

}
