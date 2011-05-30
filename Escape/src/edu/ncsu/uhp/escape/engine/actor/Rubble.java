package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Rubble actor with simple default response.
 * 
 * @author Brandon Walker
 *
 */
public class Rubble extends Npc<Rubble> {

	public Rubble(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(position, rotation, source, collision);
	}

	@Override
	public IActionResponse<Rubble> createDefaultResponse() {
		return super.createDefaultResponse();
	}

	@Override
	public Rubble asDataType() {
		return this;
	}

}
