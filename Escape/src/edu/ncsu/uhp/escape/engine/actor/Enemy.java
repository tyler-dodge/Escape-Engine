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
import edu.ncsu.uhp.escape.game.actionresponse.actor.ProjectileHitResponse;

/**
 * Enemy is the abstract (Concrete for now) enemy type that implements trackMovable
 * 
 * @author Brandon Walker
 *
 */
public abstract class Enemy<DataType extends Enemy<DataType>> extends Npc<DataType> implements TrackMovable {

	private NodalTrack trackPoints;
	private int health;
	
	public Enemy(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision, NodalTrack trackPoints, int health) {
		super(position, rotation, source, collision);
		this.trackPoints = trackPoints;
		this.health = health;
	}
	
	@Override
	public IActionResponse<DataType> createDefaultResponse() {
		IActionResponse<DataType> responder = new BaseActionResponse<DataType>();
		responder = new MovementResponse<DataType>(responder);
		responder = new TravelTrackOnTickResponse<DataType>(responder);
		responder = new CollisionWithNexusResponse<DataType>(responder);
		responder = new ProjectileHitResponse<DataType>(responder);
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
	
	public int getHealth(){
		return this.health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void subtractHealth(int health){
		this.health = this.health - health;
	}
	
	public abstract int getWorth();
}
