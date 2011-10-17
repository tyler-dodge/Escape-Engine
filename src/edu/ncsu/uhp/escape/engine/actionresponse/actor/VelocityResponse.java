package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.VelocityTick;

/**
 * Responds to a velocity tick that calls the owners velocityTick method,
 * throwing the post move action. Essentially is the step between move and 
 * post move actions.
 * 
 * @author Tyler Dodge
 *
 */
public class VelocityResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {

	public VelocityResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action.getTarget().equals(owner)) {
			if (action instanceof VelocityTick) {
				owner.velocityTick();
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return super.toString()+"Velocity ";
	}

}
