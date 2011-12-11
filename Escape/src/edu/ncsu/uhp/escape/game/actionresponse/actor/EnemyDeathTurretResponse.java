package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Turret;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.game.actor.actions.EnemyDeathAction;

/**
 * Response of an Enemy dieing, it removes the enemy from the list of any potential turrets.
 * 
 * @author Brandon Walker
 *
 */
public class EnemyDeathTurretResponse<DataType extends Turret<?>> extends SingleEvalActionResponseDecorator<DataType> {
	
	public EnemyDeathTurretResponse(IActionResponse<DataType> responder) {
		super(responder);
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof EnemyDeathAction) {
			owner.getEnemiesInRange().remove(action.getSource());
		}
		return false;
	}
}
