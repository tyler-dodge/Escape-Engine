package edu.ncsu.uhp.escape.engine.actor;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.uhp.escape.R;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.ImageSource;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.ZAxisRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.game.utilities.TurretCollision;

/**
 * Base Attack Turret that extends Turret
 * 
 * @author Brandon Walker
 * 
 */
public class BaseAttackTurret extends Turret<BaseAttackTurret> {
	
	public final static int BASE_ATTACK_TURRET_COST = 400;
	
	public static Point RANGE_DIMENSION = new Point(20, 20, 1);
	public static Point RANGE_OFFSET = new Point(-10, -10, 0);
	
	public static final TurretCollision DEFAULT_TURRET_RANGE = new TurretCollision(RANGE_DIMENSION, RANGE_OFFSET, 0, 255, 0, 50);

	public BaseAttackTurret(Point position, IRotation rotation,
			RenderSource source, List<ICollision> collision) {
		super(position, rotation, source, collision, DEFAULT_TURRET_RANGE);
	}

	@Override
	public BaseAttackTurret asDataType() {
		return this;
	}

	@Override
	public void attack(Enemy<?> enemy) {
		BoxCollision collisionBox = new BoxCollision(new Point(2, 2, 2),
				new Point(-1f, -1f, -1f));
		List<ICollision> box = new ArrayList<ICollision>();
		box.add(collisionBox);
		
		float relX = getPosition().getX() - enemy.getPosition().getX();
		float relY = enemy.getPosition().getY() - getPosition().getY(); 
		float angle = (float) Math.atan(relY / relX) + 3.14f;
		if (relX == 0 && relY == 0) {
			angle = 0;
		} else if (relY == 0) {
			if (relX > 0) {
				angle = 0;
			} else {
				angle = 3.14f;
			}
		} else if (relX == 0) {
			if (relY > 0) {
				angle = 1.57f;
			} else
				angle = 4.71f;
		}
		if (relX < 0) {
			angle += 3.14;
		}
		pushAction(new CreateActorAction(this, new Fireball(getPosition(), new ZAxisRotation(angle), new ImageSource(0, R.drawable.fireball_actor, new Point(5, 5, 0), new Point(-2.5f, -2.5f, 1)), box)));
	}
	
	public int getCost(){
		return BASE_ATTACK_TURRET_COST;
	}
	
}
