package edu.ncsu.uhp.escape.engine.actor;

import edu.ncsu.uhp.escape.engine.utilities.NodalTrack;

public interface TrackMovable {
	
	public void travelOnTrack();

	public NodalTrack getTrackPoints();
	public void setTrackPoints(NodalTrack trackPoints);
	
}
