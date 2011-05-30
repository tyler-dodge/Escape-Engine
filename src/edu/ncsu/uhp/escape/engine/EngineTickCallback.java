package edu.ncsu.uhp.escape.engine;

/**
 * Callback for when the engine ticks.
 * 
 * @author Tyler Dodge
 * 
 */
public interface EngineTickCallback {
	/**
	 * The method the engine calls when it ticks.
	 */
	void tick();
}
