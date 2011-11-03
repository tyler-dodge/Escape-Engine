package edu.ncsu.uhp.escape.engine.actionresponse;

import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.utilities.Profiler;

/**
 * The basic Decorator for Action Responders. Allows all action response to be
 * extremely modular. In case it was not obvious from the names, it implements
 * the Decorator pattern
 * 
 * @author Tyler Dodge
 * 
 * @param <DataType>
 *            Determines what kind of actor evalAction is expecting. All
 *            decorators must reference the same DataType when being applied.
 *            (Compiler will not let you mix types with this.)
 */
public abstract class ActionResponseDecorator<DataType> implements
		IActionResponse<DataType> {
	private IActionResponse<DataType> actionResponder;

	public ActionResponseDecorator(IActionResponse<DataType> responder) {
		actionResponder = responder;
	}

	/**
	 * All children should call their super evalAction, otherwise decorator
	 * pattern is not happening
	 */
	public boolean evalOwnerAction(DataType owner, Action<?> action) {
		Profiler.getInstance().startSection("Eval owner action" + actionResponder.toString());
		boolean doTarget=actionResponder.evalOwnerAction(owner, action);
		Profiler.getInstance().endSection();
		return doTarget;
	}

	public boolean evalReceivedAction(DataType owner, Action<?> action) {
		Profiler.getInstance().startSection("Eval received action" + actionResponder.toString());
		boolean doTarget=actionResponder.evalReceivedAction(owner, action);
		Profiler.getInstance().endSection();
		return doTarget;
	}

	@Override
	public String toString() {
		return actionResponder.toString();
	}
}
