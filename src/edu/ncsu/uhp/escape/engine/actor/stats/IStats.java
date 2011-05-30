package edu.ncsu.uhp.escape.engine.actor.stats;

public interface IStats {
	/**
	 * Removes all temporary stat decorators from this IStats. This does cause
	 * all IStats to be changed that are not temporary to reflect the removal of
	 * the temporary ones.
	 * 
	 * @return an IStats with no temporary decorators.
	 */
	public IStats removeTemporary();

	/**
	 * Removes all stat decorators that have the specified name
	 * 
	 * @param name
	 *            name to search by
	 * @return an IStats that has all decorators with the specified name
	 *         removed.
	 */
	public IStats removeByName(String name);

	/**
	 * Gets the key value from wrappers.
	 * 
	 * @return total value of key.
	 */
	public int get(String name);
}
