package edu.ncsu.uhp.escape.engine.actionresponse.actor;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import edu.ncsu.uhp.escape.R;
import edu.ncsu.uhp.escape.engine.actor.Actor;
import edu.ncsu.uhp.escape.engine.actor.Fireball;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;
import edu.ncsu.uhp.escape.engine.actor.actions.FireballCastAction;
import edu.ncsu.uhp.escape.engine.actionresponse.*;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.ImageSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Action response to fireball cast action, throws a create actor action with the fireball.
 * 
 * @author Brandon Walker
 *
 */
public class FireballCastResponse<DataType extends Actor<?>> extends
		SingleEvalActionResponseDecorator<DataType> {
	private Context context;

	public FireballCastResponse(Context context,
			IActionResponse<DataType> responder) {
		super(responder);
		this.context = context;
	}

	@Override
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof FireballCastAction) {
			FireballCastAction fireballAction = (FireballCastAction) action;
			BoxCollision collisionBox = new BoxCollision(new Point(5, 5, 5),
					new Point(-2.5f, -2.5f, 0));
			List<ICollision> skillBox = new ArrayList<ICollision>();
			skillBox.add(collisionBox);
			Point spawnOffset = fireballAction.getData().apply(
					new Point(5, 0, 0));
			Fireball fireball = new Fireball(owner.getPosition().add(
					spawnOffset), fireballAction.getData(), new ImageSource(
					context, 0, R.drawable.fireball_actor, new Point(5, 5, 0),
					new Point(-2.5f, -2.5f, 3)), skillBox);

			owner.pushAction(new CreateActorAction(owner, fireball));
		}
		return false;
	}

}
