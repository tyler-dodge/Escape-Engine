package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.NodalTrack;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class BaseEnemyBlob extends Enemy<BaseEnemyBlob>{

	public BaseEnemyBlob(Point position, IRotation rotation,
			RenderSource source, List<ICollision> collision,
			NodalTrack trackPoints) {
		super(position, rotation, source, collision, trackPoints);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BaseEnemyBlob asDataType() {
		return this;
	}
}
