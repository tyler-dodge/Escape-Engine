package edu.ncsu.uhp.escape.engine.actor.stats;

import java.util.ArrayList;
import java.util.HashMap;

import edu.ncsu.uhp.escape.engine.utilities.KeyValuePair;

/**
 * Used to decorate stats as the name implies. This class is mutable if it is
 * not temporary. Use caution when using the removeTemporary and removeByName
 * methods because the will cause the class to change if the class is not
 * temporary. everything else will not affect the class.
 * 
 * @author Tyler Dodge & Brandon walker
 * 
 */
public abstract class StatDecorator implements IStats {

	private String name;
	private IStats stats;
	private boolean isTemporary;
	private HashMap<String, Integer> values = new HashMap<String, Integer>();
	
	/**
	 * 
	 * @param name of buff
	 * @param isTemporary
	 * @param keyValuePairs to be put into the hashmap.
	 */
	public StatDecorator(String name, boolean isTemporary, ArrayList<KeyValuePair<String, Integer>> keyValuePairs) {
		this.isTemporary = isTemporary;
		this.name = name;
		
		for(int i = 0; i < keyValuePairs.size(); i++){
			values.put(keyValuePairs.get(i).getName(), keyValuePairs.get(i).getValue());
		}
	}

	/**
	 * If this layer is temporary it simply points itself to the previous pointer, 
	 * essentially wiping itself from the decorator stack.
	 */
	public IStats removeTemporary() {
		IStats statData;
		if (isTemporary) {
			statData = stats.removeTemporary();
		} else {
			this.stats = stats.removeTemporary();
			statData = this;
		}
		return statData;
	}

	/**
	 * If this layer's name is specified, it will point itself to the previous pointer 
	 * in the decorator stack, removing itself from the stack.
	 */
	public IStats removeByName(String name) {
		IStats statData;
		if (this.name.equals(name)) {
			statData = stats.removeByName(name);
		} else {
			this.stats = stats.removeByName(name);
			statData = this;
		}
		return statData;
	}

	public int get(String name){
		if (values.containsKey(name)){
			return values.get(name) + stats.get(name);
		}
		else return 0 + stats.get(name);
	}
	
	public String toString() {
		return name + " [" + stats.toString() + " ]";
	}
}
