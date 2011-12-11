package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.ProjectileHitAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;

public class ProjectileResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {
	
	private int damage;

	public ProjectileResponse(IActionResponse<DataType> responder, int damage) {
		super(responder);
		this.damage = damage;
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof PushAction && action.getTarget().equals(owner)) {
			action.getSource().pushAction(
					new ProjectileHitAction(owner, action.getSource(),
							damage));
		}
		return false;
	}

}