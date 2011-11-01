package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actor.stats.IStats;
import edu.ncsu.uhp.escape.engine.actor.stats.MageStats;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Mage class extends Player where Mage Specific functions and skills exist.
 * 
 * @author Brandon Walker
 * 
 */
public class BaseAttackTurret extends Turret<BaseAttackTurret> {

	public BaseAttackTurret(Point position, IRotation rotation,
			RenderSource source, List<ICollision> collision) {
		super(position, rotation, source, collision);
	}

	@Override
	public BaseAttackTurret asDataType() {
		return this;
	}
}
