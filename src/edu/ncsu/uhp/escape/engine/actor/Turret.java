package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actor.stats.IStats;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Extension of Npc class which all player classes inherit from.
 * 
 * @author Brandon Walker
 * 
 */
public abstract class Turret<DataType extends Turret<DataType>> extends
		Npc<DataType> {

	/**
	 * Constructs a Player with name, stats, and class.
	 */
	public Turret(Point position,
			IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(position, rotation, source, collision);
	}
}
