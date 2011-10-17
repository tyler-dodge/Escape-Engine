package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.ActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.TrackMovable;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;

/**
 * If an actor has this response, it will move automatically in accordance to 
 * engine ticks in the direction of the actor's nodal track.
 * 
 * @author Brandon Walker
 *
 */
public class TravelTrackOnTickResponse<DataType extends Actor<?> & TrackMovable> extends
		SingleEvalActionResponseDecorator<DataType> {

	public TravelTrackOnTickResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof EngineTickAction) {
			owner.travelOnTrack();
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + "Travel On Track By Engine Tick ";
	}

}
