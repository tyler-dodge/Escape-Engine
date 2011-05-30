package edu.ncsu.uhp.escape.engine;

import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;

/**
 * An abstraction used to receive and delegate responses to actions from other
 * ActionObservers. Handles all sending of actions and is thread safe
 * 
 * @author Tyler Dodge
 * 
 * @param <DataType>
 *            The type used by the ActionResponder
 */
public abstract class ActionObserver<DataType> extends Observable implements
		Observer {
	private Lock actionsLock = new ReentrantLock();
	private Lock tempActionsLock = new ReentrantLock();

	/**
	 * Creates an Action Observer.
	 * 
	 * @param capacity
	 *            The number of actions this ActionObserver will contain before
	 *            it drops actions.
	 */
	public ActionObserver(int capacity) {
		this.tempActions = new PriorityQueue<Action<?>>(capacity,
				queueComparator);
		this.actions = new PriorityQueue<Action<?>>(capacity, queueComparator);
		this.actionResponder = createDefaultResponse();
	}

	/**
	 * Gets this ActionObserver's Response Handler
	 * 
	 * @return This ActionObserver's Response Handler
	 */
	public IActionResponse<DataType> getResponder() {
		return actionResponder;
	}

	/**
	 * Sets this ActionObserver's Response Handler
	 * 
	 * @param responder
	 *            the new Response Handler
	 */
	public void setResponder(IActionResponse<DataType> responder) {
		actionResponder = responder;
	}

	private static final Comparator<Action<?>> queueComparator = new ActionPriorityComparator();

	private PriorityQueue<Action<?>> actions;
	private Queue<Action<?>> tempActions;
	private IActionResponse<DataType> actionResponder;

	/**
	 * Adds tempActions to action, is not thread safe so should be locked
	 */
	private void pushTempActions() {
		while (!tempActions.isEmpty()) {
			actions.offer(tempActions.remove());
		}
	}

	/**
	 * Evaluates all the actions in this actor's queue, and all actions in any
	 * actor it affects.
	 * 
	 */
	public void evalActions() {
		actionsLock.lock();
		Action<?> nextAction;
		pushTempActions();
		while ((nextAction = this.actions.poll()) != null)
			if (evalAction(asDataType(), nextAction))
				nextAction.getTarget().evalActions();
		actionsLock.unlock();
	}

	/**
	 * Evaluates the action on the object. Handles all responses.
	 * 
	 * @param action
	 *            action to be evaluated
	 * @return Whether or not the the action pushed an action on action.source
	 *         if the source is not this actor
	 */
	public boolean evalAction(DataType owner, Action<?> action) {
		return actionResponder.evalAction(owner, action);
	}

	/**
	 * If data is an instance of an Action, then it adds the action to the queue
	 * without notifying observers.
	 */
	public void update(Observable observable, Object data) {
		if (data instanceof Action<?>) {
			tempActionsLock.lock();
			Action<?> action = (Action<?>) data;
			this.pushAction(action, false);
			tempActionsLock.unlock();
		}
	}

	/**
	 * Adds an action to the queue. Notifies all observers of the action
	 * 
	 * @param action
	 * @return whether or not it succeeded.
	 */
	public boolean pushAction(Action<?> action) {
		return pushAction(action, true);
	}

	/**
	 * Adds an action to the queue, can notify observers or not. If it does not
	 * that usually means that it received an action from an observable its
	 * observing
	 * 
	 * @param action
	 *            the action to be enqueued
	 * @param notifyObservers
	 *            whether or not to notify observers
	 * @return whether or not it succeeded
	 */
	private boolean pushAction(Action<?> action, boolean notifyObservers) {
		tempActionsLock.lock();

		if (notifyObservers) {
			this.setChanged();
			this.notifyObservers(action);
		}
		boolean isAdded = tempActions.offer(action);
		tempActionsLock.unlock();
		return isAdded;
	}

	/**
	 * Inner Class used to compare actions for the Priority Queue
	 * 
	 * @author Tyler Dodge
	 * 
	 */
	private static class ActionPriorityComparator implements
			Comparator<Action<? extends Object>> {
		public int compare(Action<? extends Object> action1,
				Action<? extends Object> action2) {
			return action1.compareTo(action2);
		}

	}

	/**
	 * Converts this into the DataType it uses for ActionResponderDecorator. I
	 * chose to go this way rather than just cast it because I prefer to get
	 * compiler errors rather than runtime errors.
	 * 
	 * @return This as that DataType
	 */
	public abstract DataType asDataType();

	/**
	 * Creates the default Response Handler for this ActionObserver.
	 * 
	 * @return The Default Response Handler;
	 */
	public abstract IActionResponse<DataType> createDefaultResponse();

}
