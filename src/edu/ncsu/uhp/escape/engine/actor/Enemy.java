package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.NodalTrack;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Enemy is the abstract (Concrete for now) enemy type that implements trackMovable
 * 
 * @author Brandon Walker
 *
 */
public class Enemy<DataType extends Enemy<DataType>> extends Npc<DataType> implements TrackMovable {

	public NodalTrack trackPoints;
	
	public Enemy(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(position, rotation, source, collision);
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


	@Override
	public DataType asDataType() {
		// TODO Auto-generated method stub
		return null;
	}

}
