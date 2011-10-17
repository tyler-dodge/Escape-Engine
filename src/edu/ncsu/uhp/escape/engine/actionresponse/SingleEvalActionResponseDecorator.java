package edu.ncsu.uhp.escape.engine.actionresponse;

import edu.ncsu.uhp.escape.engine.actor.actions.Action;

public abstract class SingleEvalActionResponseDecorator<DataType> 
extends ActionResponseDecorator<DataType> {

	public SingleEvalActionResponseDecorator(IActionResponse<DataType> responder) {
		super(responder);
	}
	public abstract boolean evalAction(DataType owner, Action<?> action);
	/**
	 * All children should call their super evalAction, otherwise decorator
	 * pattern is not happening
	 */
	public boolean evalOwnerAction(DataType owner, Action<?> action) {
		boolean superResponse=super.evalOwnerAction(owner, action);
		return evalAction(owner,action) || superResponse;
	}
	
	public boolean evalReceivedAction(DataType owner, Action<?> action) {
		boolean superResponse=super.evalReceivedAction(owner, action);
		return evalAction(owner,action) || superResponse;
	}
}
