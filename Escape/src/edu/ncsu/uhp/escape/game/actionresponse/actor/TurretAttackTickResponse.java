package edu.ncsu.uhp.escape.game.actionresponse.actor;

import java.util.LinkedList;
import java.util.Set;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Turret;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;

public class TurretAttackTickResponse<DataType extends Turret<?>> extends SingleEvalActionResponseDecorator<DataType> {

	private static final int DEFAULT_TICKS = 10;
	private int ticksBetweenSpawns;
	private int currentTick = 0;
	
	public TurretAttackTickResponse(IActionResponse<DataType> responder) {
		this(responder, DEFAULT_TICKS);
	}
	
	public TurretAttackTickResponse(IActionResponse<DataType> responder, int ticksBetweenSpawns) {
		super(responder);
		this.ticksBetweenSpawns = ticksBetweenSpawns;
	}
	
	public boolean evalAction(DataType owner, Action<?> action) {
		if (action instanceof EngineTickAction && owner.isPlaced()) {
			Set<Enemy<?>> enemies = owner.getEnemiesInRange();
			if(!enemies.isEmpty()){
				currentTick++;
				if(currentTick == ticksBetweenSpawns){
					currentTick = 0;
					LinkedList<ICollidable> enemiesToRemove = new LinkedList<ICollidable>();
					synchronized(enemies){
						for(Enemy<?> enemy : enemies) {
							if(owner.isWithinRange(enemy)){
								owner.attack(enemy);
								return false;
							}
							else{
								enemiesToRemove.add(enemy);
							}
						}
						for(ICollidable enemy : enemiesToRemove){
							enemies.remove(enemy);
						}
					}
				}
			} else{
				currentTick = 0;
			}
		}
		return false;
	}
}
