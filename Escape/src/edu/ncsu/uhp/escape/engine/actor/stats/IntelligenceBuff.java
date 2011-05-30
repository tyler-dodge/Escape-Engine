package edu.ncsu.uhp.escape.engine.actor.stats;


/**
 * Example (For Now) buff that shows how the statBuff system works. It wraps the 
 * stat object with this temporary stat buff.
 * 
 * @author Brandon Walker
 *
 */
public class IntelligenceBuff extends StatBuff {
	
	private static final String INTELLIGENCE = "intelligence";
	public IntelligenceBuff(String name, int magnitude) {
		super(name, addStats(INTELLIGENCE, magnitude));
	}
}