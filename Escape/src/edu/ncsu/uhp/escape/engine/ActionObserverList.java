package edu.ncsu.uhp.escape.engine;

import java.util.ArrayList;
import java.util.List;

public class ActionObserverList<T extends ActionObserver<?>> extends
		ControlledList<T> {
	private List<ActionObserverList<?>> observedLists = new ArrayList<ActionObserverList<?>>();

	public void addObservedList(ActionObserverList<?> list) {
		observedLists.add(list);
	}

	/**
	 * Adds all the actors that are waiting to be added. Used so that the
	 * addition of actors would be thread safe, however this method itself is
	 * not thread safe so it must be enclosed by locks.
	 */
	public void flushAdd(ActionObserver<?> engine) {
		T newObserver;
		while ((newObserver = this.flushAddOnce()) != null) {
			newObserver.addObserver(engine);
			engine.addObserver(newObserver);
			for (T observer : this) {
				newObserver.addObserver(observer);
				observer.addObserver(newObserver);
			}
			for (ActionObserverList<?> list : observedLists) {
				for (ActionObserver<?> observer : list) {
					newObserver.addObserver(observer);
					observer.addObserver(newObserver);
				}
			}
		}
	}

	/**
	 * Removes all the actors that are waiting to be removed. Used so that the
	 * removal of actors would be threadsafe, however this method itself is not
	 * threadsafe so it must be enclosed by locks.
	 */
	public void flushRemove(ActionObserver<?> engine) {
		T newObserver = null;
		while ((newObserver = this.flushRemoveOnce()) != null) {
			newObserver.deleteObservers();
			engine.deleteObserver(newObserver);
		}
	}

	public void flush(ActionObserver<?> engine) {
		flushAdd(engine);
		flushRemove(engine);
	}

}
