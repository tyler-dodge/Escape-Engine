package edu.ncsu.uhp.escape.engine.actionresponse;

import edu.ncsu.uhp.escape.engine.actor.actions.Action;

/**
 * Used to handle actions. Evaluates each action as it receives them
 * 
 * @author Tyler Dodge
 * 
 * @param <DataType>
 *            the type that the owner is.
 */
public interface IActionResponse<DataType> {
	public boolean evalOwnerAction(DataType owner, Action<?> action);
	public boolean evalReceivedAction(DataType owner, Action<?> action);
}
