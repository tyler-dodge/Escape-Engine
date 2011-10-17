package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.ProjectileHitAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class ProjectileResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {

	public ProjectileResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof PushAction && action.getTarget().equals(owner)) {
			action.getSource().pushAction(
					new ProjectileHitAction(owner, action.getSource(),
							new Point(0, 0, 0)));
		}
		return false;
	}

}