package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.DieAfterCollisionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.DieAfterTicksResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.MoveOnTickResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.ProjectileResponse;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Fireball actor with it's default responses.
 * 
 * @author Brandon Walker
 * 
 */
public class Fireball extends Npc<Fireball> {

	public Fireball(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(position, rotation, source, collision);
	}

	@Override
	public IActionResponse<Fireball> createDefaultResponse() {
		IActionResponse<Fireball> superResponse = super.createDefaultResponse();
		superResponse = new DieAfterTicksResponse<Fireball>(superResponse, 120);
		superResponse = new DieAfterCollisionResponse<Fireball>(superResponse);
		superResponse = new ProjectileResponse<Fireball>(superResponse);
		return new MoveOnTickResponse<Fireball>(superResponse);
	}

	@Override
	public Fireball asDataType() {
		return this;
	}

}
