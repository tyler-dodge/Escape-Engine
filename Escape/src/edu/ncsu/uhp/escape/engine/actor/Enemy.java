package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.MovementResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.PushResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.TravelTrackOnTickResponse;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.NodalTrack;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.game.actionresponse.actor.CollisionWithNexusResponse;

/**
 * Enemy is the abstract (Concrete for now) enemy type that implements trackMovable
 * 
 * @author Brandon Walker
 *
 */
public abstract class Enemy<DataType extends Enemy<DataType>> extends Npc<DataType> implements TrackMovable {

	public NodalTrack trackPoints;
	
	public Enemy(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision, NodalTrack trackPoints) {
		super(position, rotation, source, collision);
		this.trackPoints = trackPoints;
	}
	
	@Override
	public IActionResponse<DataType> createDefaultResponse() {
		IActionResponse<DataType> responder = new BaseActionResponse<DataType>();
		responder = new MovementResponse<DataType>(responder);
		responder = new TravelTrackOnTickResponse<DataType>(responder);
		responder = new CollisionWithNexusResponse<DataType>(responder);
		return responder;
	}
	
	public NodalTrack getTrackPoints(){
		return trackPoints;
	}
	
	public void setTrackPoints(NodalTrack trackPoints){
		this.trackPoints = trackPoints;
	}
	
	public void travelOnTrack(){
		if(trackPoints != null){
			trackPoints.travel(this);
		}

	}
}
