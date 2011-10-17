package edu.ncsu.uhp.escape.engine.actionresponse;

import edu.ncsu.uhp.escape.engine.actor.actions.Action;

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
		return actionResponder.evalOwnerAction(owner, action);
	}

	public boolean evalReceivedAction(DataType owner, Action<?> action) {
		return actionResponder.evalReceivedAction(owner, action);
	}

	public String toString() {
		return actionResponder.toString();
	}
}
