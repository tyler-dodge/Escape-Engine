package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.CreateRubbleResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.TreeBurnAfterImpactResponse;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Tree object with default response and burn after impact response.
 * 
 * @author Brandon Walker
 *
 */
public class Tree extends Npc<Tree> {
	public Tree(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(position, rotation, source, collision);
	}

	@Override
	public IActionResponse<Tree> createDefaultResponse() {
		IActionResponse<Tree> superResponse = super.createDefaultResponse();
		return new TreeBurnAfterImpactResponse<Tree>(superResponse); 
	}

	@Override
	public Tree asDataType() {
		return this;
	}

}
