package edu.ncsu.uhp.escape.engine.actor.stats;

import java.util.ArrayList;

import edu.ncsu.uhp.escape.engine.utilities.KeyValuePair;

/**
 * StatBuff abstract class that holds allows stats to be decorated onto the 
 * current base stack of stats.
 * 
 * @author Brandon Walker
 *
 */
public abstract class StatBuff extends StatDecorator {
	

	public StatBuff(String name, ArrayList<KeyValuePair<String, Integer>> keyValuePairs) {
		super(name, true, keyValuePairs);
	}
	
	/**
	 * Converts a single stat into an ArrayList for children to call to create the 
	 * decorator.  
	 * 
	 * @param name of buff
	 * @param magnitude of the buff
	 * @return the ArrayList of stats.
	 */
	public static ArrayList<KeyValuePair<String, Integer>> addStats(String name, int magnitude){
		ArrayList<KeyValuePair<String, Integer>> keyValuePairs = new ArrayList<KeyValuePair<String, Integer>>();
		keyValuePairs.add(new KeyValuePair<String, Integer>(name, magnitude));
		return keyValuePairs;
	}
	
}
