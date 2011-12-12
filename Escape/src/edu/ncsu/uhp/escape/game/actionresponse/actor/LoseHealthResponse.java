package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.game.actor.GameController;
import edu.ncsu.uhp.escape.game.actor.actions.LoseHealthAction;

public class LoseHealthResponse extends SingleEvalActionResponseDecorator<GameController>{

	public LoseHealthResponse(IActionResponse<GameController> responder) {
		super(responder);
	}
	
	public boolean evalAction(GameController owner, Action<?> action) {
		if (action instanceof LoseHealthAction) {
			owner.damageHealth(((LoseHealthAction)action).getData());
		}
		return false;
	}
}
