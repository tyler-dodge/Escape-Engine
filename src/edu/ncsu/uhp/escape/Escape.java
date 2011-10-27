package edu.ncsu.uhp.escape;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.EngineTickCallback;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.CreateRubbleResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.FireballCastResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.actor.TravelTrackOnTickResponse;
import edu.ncsu.uhp.escape.engine.actor.BaseEnemyBlob;
import edu.ncsu.uhp.escape.engine.actor.Enemy;
import edu.ncsu.uhp.escape.engine.actor.Mage;
import edu.ncsu.uhp.escape.engine.actor.Npc;
import edu.ncsu.uhp.escape.engine.actor.Track;
import edu.ncsu.uhp.escape.engine.actor.Tree;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.FireballCastAction;
import edu.ncsu.uhp.escape.engine.actor.actions.MoveAction;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.*;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * This is the actual game in terms of setting up the map and actors. GUI events will
 * eventually be moved to it's own separate class, but stand alone for now.
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
	private IRotation spawnRotation;
	private float centerX = -1;
	private float centerY = -1;
	private Enemy<BaseEnemyBlob> track;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			// Really really really really
			// Really really really really
			// Really really really really
			// Really really really really
			// Really really really really
			// lazy rotational movement
			if (centerX == -1)
				centerX = (glSurface.getLeft() + glSurface.getWidth()) / 2;
			if (centerY == -1)
				centerY = (glSurface.getTop() + glSurface.getHeight()) / 2;
			float relX = centerX - event.getX();
			float relY = centerY - event.getY();
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
			rotation = new ZAxisRotation(angle);
			movePlayer = true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			movePlayer = false;
		} else if (event.getAction() == MotionEvent.ACTION_POINTER_UP) {
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
		}

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

		BoxCollision whiteCollision = new BoxCollision(new Point(5, 5, 5),
				new Point(-2.5f, -2.5f, 0));
		List<ICollision> whiteBox = new ArrayList<ICollision>();
		whiteBox.add(whiteCollision);
		white = new BaseEnemyBlob(new Point(5, 5, 0), new ZAxisRotation(0),
				new ImageSource(getApplicationContext(), 0,
						R.drawable.mage_ani_1, new Point(5, 5, 1), new Point(
								-2.5f, -2.5f, 5)), whiteBox, NodalTrack.getInstanceForTrackLevel("FIRST"));
		white.setResponder(new TravelTrackOnTickResponse<BaseEnemyBlob>(white.getResponder()));
		rotation = white.getRotation();
		
		/*ArrayList<Point> points = TrackPointDictionary.getInstance().getLevelPointList("FIRST");
		List<ICollision> trackBox = new ArrayList<ICollision>();

		
		track = new Track(new ImageSource(getApplicationContext(), 0,
				R.drawable.track1, new Point(100, 100, 1), new Point(
						-2.5f, -2.5f, 5)), trackBox, points);
		
		engine.addObserver(track);
		*/
		track = new BaseEnemyBlob(new Point(0, 0, 2), new ZAxisRotation(1.57f),
				new ImageSource(getApplicationContext(), 0,
						R.drawable.track1, new Point(85, 55, 1), new Point(
								-42, -28, 0)), whiteBox, NodalTrack.getInstanceForTrackLevel("FIRST"));
		white.setResponder(new TravelTrackOnTickResponse<BaseEnemyBlob>(white.getResponder()));
		rotation = white.getRotation();
		engine.pushAction(new CreateActorAction(engine, white));
		engine.pushAction(new CreateActorAction(engine, track));
		//engine.setTrack(track);
		
		engineLoopThread = new Thread(engine);
		engineLoopThread.start();
		engine.setGlSurface(glSurface);
		engine.setTickCallback(callback);
	}

	private boolean movePlayer;
	private EngineCallback callback = new EngineCallback();

	private class EngineCallback implements EngineTickCallback {

		public void tick() {

			if (movePlayer) {
				white.pushAction(new MoveAction(white, white.getPosition(),
						rotation));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.turret_overlay, menu);
	    return true;
	}
}