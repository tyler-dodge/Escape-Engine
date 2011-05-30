package edu.ncsu.uhp.escape.engine.actor.skill;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.Actor;

/**
 * Skill is a container class that holds a skill name and its congruent action responder.
 * 
 * @author Brandon Walker
 * 
 */
public abstract class Skill<DataType extends Actor<?>> {
	
	String name;
	
	/**
	 * Creates a skill object with name and action responder decorator.
	 * 
	 * @param Name assigns name to the class
	 */
	public Skill(String name){
		this.name = name;
	}
	
	/**
	 * 
	 * 
	 * @param original
	 * @return 
	 */
	public abstract IActionResponse<DataType> addResponder(IActionResponse<DataType> original);

}
