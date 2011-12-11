package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;
import edu.ncsu.uhp.escape.game.actor.GameController;

public class SpawnerTickResponse  extends SingleEvalActionResponseDecorator<GameController>{

	private static final int DEFAULT_TICKS = 20;
	private int ticksBetweenSpawns;
	private int currentTick = 0;
	
	public SpawnerTickResponse(IActionResponse<GameController> responder) {
		this(responder, DEFAULT_TICKS);
	}
	
	public SpawnerTickResponse(IActionResponse<GameController> responder, int ticksBetweenSpawns) {
		super(responder);
		this.ticksBetweenSpawns = ticksBetweenSpawns;
	}
	
	public boolean evalAction(GameController owner, Action<?> action) {
		if (action instanceof EngineTickAction) {
			currentTick++;
			if(currentTick == ticksBetweenSpawns){
				if(owner.getSpawner().getEnemyDictionary().hasNext()){
					Enemy<?> enemy = owner.getSpawner().getEnemyDictionary().next();
					owner.pushAction(new CreateActorAction(owner, enemy));
					owner.getActiveEnemies().add(enemy);
				}
				currentTick = 0;
			}
		}
		return false;
	}
}
