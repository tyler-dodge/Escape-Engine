package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.GravityAction;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Responds to a gravity action and applies a force specified by the constructor.
 * 
 * @author Tyler Dodge
 *
 */
public class GravityResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {
	private float gravity;

	public GravityResponse(float gravity, IActionResponse<DataType> responder) {
		super(responder);
		this.gravity = gravity;
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof GravityAction) {
			owner.applyForce(new Point(0, 0, gravity));
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + "Gravity ";
	}

}
