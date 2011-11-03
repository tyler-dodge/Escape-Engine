package edu.ncsu.uhp.escape.engine;

import edu.ncsu.uhp.escape.R;
import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.engine.ActorDieResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.engine.CreateActorResponse;
import edu.ncsu.uhp.escape.engine.actor.*;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;
import edu.ncsu.uhp.escape.engine.actor.actions.GravityAction;
import edu.ncsu.uhp.escape.engine.utilities.Profiler;
import edu.ncsu.uhp.escape.engine.utilities.RenderableData;
import edu.ncsu.uhp.escape.engine.utilities.ZAxisRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

import edu.ncsu.uhp.escape.engine.map.*;
import edu.ncsu.uhp.escape.engine.map.Map;

/**
 * Handles all the engine logic. Extends ActionObserver so it can respond to
 * actions from the Actors and map it contains.
 * 
 * @author Tyler Dodge
 * 
 */
public class Engine extends ActionObserver<Engine> implements Runnable {
	private EngineTickCallback callback;
	private boolean isFinalized;
	private boolean isPaused;
	private GLSurfaceView engineSurface;
	private static final int ACTION_CAPACITY = 10;
	private Actor<?> followActor;
	private Lock actorLock = new ReentrantLock();
	private TemporaryActorQueue actorsToBeAdded;
	private TemporaryActorQueue actorsToBeRemoved;
	private Thread callingThread;

	/**
	 * Creates an instance of the engine
	 * 
	 * @param context
	 *            the android context
	 */
	public Engine(Context context) {
		super(ACTION_CAPACITY);
		actorsToBeAdded = new TemporaryActorQueue();
		actorsToBeRemoved = new TemporaryActorQueue();
		int sizeX = 20;
		int sizeY = 20;
		Tile[][] tiles = new Tile[sizeX][sizeY];
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				tiles[x][y] = Tile
						.fromSourceId(context, R.drawable.grass_basic);

			}
		}
	}
	public void changeMap(Map map)
	{
		this.map=map;
	}
	/**
	 * Creates a default response that is composed of a CreateActorResponse and
	 * an ActorDieResponse
	 */
	@Override
	public IActionResponse<Engine> createDefaultResponse() {
		IActionResponse<Engine> response = new BaseActionResponse<Engine>();
		response = new CreateActorResponse<Engine>(response);
		response = new ActorDieResponse<Engine>(response);
		return response;
	}

	/**
	 * Creates all the actions that the engine creates each iteration
	 * 
	 * @return the actions
	 */
	public Action<?>[] createEngineIterationActions() {
		Action<?>[] actions = { new GravityAction(this),
				new EngineTickAction(this) };
		return actions;
	}

	/**
	 * Kills the engine threads and frees resources.
	 */
	@Override
	public void finalize() {
		isFinalized = true;
	}

	private ArrayList<Actor<?>> actors = new ArrayList<Actor<?>>();
	private Map<?> map;

	/**
	 * Adds an actor to the engine. Adds all other Actors, the Map, and the
	 * engine to its Observer list.
	 * 
	 * @param newActor
	 *            the actor to be added.
	 */
	public void addActor(Actor<?> newActor) {
		actorsToBeAdded.enqueue(newActor);
	}

	/**
	 * Removes the actor from the engine. Removes all references to other
	 * actors, the map, and the engine from its observer list.
	 * 
	 * @param newActor
	 *            the actor to be removed
	 */
	public void removeActor(Actor<?> newActor) {
		actorsToBeRemoved.enqueue(newActor);
	}

	/**
	 * Adds all the actors that are waiting to be added. Used so that the
	 * addition of actors would be threadsafe, however this method itself is not
	 * threadsafe so it must be enclosed by locks.
	 */
	private void addPendingActors() {
		while (!actorsToBeAdded.isEmpty()) {
			Actor<?> newActor = actorsToBeAdded.dequeue();
			newActor.addObserver(this);
			this.addObserver(newActor);
			for (Actor<?> actor : actors) {
				newActor.addObserver(actor);
				actor.addObserver(newActor);
			}
			actors.add(newActor);
		}
	}

	/**
	 * Removes all the actors that are waiting to be removed. Used so that the
	 * removal of actors would be threadsafe, however this method itself is not
	 * threadsafe so it must be enclosed by locks.
	 */
	private void removePendingActors() {
		while (!actorsToBeRemoved.isEmpty()) {
			Actor<?> newActor = actorsToBeRemoved.dequeue();
			newActor.deleteObservers();
			this.deleteObserver(newActor);
			actors.remove(newActor);
		}
	}

	/**
	 * Evaluates all the actors' actions, engine's actions, and map's actions.
	 * Pushes the Engine Iteration Actions onto the engine's stack. These
	 * actions are only pushed from the engine onto actors, so there is no
	 * possibility of actors intercepting engine iteration actions.
	 */
	public void loop() {
		Profiler.getInstance().incrementFrame();
		Profiler.getInstance().startSection("eval actions");
		actorLock.lock();
		addPendingActors();
		removePendingActors();
		Action<?>[] actions = createEngineIterationActions();
		for (Action<?> action : actions) {
			this.pushAction(action);
		}
		Profiler.getInstance().startSection("Eval actor actions");
		for (Actor<?> actor : actors) {
			// Testing to for actor collision, if it does collide, pushes the
			// projectile hit action
			// currently despawns fireball upon cast
			// if(actor.doesCollide(followActor)){
			// actor.pushAction(new ProjectileHitAction(actor, followActor,
			// actor.getPosition()));
			// }
			Profiler.getInstance().startSection(
					actor.toString() + " eval actions");
			actor.evalActions();
			Profiler.getInstance().endSection();
		}
		Profiler.getInstance().endSection();
		Profiler.getInstance().startSection("Eval map actions");
		if (map!=null)
			map.evalActions();
		Profiler.getInstance().endSection();
		Profiler.getInstance().startSection("Engine eval action");
		evalActions();
		Profiler.getInstance().endSection();
		actorLock.unlock();
		Profiler.getInstance().endSection();
	}

	/**
	 * Gets all the renderables that need to get rendered each iteration
	 * 
	 * @param gl
	 *            the opengl context
	 * @return the list of renderables.
	 */
	public Queue<RenderableData> getRenderables(GL10 gl) {
		// the additional 1 is for the map
		Profiler.getInstance().startSection("Generate Renderables list");
		actorLock.lock();
		Queue<RenderableData> renderables;
		renderables = new LinkedList<RenderableData>();

		if (map != null) {

			renderables.add(new RenderableData(map.getRenderable(gl),
					new Point(0, 0, -0.1f), ZAxisRotation.getIdentity()));
		}
		for (Actor<?> actor : actors) {
			renderables.add(new RenderableData(actor.getRenderable(gl), actor
					.getPosition(), actor.getRotation()));
		}
		actorLock.unlock();
		Profiler.getInstance().endSection();
		return renderables;
	}

	/**
	 * Used to allow custom actions by clients of the engine that need to get
	 * run once per iteration.
	 * 
	 * @param callback
	 *            the callback to be called each engine tick.
	 */
	public void setTickCallback(EngineTickCallback callback) {
		this.callback = callback;
	}

	/**
	 * Sets the GlSurface that the engine writes to.
	 * 
	 * @param surface
	 *            the GlSurface that the engine writes to.
	 */
	public void setGlSurface(GLSurfaceView surface) {
		this.engineSurface = surface;
	}

	/**
	 * Gets the actor that the engine's camera is currently following.
	 * 
	 * @return the actor the engine's camera is following.
	 */
	public Actor<?> getFollowActor() {
		return followActor;
	}

	/**
	 * sets the actor that the engine's camera is currently following.
	 * 
	 * @param the
	 *            actor the engine's camera is following.
	 */
	public void setFollowActor(Actor<?> actor) {
		this.followActor = actor;
	}

	/**
	 * Limits the logic tick rate of the engine.
	 * 
	 * @author Tyler Dodge
	 * 
	 */
	private class FrameLimiter extends TimerTask {
		private Timer tickBlocker = new Timer();
		private boolean isOpen;

		/**
		 * starts the tickBlocker scheduler
		 */
		public FrameLimiter() {
			tickBlocker.schedule(this, 1000 / 60, 1000 / 60);
			isOpen = false;
		}

		/**
		 * Waits until tickBlocker's event fires
		 */
		public void blockUntilOpen() {
			while (!isOpen) {
			}
			isOpen = false;
		}

		/**
		 * causes blockUntilOpen to let out any threads using it. Used by
		 * tickBlocker.
		 */
		@Override
		public void run() {
			isOpen = true;

		}
	}

	/**
	 * Runs the engine in the current thread. Since the Engine implements
	 * Runnable, this can be used as the entry point for a thread.
	 */
	public void run() {
		FrameLimiter limiter = new FrameLimiter();
		callingThread = Thread.currentThread();
		while (!isFinalized && !isPaused) {
			if (callback != null)
				callback.tick();
			loop();
			if (engineSurface != null)
				engineSurface.requestRender();
			limiter.blockUntilOpen();
		}
	}

	public void pause() {
		isPaused = true;
	}

	public void unpause() {
		isPaused = false;
	}

	@Override
	public Engine asDataType() {
		return this;
	}

	/**
	 * A thread safe queue used to contain Temporary actors.
	 * 
	 * @author Tyler Dodge
	 * 
	 */
	private class TemporaryActorQueue {
		private Queue<Actor<?>> actors;
		private Lock tempLock;

		public TemporaryActorQueue() {
			tempLock = new ReentrantLock();
			actors = new LinkedList<Actor<?>>();
		}

		public boolean isEmpty() {
			return actors.isEmpty();
		}

		public void enqueue(Actor<?> actor) {
			tempLock.lock();
			actors.add(actor);
			tempLock.unlock();
		}

		public Actor<?> dequeue() {
			tempLock.lock();
			Actor<?> actor = actors.remove();
			tempLock.unlock();
			return actor;
		}
	}

}
