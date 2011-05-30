package edu.ncsu.uhp.escape.engine.utilities;

/**
 * Generic-fied key value pair object for the hashmap system.
 * 
 * @author Brandon Walker
 *
 * @param <KeyType> key name
 * @param <ValueType> value
 */
public class KeyValuePair<KeyType, ValueType> {
	
	private KeyType name;
	private ValueType value;
	public KeyValuePair(KeyType name, ValueType value){
		this.name = name;
		this.value = value;
	}
	
	public KeyType getName(){
		return this.name;
	}
	
	public ValueType getValue(){
		return this.value;
	}
}
