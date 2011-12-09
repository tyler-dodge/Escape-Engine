package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Turret;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.PostMoveAction;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;

/**
 * Is the responder for turret collisions. It will set the turret colliding to true
 * 
 * @author Brandon Walker
 *
 */
public class CheckWithinTurretRangeResponse<DataType extends Turret<?>> extends SingleEvalActionResponseDecorator<DataType> {
	
	public CheckWithinTurretRangeResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action.getSource() instanceof Enemy<?>) {
			if(action instanceof PostMoveAction){
				if(owner.isWithinRange((ICollidable) action.getSource())){
					owner.addEnemyToAttackList((Enemy<?>) action.getSource());
				}
			}
		}
		return false;
	}

}
