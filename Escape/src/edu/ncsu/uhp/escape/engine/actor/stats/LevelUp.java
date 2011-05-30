package edu.ncsu.uhp.escape.engine.actor.stats;

import java.util.ArrayList;

import edu.ncsu.uhp.escape.engine.utilities.KeyValuePair;

/**
 * Standard LevelUp statDecorator. This decorator uses an ArrayList to pass in
 * multiple stat buffs to be decorated.
 * 
 * @author Brandon Walker
 *
 */
public class LevelUp extends StatDecorator {

	public LevelUp(String name, ArrayList<KeyValuePair<String, Integer>> values) {
		super(name, false, values);
	}
}
