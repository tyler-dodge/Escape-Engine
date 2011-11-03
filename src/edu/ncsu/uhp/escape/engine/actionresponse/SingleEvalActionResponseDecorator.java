package edu.ncsu.uhp.escape.engine.actionresponse;

import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.utilities.Profiler;

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
	@Override
	public boolean evalOwnerAction(DataType owner, Action<?> action) {
		boolean superResponse=super.evalOwnerAction(owner, action);
		Profiler.getInstance().startSection("Eval single action"+this.toString());
		superResponse=evalAction(owner,action) || superResponse;
		Profiler.getInstance().endSection();
		return superResponse;
	}
	
	@Override
	public boolean evalReceivedAction(DataType owner, Action<?> action) {
		boolean superResponse=super.evalReceivedAction(owner, action);
		Profiler.getInstance().startSection("Eval single action"+this.toString());
		superResponse=evalAction(owner,action) || superResponse;
		Profiler.getInstance().endSection();
		return superResponse;
	}
}
