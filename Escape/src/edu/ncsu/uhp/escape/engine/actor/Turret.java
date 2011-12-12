package edu.ncsu.uhp.escape.engine.actor;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.MovementResponse;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.game.actionresponse.actor.CheckWithinTurretRangeResponse;
import edu.ncsu.uhp.escape.game.actionresponse.actor.EnemyDeathTurretResponse;
import edu.ncsu.uhp.escape.game.actionresponse.actor.TurretAttackTickResponse;
import edu.ncsu.uhp.escape.game.actionresponse.actor.TurretCollisionResponse;
import edu.ncsu.uhp.escape.game.utilities.TurretCollision;
import edu.ncsu.uhp.escape.game.utilities.TurretRenderableSource;

/**
 * Extension of NPC for turrets
 * 
 * @author Brandon Walker
 * 
 */
public abstract class Turret<DataType extends Turret<DataType>> extends
		Npc<DataType> {
	
	private TurretCollision rangeCollision;
	private boolean selected;
	private Set<ICollidable> collidingWith = Collections.synchronizedSet(new LinkedHashSet<ICollidable>());
	private Set<Enemy<?>> enemiesInRange = Collections.synchronizedSet(new LinkedHashSet<Enemy<?>>());
	private boolean colliding;
	private boolean placed;
	private RenderSource source;
	
	/**
	 * Constructs a Turret with a range
	 */
	public Turret(Point position,
			IRotation rotation, RenderSource source,
			List<ICollision> collision, TurretCollision rangeCollision) {
		super(position, rotation, source, collision);
		this.rangeCollision = rangeCollision;
		this.source = source;
		setSource(new TurretRenderableSource( 0, new RenderSource[]{ 
				source}, new Point(0,0,0), rangeCollision));
	}
	
	@Override
	public IActionResponse<DataType> createDefaultResponse() {
		IActionResponse<DataType> responder = new BaseActionResponse<DataType>();
		responder = new MovementResponse<DataType>(responder);
		responder = new TurretCollisionResponse<DataType>(responder);
		responder = new CheckWithinTurretRangeResponse<DataType>(responder);
		responder = new TurretAttackTickResponse<DataType>(responder);
		responder = new EnemyDeathTurretResponse<DataType>(responder);
		return responder;
	}
	
	public abstract void attack(Enemy<?> enemy);
	
	/**
	 * Checks whether or not if an actor is within the turrets range.
	 * 
	 * @param checkActor
	 *            the actor to be checked against
	 * @return whether or not this actor collides with checkActor
	 */
	public boolean isWithinRange(ICollidable checkActor) {
		Point thisPosition = this.getPosition();
		IRotation thisRotation = this.getRotation();
		Point otherPosition = checkActor.getPosition();
		IRotation otherRotation = checkActor.getRotation();
		// iterates through this actor's collision items
		if (rangeCollision == null || checkActor.getCollisions() == null)
			return false;
		for (ICollision otherCollisionItem : checkActor.getCollisions()) {
			if (rangeCollision.doesCollide(thisPosition, thisRotation,
					otherCollisionItem, otherPosition, otherRotation))
				return true;
		}
		return false;
	}
	
	public void addEnemyToAttackList(Enemy<?> enemy){
		enemiesInRange.add(enemy);
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public TurretCollision getRangeCollision(){
		return this.rangeCollision;
	}
	
	public void setColor(ICollidable observer){
		collidingWith.add(observer);
		rangeCollision.changeColor(255, 0, 0, 50);
		getSource().reload();
		colliding = true;
	}
	
	/**
	 * test colliding first checks to see if colliding was set to true since the last test, if true
	 * then there is no reason to check this time. If it is false, it then goes through it's previously recorded
	 * collisions and checks if it is still colliding. If none are, then all of the actors are removed and then 
	 * the turret range color is changed to green. If there is still a collision then it ends the test without changing
	 * the color, removes the non-colliding actors it calculated (However not all will be calculated, only collision with one is checked
	 * before the loop is broken) and still sets colliding to false for the next eval.
	 * 
	 * 
	 */
	public void testStillColliding(){
		if(colliding == true){	
			colliding = false;
		}
		else{
			LinkedList<ICollidable> observersToRemove = new LinkedList<ICollidable>();
			synchronized(collidingWith){
				for(ICollidable observer : collidingWith){
					if(colliding == false && this.doesCollide(observer)){
						colliding = true;
					}
					else{
						observersToRemove.add(observer);
					}
				}
				for(ICollidable observer : observersToRemove){
					collidingWith.remove(observer);
				}
			}
			if(colliding == false){
				rangeCollision.changeColor(0, 255, 0, 150);
				getSource().reload();
			}
			colliding = false;
		}
	}
	
	public boolean placeable(){
		colliding = false;
		testStillColliding();
		return collidingWith.isEmpty();
	}
	
	public Set<Enemy<?>> getEnemiesInRange() {
		return enemiesInRange;
	}
	
	public boolean isPlaced(){
		return placed;
	}
	
	public void place(){
		setSource(source);
		placed = true;
	}
	
}
