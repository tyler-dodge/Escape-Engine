package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actor.stats.IStats;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * 
 * PlayerClass holds every class' skill tree.
 * 
 * @author Tyler Dodge and Brandon Walker
 * 
 */
public abstract class PlayerClass<DataType extends PlayerClass<DataType>>
		extends Player<DataType> {

	private SkillTree tree;

	public abstract SkillTree generateSkillTree();

	/**
	 * 
	 * Constructs a PlayerClass.
	 * 
	 */
	public PlayerClass(String name, ClassName className, Point position,
			IRotation rotation, RenderSource source,
			List<ICollision> collision, IStats stats) {
		super(name, className, position, rotation, source, collision, stats);
	}

	public SkillTree getTree() {
		return tree;
	}
}
