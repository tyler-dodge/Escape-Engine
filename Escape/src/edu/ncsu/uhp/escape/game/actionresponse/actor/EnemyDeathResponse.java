package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.game.actor.GameController;
import edu.ncsu.uhp.escape.game.actor.actions.EnemyDeathAction;

/**
 * Response of an Enemy dieing, it removes the enemy from the list of active enemies and then adds the worth to the player's money.
 * 
 * @author Brandon Walker
 *
 */
public class EnemyDeathResponse extends SingleEvalActionResponseDecorator<GameController> {
	
	public EnemyDeathResponse(IActionResponse<GameController> responder) {
		super(responder);
	}
	
	public boolean evalAction(GameController owner, Action<?> action) {
		if (action instanceof EnemyDeathAction) {
			owner.getActiveEnemies().remove(action.getSource());
			owner.addMoney(((Enemy<?>) action.getSource()).getWorth());
		}
		return false;
	}

}
