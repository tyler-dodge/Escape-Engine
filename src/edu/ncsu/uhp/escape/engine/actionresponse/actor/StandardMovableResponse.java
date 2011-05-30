package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.Actor;

/**
 * Standard movement response that incorporates a gravity and push response. 
 * 
 * @author Tyler Dodge
 *
 */
public class StandardMovableResponse<DataType extends Actor<?>> extends
		ActionResponseDecorator<DataType> {
	public StandardMovableResponse(IActionResponse<DataType> baseResponse) {
		super(new MovementResponse<DataType>(new GravityResponse<DataType>(
				-9.8f, new PushResponse<DataType>(baseResponse))));

	}
}
