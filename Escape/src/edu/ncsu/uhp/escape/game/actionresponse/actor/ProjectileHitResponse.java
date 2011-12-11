package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.ProjectileHitAction;
import edu.ncsu.uhp.escape.game.actor.actions.EnemyDeathAction;

public class ProjectileHitResponse<DataType extends Enemy<?>> extends
		SingleEvalActionResponseDecorator<DataType> {
	
	public ProjectileHitResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof ProjectileHitAction && action.getTarget().equals(owner)) {
			owner.subtractHealth(((ProjectileHitAction)action).getData());
			if(owner.getHealth() <= 0){
				owner.pushAction(new EnemyDeathAction(owner, action.getSource()));
				owner.pushAction(new DieAction(owner, owner));
			}
		}
		return false;
	}

}