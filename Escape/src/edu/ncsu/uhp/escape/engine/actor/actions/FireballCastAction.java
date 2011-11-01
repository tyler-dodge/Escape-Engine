package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;

/**
 * Action thrown when a fireball is cast.
 * 
 * @author Brandon Walker
 *
 */
public class FireballCastAction extends Action<IRotation> {

	public FireballCastAction(ActionObserver<?> source, String name,
			IRotation data) {
		super(source, source, name, data);
	}

}
