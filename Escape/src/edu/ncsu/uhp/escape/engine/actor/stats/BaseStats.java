package edu.ncsu.uhp.escape.engine.actor.stats;

import java.util.HashMap;

/**
 * Creates the Basic stage of stats. Extend this if you want to prevent your
 * stats from being layered on top of more stats.
 * 
 * @author Tyler Dodge & Brandon Walker
 * 
 */
public class BaseStats implements IStats {
	private final String STRENGTH = "strength", DEXTERITY = "dexterity", CONSTITUTION = "constitution", INTELLIGENCE = "intelligence", WISDOM = "wisdom",
			CHARISMA = "charisma", ARMOR = "armor", MAGICRESIST = "magicresist", DODGE = "dodge";
	private String name;
	private HashMap<String, Integer> values = new HashMap<String, Integer>();
	
	public BaseStats(String name, int strength, int dexterity,
			int constitution, int intelligence, int wisdom, int charisma,
			int armor, int magicResist, int dodge) {
		values.put(STRENGTH, strength);
		values.put(DEXTERITY, dexterity);
		values.put(CONSTITUTION, constitution);
		values.put(INTELLIGENCE, intelligence);
		values.put(WISDOM, wisdom);
		values.put(CHARISMA, charisma);
		values.put(ARMOR, armor);
		values.put(MAGICRESIST, magicResist);
		values.put(DODGE, dodge);
		this.name = name;
	}

	public IStats removeTemporary() {
		return this;
	}

	public IStats removeByName(String name) {
		return this;
	}

	public int get(String name) {
		if (values.containsKey(name)){
			return values.get(name);
		}
		else return 0;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
