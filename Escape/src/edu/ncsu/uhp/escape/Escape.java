package edu.ncsu.uhp.escape;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.EngineTickCallback;
import edu.ncsu.uhp.escape.engine.actor.BaseAttackTurret;
import edu.ncsu.uhp.escape.engine.actor.BaseEnemyBlob;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Nexus;
import edu.ncsu.uhp.escape.engine.actor.Track;
import edu.ncsu.uhp.escape.engine.actor.Turret;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateObserverAction;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.MoveAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PostMoveAction;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.*;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.game.actor.Spawner;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * This is the actual game in terms of setting up the map and actors. GUI events
 * will eventually be moved to it's own separate class, but stand alone for now.
 * 
 * @author Tyler Dodge & Brandon Walker
 * 
 */
public class Escape extends Activity {
	private Engine engine;
	private Enemy<BaseEnemyBlob> white;
	private EscapeSurfaceView glSurface;
	private Thread engineLoopThread;
	private IRotation rotation;
	private Track track;
	private Nexus nexus;
	private boolean placingTurret;
	private boolean selectedTurret;
	private Turret<?> currentTurret;
	float turretRelX;
	float turretRelY;
	private float ratioX;
	private float ratioY;
	public static final float FOV = 45f;
	public static float distanceZ;
	private static float widthX;
	private static final float heightY = 82.75987f;
	private static float aspectRatio;
	public static final float DISTANCE_FROM_CLOSE_PLANE = 0.1f;
	public Spawner spawner;
	private boolean isPaused;

	@Override
	public void onStop() {
		super.onStop();
		engine.pause();
		try {
			engineLoopThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isPaused = true;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isPaused) {
			engine.unpause();
			engineLoopThread = new Thread(engine);
			engineLoopThread.start();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		engine.finalize();
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		finish();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			ratioX = getWidthX() / glSurface.getWidth();
			ratioY = getHeightY() / glSurface.getHeight();
			float relX = event.getX() * ratioX;
			float relY = (glSurface.getHeight() - event.getY()) * ratioY;

			if(selectedTurret){
					BoxCollision collisionBox = new BoxCollision(new Point(5, 5, 5),
							new Point(-2.5f, -2.5f, -2.5f));
					List<ICollision> box = new ArrayList<ICollision>();
					box.add(collisionBox);
					
		        	currentTurret = new BaseAttackTurret(new Point(relX, relY, 0), new ZAxisRotation(0f),
		    				new ImageSource( 0, R.drawable.basic_tree, new Point(5, 5, 0), new Point(
		    								-2.5f, -2.5f, 1)), box);
		        	engine.pushAction(new CreateActorAction(engine, currentTurret));
		        	placingTurret = true;
		        	selectedTurret = false;
			}
				
			if(placingTurret){	
				turretRelX = relX;
				turretRelY = relY;
			}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if(currentTurret != null){
					placingTurret = false;
					if(!currentTurret.placeable()){
						engine.pushAction(new DieAction(engine, currentTurret));
					}else{
						currentTurret.place();
					}
					currentTurret = null;
				}
			}
		/*	
			else if (event.getAction() == MotionEvent.ACTION_POINTER_UP) {
			float midX = (event.getX(0) + event.getX(1)) / 2;
			float midY = (event.getY(0) + event.getY(1)) / 2;
			if (centerX == -1)
				centerX = (glSurface.getLeft() + glSurface.getWidth()) / 2;
			if (centerY == -1)
				centerY = (glSurface.getTop() + glSurface.getHeight()) / 2;
			float relX = centerX - midX;
			float relY = centerY - midY;
			float angle = (float) Math.atan(relY / relX) + 3.14f;
			if (relX == 0 && relY == 0) {
				angle = 0;
			} else if (relY == 0) {
				if (relX > 0) {
					angle = 0;
				} else {
					angle = 3.14f;
				}
			} else if (relX == 0) {
				if (relY > 0) {
					angle = 1.57f;
				} else
					angle = 4.71f;
			}
			if (relX < 0) {
				angle += 3.14;
			}
			spawnRotation = new ZAxisRotation(angle);
			white.pushAction(new FireballCastAction(white, "Fireball",
					spawnRotation));
		}*/

		return false;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		engine = new Engine(getApplicationContext());
		glSurface = (EscapeSurfaceView) findViewById(R.id.engineSurface);
		glSurface.setEngine(engine);
		
		ArrayList<Point> points = TrackPointDictionary.getInstance().getLevelPointList("FIRST");
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		aspectRatio = (float) metrics.widthPixels / metrics.heightPixels;
		distanceZ = (float) -(((heightY / Math.tan(Math.toRadians(FOV) / 2)) / 2) + DISTANCE_FROM_CLOSE_PLANE);
		widthX = heightY * aspectRatio;
		
		track = new Track(new Point(0,0,0), new ImageSource(0,
				R.drawable.track1, new Point(widthX, heightY, 0), new Point(
						0, 0, 0)), points);
		
		
		BoxCollision nexusCollision = new BoxCollision(new Point(5, 5, 5),
				new Point(0, 0, 0));
		List<ICollision> nexusBox = new ArrayList<ICollision>();
		nexusBox.add(nexusCollision);
		
		nexus = new Nexus(new Point(widthX/2 - 2, 0, 0), new ZAxisRotation(.5f), new ImageSource(0,
						R.drawable.nexusdemo, new Point(10, 5, 0), new Point(
								-5, -2.5f, 0)), nexusBox);
		
		spawner = new Spawner(1, "FIRST");
		engine.pushAction(new CreateObserverAction(engine, spawner));

		engine.pushAction(new CreateActorAction(engine, track));
		engine.pushAction(new CreateActorAction(engine, nexus));

		engineLoopThread = new Thread(engine);
		engineLoopThread.start();
		engine.setGlSurface(glSurface);
		engine.setTickCallback(callback);
	}

	private EngineCallback callback = new EngineCallback();

	private class EngineCallback implements EngineTickCallback {

		public void tick() {

			if (placingTurret) {
				//Always push and test for collision before setting the position or you will get "color change" lag for the turret
				currentTurret.pushAction(new PostMoveAction(currentTurret, currentTurret.getPosition(), new Point(turretRelX, turretRelY, 1)));
				currentTurret.testStillColliding();
				currentTurret.setPosition(new Point(turretRelX, turretRelY, 1));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.turret_overlay, menu);
		return true;
	}
	
	 @Override
     public boolean onOptionsItemSelected(MenuItem item)
     {
         switch(item.getItemId())
         {
             case 0:
            	 selectedTurret = true;
            	 return true;
         }
             return super.onOptionsItemSelected(item);
     }
		
		public static float getWidthX() {
			return widthX;
		}

		public static float getHeightY() {
			return heightY;
		}

		public static float getAspectRatio(){
			return aspectRatio;
		}
}