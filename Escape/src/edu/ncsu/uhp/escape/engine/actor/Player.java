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
public abstract class Player<DataType extends Player<DataType>> extends
		Npc<DataType> {

	private String name;
	private ClassName className;
	private int[] skillTreeHash = new int[10];
	private IStats stats;

	// private ArrayList<Gear> gear = new ArrayList<Gear>();

	/**
	 * Constructs a Player with name, stats, and class.
	 */
	public Player(String name, ClassName className, Point position,
			IRotation rotation, RenderSource source,
			List<ICollision> collision, IStats stats) {
		super(position, rotation, source, collision);
		this.className = className;
		this.name = name;
		this.stats = stats;
	}

	/**
	 * Levels up the character by creating a new stats object.
	 */
	public void levelUp(/* Params Here */) {
		// TODO: Implement Level Up
		switch (className) {
		case MAGE:
		case ROGUE:
		case WARRIOR:
		}
	}

	/**
	 * Enumerated class types
	 * 
	 * @author Brandon Walker
	 * 
	 */
	public static enum ClassName {
		MAGE, WARRIOR, ROGUE
	}

	public String getName() {
		return this.name;
	}

	public ClassName getClassName() {
		return this.className;
	}

	public int[] getSkillTreeHash() {
		return this.skillTreeHash;
	}

	public IStats getStats() {
		return this.stats;
	}
}
