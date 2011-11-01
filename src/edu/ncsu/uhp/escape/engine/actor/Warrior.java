package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actor.stats.IStats;
import edu.ncsu.uhp.escape.engine.actor.stats.WarriorStats;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Warrior class extends Player where Warrior Specific functions and skills
 * exist.
 * 
 * @author Brandon Walker
 * 
 */
public class Warrior extends PlayerClass<Warrior> {

	private static ClassName className = ClassName.WARRIOR;
	
	/**
	 * Creates a Warrior object with position, rotation, source and collision, stats, 
	 * and stat mods based on a predefined amount. Is used on level up.
	 *
	 * @param name
	 * @param position
	 * @param rotation
	 * @param source
	 * @param collision
	 */
	public Warrior(String name, Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision, IStats stats) {
		super(name, className, position, rotation, source, collision, stats);
	}
	
	/**
	 * Creates a new warrior object with position, rotation, source and collision.
	 * Is used upon new character creation hence no stats.
	 * 
	 * @param name
	 * @param position
	 * @param rotation
	 * @param source
	 * @param collision
	 */
	public Warrior(String name, Point position, IRotation rotation,
			RenderSource source, List<ICollision> collision) {
		super(name, className, position, rotation, source, collision, new WarriorStats());
	}
	

	@Override
	public SkillTree generateSkillTree() {
		return new SkillTree(getSkillTreeHash(), ClassName.WARRIOR);
	}

	@Override
	public Warrior asDataType() {
		return this;
	}
}
