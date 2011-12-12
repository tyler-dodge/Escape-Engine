package edu.ncsu.uhp.escape;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.EngineTickCallback;
import edu.ncsu.uhp.escape.engine.actor.BaseAttackTurret;
import edu.ncsu.uhp.escape.engine.actor.Nexus;
import edu.ncsu.uhp.escape.engine.actor.Track;
import edu.ncsu.uhp.escape.engine.actor.Turret;
import edu.ncsu.uhp.escape.engine.actor.actions.CreateActorAction;
import edu.ncsu.uhp.escape.engine.actor.actions.DieAction;
import edu.ncsu.uhp.escape.engine.actor.actions.PostMoveAction;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.Graphic;
import edu.ncsu.uhp.escape.engine.utilities.ImageSource;
import edu.ncsu.uhp.escape.engine.utilities.TextGraphic;
import edu.ncsu.uhp.escape.engine.utilities.TrackPointDictionary;
import edu.ncsu.uhp.escape.engine.utilities.ZAxisRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.game.actor.GameController;
import edu.ncsu.uhp.escape.game.actor.Spawner;
import edu.ncsu.uhp.escape.game.utilities.TurretOverlay;

/**
 * This is the actual game in terms of setting up the map and actors. GUI events
 * will eventually be moved to it's own separate class, but stand alone for now.
 * 
 * @author Tyler Dodge & Brandon Walker
 * 
 */
public class Escape extends Activity {
	/**
	 * Screen Properties
	 */
	private float ratioX;
	private float ratioY;
	public static final float FOV = 45f;
	public static float distanceZ;
	private static float widthX;
	private static final float heightY = 82.75987f;
	private static float aspectRatio;
	public static final float DISTANCE_FROM_CLOSE_PLANE = 0.1f;

	/**
	 * Game engine variables
	 */
	private Engine engine;
	private EscapeSurfaceView glSurface;
	private Thread engineLoopThread;

	/**
	 * Coordinates for ontouch events
	 */
	float turretRelX;
	float turretRelY;

	/**
	 * Gui states and variables
	 */
	private boolean isPaused;
	private boolean placingTurret;
	private boolean placedTurret;
	private boolean selectedTurret;
	private Turret<?> currentTurret;
	private TurretOverlay turretSelection;


	/**
	 * Popup Dialogue constants
	 */
	static final int DIALOG_NEXT_WAVE_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	static final int TURRET_SELECTION_MENU = 2;
	static final String NEXT_WAVE = "NEXT WAVE";
	static final String GAME_OVER = "GAME OVER";

	// Game controller
	private GameController game;

	// GUI Handler
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String event = (msg.obj == null)? "" : msg.obj.toString();
			if(event.equals(NEXT_WAVE)){
				showDialog(DIALOG_NEXT_WAVE_ID);
			}else if(event.equals(GAME_OVER)){
				showDialog(DIALOG_GAMEOVER_ID);
			}
		}
	};

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
		callback.finalize();
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
					BoxCollision collisionBox = new BoxCollision(new Point(5, 5f, 5),
							new Point(-2.5f, -2.5f, -2.5f));
					List<ICollision> box = new ArrayList<ICollision>();
					box.add(collisionBox);
					
					currentTurret = new BaseAttackTurret(new Point(relX, relY, 0), new ZAxisRotation(0),
		    				new ImageSource(0, R.drawable.turret, new Point(5, 5f, 0), new Point(-2.5f, -2.5f, 1)), box);

				engine.pushAction(new CreateActorAction(engine, currentTurret));
				placingTurret = true;
				selectedTurret = false;
			}

			if (placingTurret) {
				turretRelX = relX;
				turretRelY = relY;
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (currentTurret != null) {
					placedTurret = true;
			}
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ( keyCode == KeyEvent.KEYCODE_MENU ) {
	    	if(turretSelection == null){
				showDialog(TURRET_SELECTION_MENU);
	    	}else{
				if(turretSelection.isShowing()){
					turretSelection.dismiss();
				} else{
					showDialog(TURRET_SELECTION_MENU);
				}
	    	}
	        return true;
	        //Needs to be true!
	    }
	    return super.onKeyDown(keyCode, event);
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

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		aspectRatio = (float) metrics.widthPixels / metrics.heightPixels;
		distanceZ = (float) -(((heightY / Math.tan(Math.toRadians(FOV) / 2)) / 2) + DISTANCE_FROM_CLOSE_PLANE);
		widthX = heightY * aspectRatio;

		ArrayList<Point> points = TrackPointDictionary.getInstance()
				.getLevelPointList("FIRST");

		Track track = new Track(new Point(0, 0, 0), new ImageSource(0,
				R.drawable.track1, new Point(widthX, heightY, 0), new Point(0,
						0, 0)), points);

		BoxCollision nexusCollision = new BoxCollision(new Point(5, 5, 5),
				new Point(-2.5f, -2.5f, -2.5f));
		List<ICollision> nexusBox = new ArrayList<ICollision>();
		nexusBox.add(nexusCollision);

		Nexus nexus = new Nexus(new Point(widthX / 2 - 2, 0, 0),
				new ZAxisRotation(0f), new ImageSource(0, R.drawable.turret,
						new Point(10, 10, 0), new Point(-5, -5, 0)), nexusBox);

		Spawner spawner = new Spawner(1, "FIRST");		
		
		game = new GameController(nexus, track, spawner);
		engine.addActionObserver(spawner);
		engine.addActor(track);
		engine.addActor(nexus);
		engine.addActionObserver(game);

		engineLoopThread = new Thread(engine);
		engineLoopThread.start();
		engine.addGraphic(new TextGraphic(new Point(widthX - 30.8f, heightY - 5.45f, 0), "Money:", 2.3f, 255,
				0, 0, 255));
		engine.addGraphic(new TextGraphic(new Point(widthX - 15, heightY - 5, 0), "Health:", 1.85f, 255,
				0, 0, 255));
		engine.setGlSurface(glSurface);
		engine.setTickCallback(callback);

}


	public void selectTurret(View view){
		view.findViewById(R.id.ImageButton1);
		if(game.getMoney() >= BaseAttackTurret.BASE_ATTACK_TURRET_COST){
			selectedTurret = true;
			game.spendMoney(BaseAttackTurret.BASE_ATTACK_TURRET_COST);
		}
		turretSelection.dismiss();
	}
	
	private EngineCallback callback = new EngineCallback();

	private class EngineCallback implements EngineTickCallback {
		Handler innerHandler;
		private Thread looperThread;
		public static final int TERMINATE_LOOPER = 1;
		public static final int CONTINUE_LOOPER = 0;
		private int health;
		private int money;
		private Graphic healthGraphic;
		private Graphic moneyGraphic;

		public EngineCallback() {
			looperThread = new Thread(new Runnable() {
				public void run() {
					Looper.prepare();
					innerHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (msg.arg1 == TERMINATE_LOOPER) {
								Looper.myLooper().quit();
							} else {
								handler.sendMessage(msg);
							}
						};
					};

					Looper.loop();
				}

			});
			looperThread.start();
		}

		public void finalize() {
			Message endMessage = new Message();
			endMessage.arg1 = 1;
			innerHandler.sendMessage(endMessage);
		}

		public void tick() {

			if (placingTurret) {
				//Always push and test for collision before setting the position or you will get "color change" lag for the turret
				currentTurret.pushAction(new PostMoveAction(currentTurret, currentTurret.getPosition(), new Point(turretRelX, turretRelY, 1)));
				currentTurret.testStillColliding();
				currentTurret.setPosition(new Point(turretRelX, turretRelY, 1));
			}
			if(placedTurret){
				placingTurret = false;
				if(!currentTurret.placeable()){
					engine.pushAction(new DieAction(engine, currentTurret));
					game.addMoney(BaseAttackTurret.BASE_ATTACK_TURRET_COST);
				}else{
					currentTurret.place();
				}
				currentTurret = null;
				placedTurret = false;
			}
			if(game.getGameOver()){
				Escape.this.finish();
			}
			if(game.getGUIValuesChanged()){
				if(money != game.getMoney()){
					money = game.getMoney();
					engine.removeGraphic(moneyGraphic);
					moneyGraphic = new TextGraphic(new Point(widthX - 22.9f, heightY - 5, 0), String.valueOf(money), 2, 255,0, 0, 255);
					engine.addGraphic(moneyGraphic);
				}
				if(health != game.getHealth()){
					health = game.getHealth();
					engine.removeGraphic(healthGraphic);
					healthGraphic = new TextGraphic(new Point(widthX - 7, heightY - 5, 0), String.valueOf(health), 2, 255,0, 0, 255);
					engine.addGraphic(healthGraphic);
				}
				game.setGUIValuesChanged(false);
			}
			if(game.getWaveOver()){
				//engine.pause();
				Message message = new Message();
				message.obj = NEXT_WAVE;
				innerHandler.dispatchMessage(message);
			}
		}

	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		switch (id) {
		case DIALOG_NEXT_WAVE_ID:
			AlertDialog.Builder waveBuilder = new AlertDialog.Builder(this);
			waveBuilder
					.setMessage("You beat the wave! Do you want to continue?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									game.nextWave();
									dialog.dismiss();
									//engine.unpause();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Escape.this.finish();
								}
							});
			dialog = waveBuilder.create();
			break;
		case DIALOG_GAMEOVER_ID:
			AlertDialog.Builder gameOverBuilder = new AlertDialog.Builder(this);
			gameOverBuilder
					.setMessage("Game Over! Do you want to start over?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									game.resetGame();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Escape.this.finish();
								}
							});
			dialog = gameOverBuilder.create();
			break;
		case TURRET_SELECTION_MENU:
			turretSelection = new TurretOverlay(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
			turretSelection.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
			turretSelection.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			turretSelection.setContentView(R.layout.turret_overlay);
			turretSelection.setOwnerActivity(this);
			dialog = turretSelection;
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	public static float getWidthX() {
		return widthX;
	}

	public static float getHeightY() {
		return heightY;
	}

	public static float getAspectRatio() {
		return aspectRatio;
	}
}