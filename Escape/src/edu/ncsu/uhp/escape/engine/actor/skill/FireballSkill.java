package edu.ncsu.uhp.escape.engine.actor.skill;

import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.FireballCastResponse;
import edu.ncsu.uhp.escape.engine.actor.Actor;

/**
 * Fireball Skill actor
 * 
 * @author Brandon Walker
 *
 */
public class FireballSkill<DataType extends Actor<?>> extends Skill<DataType> {
	public FireballSkill(String name) {
		super(name);
	}

	public IActionResponse<DataType> addResponder(
			IActionResponse<DataType> original) {

		return original;
	}
}
