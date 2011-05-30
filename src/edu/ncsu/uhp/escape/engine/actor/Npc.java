package edu.ncsu.uhp.escape.engine.actor;

import java.util.List;

import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.*;
import edu.ncsu.uhp.escape.engine.actor.actions.PostMoveAction;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.*;

/**
 * Abstraction of all humanoid/moving characters. Will be extended to make
 * individual enemies/players/allies
 * 
 * @author Tyler Dodge & Brandon Walker
 * 
 */

public abstract class Npc<DataType extends Actor<DataType>> extends
		Actor<DataType> {
	public static final float speed = 0.1f;

	public float speedMod = 0;

	public Npc(Point position, IRotation rotation, RenderSource source,
			List<ICollision> collision) {
		super(position, rotation, source, collision);
	}

	public IActionResponse<DataType> createDefaultResponse() {
		IActionResponse<DataType> responder = new BaseActionResponse<DataType>();
		responder = new MovementResponse<DataType>(responder);
		responder = new PushResponse<DataType>(responder);
		return responder;
	}

	@Override
	public Point move(IRotation direction) {
		Point speedVector = new Point(getSpeed(), 0, 0);
		Point moveVector = direction.apply(speedVector);
		Point originalPosition = getPosition();
		Point finalPosition = originalPosition.add(moveVector);
		setPosition(finalPosition);
		this.setRotation(direction);
		this.pushAction(new PostMoveAction(this, originalPosition,
				finalPosition));
		return finalPosition;
	}

	public float getSpeed() {
		return speed + this.speedMod;
	}

	public void setSpeed(float speed) {
		this.speedMod = speed;
	}
}
