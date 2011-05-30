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
public class Mage extends PlayerClass<Mage> {

	private static ClassName className = ClassName.MAGE;

	/**
	 * Creates a Mage object with position, rotation, source and collision,
	 * stats, and stat mods based on a predefined amount. Is used on level up.
	 * 
	 * @param name
	 * @param position
	 * @param rotation
	 * @param source
	 * @param collision
	 */
	public Mage(String name, Point position, IRotation rotation,
			RenderSource source, List<ICollision> collision, IStats stats) {
		super(name, className, position, rotation, source, collision, stats);
	}

	/**
	 * Creates a new mage object with position, rotation, source and collision.
	 * Is used upon new character creation hence no stats.
	 * 
	 * @param name
	 * @param position
	 * @param rotation
	 * @param source
	 * @param collision
	 */
	public Mage(String name, Point position, IRotation rotation,
			RenderSource source, List<ICollision> collision) {
		super(name, className, position, rotation, source, collision,
				new MageStats());
	}

	public SkillTree generateSkillTree() {
		return new SkillTree(getSkillTreeHash(), ClassName.MAGE);
	}

	@Override
	public Mage asDataType() {
		return this;
	}
}
