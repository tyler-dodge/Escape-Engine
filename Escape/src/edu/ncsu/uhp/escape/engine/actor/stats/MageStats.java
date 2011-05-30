package edu.ncsu.uhp.escape.engine.actor.stats;

/**
 * Example extension of BaseStats to represent that each character type
 * can start with a differing types and values of stats.
 * 
 * @author Brandon Walker
 *
 */
public class MageStats extends BaseStats {
	// TODO: Migrate these constants to Android's Resources
	private static final int STRENGTH = 0;
	private static final int DEXTERITY = 0;
	private static final int CONSTITUTION = 0;
	private static final int INTELLIGENCE = 0;
	private static final int WISDOM = 0;
	private static final int CHARISMA = 0;
	private static final int ARMOR = 0;
	private static final int MAGICRESIST = 0;
	private static final int DODGE = 0;

	public MageStats() {
		super("Mage Base Class", STRENGTH, DEXTERITY, CONSTITUTION,
				INTELLIGENCE, WISDOM, CHARISMA, ARMOR, MAGICRESIST, DODGE);
	}

}
