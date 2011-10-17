package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.MoveAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PostMoveAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PushAction;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;

/**
 * Response to PostMoveAction and MoveAction. After a check with collision, it
 * throws a push action which will actually set the actors position.
 * 
 * @author Tyler Dodge
 * 
 */
public class MovementResponse<DataType extends Actor<?>> extends
		ActionResponseDecorator<DataType> {

	public MovementResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalOwnerAction(DataType owner, Action<?> action) {
		boolean superResponse = super.evalOwnerAction(owner, action);
		if (action instanceof MoveAction) {
			MoveAction moveAction = (MoveAction) action;
			owner.move(moveAction.getDirection());
			PostMoveAction postMove = new PostMoveAction(owner,
					moveAction.getOriginalPosition(), owner.getPosition());
			owner.pushAction(postMove);
		}
		return superResponse;
	}

	@Override
	public boolean evalReceivedAction(DataType owner, Action<?> action) {
		boolean superResponse = super.evalReceivedAction(owner, action);
		ActionObserver<?> actionSource = action.getSource();
		if (action instanceof PostMoveAction
				&& actionSource instanceof ICollidable) {
			PostMoveAction moveAction = (PostMoveAction) action;
			if (owner.doesCollide((ICollidable) actionSource)) {
				actionSource.pushAction(new PushAction(owner, actionSource,
						moveAction.getOriginalPosition()));
				superResponse = true;
			}
		}
		return superResponse;
	}

	@Override
	public String toString() {
		return super.toString() + "Movement ";
	}

}
