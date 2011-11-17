package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import edu.ncsu.uhp.escape.R;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.Rubble;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateRubbleAction;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.ImageSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Response to create rubble. First creates the new rubble actor, and then 
 * kills the actor who invoked the action.
 * 
 * author Brandon Walker
 *
 */
public class CreateRubbleResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {

	public CreateRubbleResponse(IActionResponse<DataType> responder) {
		super(responder);
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof CreateRubbleAction) {
			CreateRubbleAction rubbleAction = (CreateRubbleAction) action;
			List<ICollision> skillBox = new ArrayList<ICollision>();
				//TODO: No collisions added yet because they were crashing the program.
			Rubble rubble = new Rubble(owner.getPosition(), rubbleAction.getData(), new ImageSource(0, R.drawable.ash_tree, new Point(5, 5, 0),
					new Point(-2.5f, -2.5f, 3)), skillBox);
			owner.pushAction(new CreateActorAction(owner, rubble));
			owner.pushAction(new DieAction(owner, owner));
		}
		return false;
	}

}
