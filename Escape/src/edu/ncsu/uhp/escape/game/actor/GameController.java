package edu.ncsu.uhp.escape.game.actor;

import java.util.ArrayList;

import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Nexus;
import edu.ncsu.uhp.escape.engine.actor.Track;
import edu.ncsu.uhp.escape.game.actionresponse.actor.EnemyDeathResponse;
import edu.ncsu.uhp.escape.game.actionresponse.actor.LoseHealthResponse;
import edu.ncsu.uhp.escape.game.actionresponse.actor.SpawnerTickResponse;

/**
 * Game Controller is the centralized class that holds everything about the game including the players money, health, and statistics.
 * @author Mallific
 *
 */
public class GameController extends ActionObserver<GameController>{

	public static final int ACTION_CAPACITY = 100;
	private Spawner spawner;
	private Nexus nexus;
	private Track track;

	private int health = 100;
	private int money = 500;

	private boolean GUIValuesChanged = true;
	
	private ArrayList<Enemy<?>> activeEnemies = new ArrayList<Enemy<?>>();
	
	public GameController(int capacity, Nexus nexus, Track track, Spawner spawner) {
		super(capacity);
		this.nexus = nexus;
		this.track = track;
		this.spawner = spawner;
	}

	public GameController(Nexus nexus, Track track, Spawner spawner){
		this(ACTION_CAPACITY, nexus, track, spawner);
	}
	
	public boolean getGUIValuesChanged(){
		return this.GUIValuesChanged;
	}
	
	public void setGUIValuesChanged(boolean GUIValuesChanged){
		this.GUIValuesChanged = GUIValuesChanged;
	}
	
	public boolean getWaveOver(){
		return activeEnemies.isEmpty() && spawner.getEnemyDictionary().queuedEnemies.isEmpty();
	}
	
	public boolean getGameOver(){
		return health <= 0;
	}
	
	public void resetGame(){
		spawner.setWave(0);
		spawner.nextWave();
		health = 100;
		money = 500;
		GUIValuesChanged = true;
	}
	
	@Override
	public GameController asDataType() {
		return this;
	}

	@Override
	public IActionResponse<GameController> createDefaultResponse() {
		IActionResponse<GameController> responder = new BaseActionResponse<GameController>();
		responder = new SpawnerTickResponse(responder);
		responder = new EnemyDeathResponse(responder);
		responder = new LoseHealthResponse(responder);
		return responder;
	} 
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		GUIValuesChanged = true;
	}
	
	public void damageHealth(int health){
		this.health = this.health - health;
		GUIValuesChanged = true;
	}
	
	public void addHealth(int health){
		this.health = this.health + health;
		GUIValuesChanged = true;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
		GUIValuesChanged = true;
	}
	
	public void addMoney(int money){
		this.money = this.money + money;
		GUIValuesChanged = true;
	}
	
	public void spendMoney(int money){
		this.money = this.money - money;
		GUIValuesChanged = true;
	}

	public ArrayList<Enemy<?>> getActiveEnemies() {
		return activeEnemies;
	}

	public void setActiveEnemies(ArrayList<Enemy<?>> activeEnemies) {
		this.activeEnemies = activeEnemies;
	}

	public void nextWave(){
		spawner.nextWave();
	}
	
	public Spawner getSpawner() {
		return spawner;
	}

	public void setSpawner(Spawner spawner) {
		this.spawner = spawner;
	}

	public Nexus getNexus() {
		return nexus;
	}

	public void setNexus(Nexus nexus) {
		this.nexus = nexus;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

}
