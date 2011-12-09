package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Nexus;
import edu.ncsu.uhp.escape.engine.actor.Turret;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;

/**
 * Is the responder for turret collisions. It will set the turret colliding to true
 * 
 * @author Brandon Walker
 *
 */
public class TurretCollisionResponse<DataType extends Turret<?>> extends SingleEvalActionResponseDecorator<DataType> {
	
	public TurretCollisionResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if(action.getTarget().equals(owner) && !owner.isPlaced()){
			if (action instanceof PushAction) {
				owner.setColor((ICollidable) action.getSource());
			}
		}
		return false;
	}

}
