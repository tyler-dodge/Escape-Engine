package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;

/**
 * Action thrown when Rubble is created.
 * 
 * @author Brandon Walker
 *
 */
public class CreateRubbleAction extends Action<IRotation> {

	public CreateRubbleAction(ActionObserver<?> source, String name,
			IRotation data) {
		super(source, source, name, data);
	}

}
