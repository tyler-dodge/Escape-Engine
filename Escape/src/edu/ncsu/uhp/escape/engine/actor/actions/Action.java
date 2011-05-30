package edu.ncsu.uhp.escape.engine.actor.actions;

import edu.ncsu.uhp.escape.engine.ActionObserver;

/**
 * Used to handle actor interaction. The idea is that since all changes to
 * actors will be cataloged, all changes can occur in a single pass, rather than
 * whenever the pushAction is called, which will make it simpler to debug
 * changes (and log changes for that matter.) Also, makes it predictable when
 * changes occur.
 * 
 * @author Tyler Dodge
 * 
 */
public class Action<DataType> implements Comparable<Action<?>> {
	/*
	 * The standard priority of actions created by the system. Should be used
	 * rather than creating other priorities unless necessary.
	 */
	public static final int STANDARD_PRIORITY = 100;

	private int priority;
	private String name;
	private DataType data;
	private ActionObserver<?> target;
	private ActionObserver<?> source;

	public Action(ActionObserver<?> source, ActionObserver<?> target, String name, DataType data) {
		this(source, target, name, data, STANDARD_PRIORITY);
	}

	public Action(ActionObserver<?> source, String name, DataType data) {
		this(source, name, data, STANDARD_PRIORITY);
	}

	public Action(ActionObserver<?> source, String name, DataType data, int priority) {
		this(source, source, name, data, priority);
	}

	public Action(ActionObserver<?> source, ActionObserver<?> target, String name, DataType data,
			int priority) {
		this.target = target;
		this.source = source;
		this.priority = priority;
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public DataType getData() {
		return data;
	}

	public int getPriority() {
		return priority;
	}

	public ActionObserver<?> getTarget() {
		return target;
	}

	public ActionObserver<?> getSource() {
		return source;
	}

	/**
	 * Compares based on priority.
	 */
	public int compareTo(Action<?> other) {
		int result = 0;
		if (this.getPriority() > other.getPriority())
			result = 1;
		else if (this.getPriority() < other.getPriority())
			result = -1;
		return result;
	}
}
