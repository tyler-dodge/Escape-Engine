package edu.ncsu.uhp.escape.game.actor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import edu.ncsu.uhp.escape.R;
import edu.ncsu.uhp.escape.engine.ActionObserver;
import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actor.BaseEnemyBlob;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.ImageSource;
import edu.ncsu.uhp.escape.engine.utilities.NodalTrack;
import edu.ncsu.uhp.escape.engine.utilities.TrackPointDictionary;
import edu.ncsu.uhp.escape.engine.utilities.ZAxisRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.game.actionresponse.actor.SpawnerTickResponse;

public class Spawner extends ActionObserver<Spawner>{

	private int wave;
	public static final int ACTION_CAPACITY = 100;
	private EnemyDictionary enemyDictionary;
	private String level;
	private Context context;
	
	public Spawner(int capacity, int wave, String level, Context context) {
		super(capacity);
		this.wave = wave;
		this.level = level;
		this.context = context;
		//Always needs to be last
		this.enemyDictionary = new EnemyDictionary();
	}

	public Spawner(int wave, String level, Context context){
		this(ACTION_CAPACITY, wave, level, context);
	}
	
	@Override
	public Spawner asDataType() {
		return this;
	}

	@Override
	public IActionResponse<Spawner> createDefaultResponse() {
		IActionResponse<Spawner> responder = new BaseActionResponse<Spawner>();
		responder = new SpawnerTickResponse(responder);
		return responder;

	}
	
	public void nextWave(){
		wave ++;
		enemyDictionary.calculateEnemyRepository(wave);
	}

	public class EnemyDictionary{
		
		LinkedList<Enemy<?>> queuedEnemies;
				
		public EnemyDictionary() {
			calculateEnemyRepository(getWave());
		}
		
		public void calculateEnemyRepository(int wave){
			queuedEnemies = new LinkedList<Enemy<?>>();
			//Add five enemies for now, will make enemy constructor/strength calculator later
			ArrayList<Point> points = TrackPointDictionary.getInstance().getLevelPointList(getLevel());
			BoxCollision enemyCollision = new BoxCollision(new Point(5, 5, 3),
					new Point(0f, 0f, 0f));
			List<ICollision> enemyBox = new ArrayList<ICollision>();
			enemyBox.add(enemyCollision);
			
			for(int i = 0; i < 5; i++){
				Enemy<BaseEnemyBlob> enemy = new BaseEnemyBlob(points.get(0), new ZAxisRotation(0),
						new ImageSource(getContext(), 0,
								R.drawable.mage_ani_1, new Point(5, 5, 0), new Point(
										-2.5f, -2.5f, 0)), enemyBox, NodalTrack.getInstanceForTrackLevel(getLevel()));
				queuedEnemies.add(enemy);
			}
		}
		
		public Enemy<?> next(){
			return queuedEnemies.poll();
		}
		
		public boolean hasNext(){
			return queuedEnemies.getFirst() != null;
		}
	}
	
	public EnemyDictionary getEnemyDictionary(){
		return this.enemyDictionary;
	}
	
	public int getWave(){
		return this.wave;
	}
	
	public void setWave(int wave){
		this.wave = wave;
	}
	
	public String getLevel(){
		return this.level;
	}
	
	public void setLevel(String level){
		this.level = level;
	}

	public Context getContext(){
		return this.context;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
}
