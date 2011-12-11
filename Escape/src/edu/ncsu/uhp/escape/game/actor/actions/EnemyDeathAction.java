package edu.ncsu.uhp.escape.game.actor.actions;


import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;

/**
 * Action thrown when an Enemy Dies
 * 
 * @author Brandon Walker
 *
 */
public class EnemyDeathAction extends Action<ActionObserver<?>> {
	public static final String DIE_ACTION = "ENEMY_DIES";

	public EnemyDeathAction(ActionObserver<?> source, ActionObserver<?> target) {
		this(source, target, Action.STANDARD_PRIORITY);
		// TODO Auto-generated constructor stub
	}

	public EnemyDeathAction(ActionObserver<?> source, ActionObserver<?> target, int priority) {
		super(source, source, DIE_ACTION, target, priority);
		// TODO Auto-generated constructor stub
	}

}
