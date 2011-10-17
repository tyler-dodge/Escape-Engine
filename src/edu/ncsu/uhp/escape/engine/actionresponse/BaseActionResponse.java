package edu.ncsu.uhp.escape.engine.actionresponse;

import edu.ncsu.uhp.escape.engine.actor.actions.Action;

/**
 * The Base part of any Action Response. Does nothing on its own.
 * 
 * @author Tyler Dodge
 * 
 * @param <DataType>
 */
public class BaseActionResponse<DataType> implements IActionResponse<DataType> {
	public BaseActionResponse() {
	}

	/**
	 * Always returns false because it does not affect anything.
	 */
	public boolean evalOwnerAction(DataType owner, Action<?> action) {
		return false;
	}
	public boolean evalReceivedAction(DataType owner, Action<?> action) {
		return false;
	}
	@Override
	public String toString() {
		return "Responder";
	}
}
