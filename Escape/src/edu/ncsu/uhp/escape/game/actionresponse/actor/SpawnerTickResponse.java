package edu.ncsu.uhp.escape.game.actionresponse.actor;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.SingleEvalActionResponseDecorator;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;
import edu.ncsu.uhp.escape.game.actor.Spawner;

public class SpawnerTickResponse  extends SingleEvalActionResponseDecorator<Spawner>{

	private static final int DEFAULT_TICKS = 20;
	private int ticksBetweenSpawns;
	private int currentTick = 0;
	
	public SpawnerTickResponse(IActionResponse<Spawner> responder) {
		this(responder, DEFAULT_TICKS);
	}
	
	public SpawnerTickResponse(IActionResponse<Spawner> responder, int ticksBetweenSpawns) {
		super(responder);
		this.ticksBetweenSpawns = ticksBetweenSpawns;
	}
	
	public boolean evalAction(Spawner owner, Action<?> action) {
		if (action instanceof EngineTickAction) {
			currentTick++;
			if(currentTick == ticksBetweenSpawns){
				if(owner.getEnemyDictionary().hasNext()){
					owner.pushAction(new CreateActorAction(owner, owner.getEnemyDictionary().next()));
				}
				currentTick = 0;
			}
		}
		return false;
	}
}
