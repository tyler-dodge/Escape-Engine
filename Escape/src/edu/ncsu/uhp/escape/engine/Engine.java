package edu.ncsu.uhp.escape.engine;

import edu.ncsu.uhp.escape.R;
import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.engine.ActorDieResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.engine.CreateActorResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.engine.CreateObserverResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.engine.ObserverRemoveResponse;
import edu.ncsu.uhp.escape.engine.actor.*;
import edu.ncsu.uhp.escape.engine.actor.actions.Action;
import edu.ncsu.uhp.escape.engine.actor.actions.EngineTickAction;
import edu.ncsu.uhp.escape.engine.actor.actions.GravityAction;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
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
	private Lock observerLock = new ReentrantLock();
	private TemporaryQueue<Actor<?>> actorsToBeAdded;
	private TemporaryQueue<Actor<?>> actorsToBeRemoved;
	private TemporaryQueue<ActionObserver<?>> observersToBeAdded;
	private TemporaryQueue<ActionObserver<?>> observersToBeRemoved;
	private static final boolean RENDER_COLLISIONS = true;

	/**
	 * Creates an instance of the engine
	 * 
	 * @param context
	 *            the android context
	 */
	public Engine(Context context) {
		super(ACTION_CAPACITY);
		actorsToBeAdded = new TemporaryQueue<Actor<?>>();
		actorsToBeRemoved = new TemporaryQueue<Actor<?>>();
		observersToBeAdded = new TemporaryQueue<ActionObserver<?>>();
		observersToBeRemoved = new TemporaryQueue<ActionObserver<?>>();
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

	public void changeMap(Map<?> map) {
		this.map = map;
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
		response = new CreateObserverResponse<Engine>(response);
		response = new ObserverRemoveResponse<Engine>(response);
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
	private ArrayList<ActionObserver<?>> observers = new ArrayList<ActionObserver<?>>();
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

	public void addActionObserver(ActionObserver<?> newObserver) {
		observersToBeAdded.enqueue(newObserver);
	}

	public void removeActionObserver(ActionObserver<?> oldObserver) {
		observersToBeRemoved.enqueue(oldObserver);
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

	private void addPendingObservers() {
		while (!observersToBeAdded.isEmpty()) {
			ActionObserver<?> newObserver = observersToBeAdded.dequeue();
			newObserver.addObserver(this);
			this.addObserver(newObserver);
			for (ActionObserver<?> observer : observers) {
				newObserver.addObserver(observer);
				observer.addObserver(newObserver);
			}
			observers.add(newObserver);
		}
	}

	private void removePendingObservers() {
		while (!observersToBeRemoved.isEmpty()) {
			ActionObserver<?> newObserver = observersToBeRemoved.dequeue();
			newObserver.deleteObservers();
			this.deleteObserver(newObserver);
			observers.remove(newObserver);
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
		observerLock.lock();
		addPendingActors();
		removePendingActors();
		addPendingObservers();
		removePendingObservers();
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
					actor.toString() + " eval actions actors");
			actor.evalActions();
			Profiler.getInstance().endSection();
		}
		for (ActionObserver<?> observer : observers) {
			Profiler.getInstance().startSection(
					observer.toString() + " eval actions observers");
			observer.evalActions();
			Profiler.getInstance().endSection();
		}
		Profiler.getInstance().endSection();
		Profiler.getInstance().startSection("Eval map actions");
		if (map != null)
			map.evalActions();
		Profiler.getInstance().endSection();
		Profiler.getInstance().startSection("Engine eval action");
		evalActions();
		Profiler.getInstance().endSection();
		actorLock.unlock();
		observerLock.unlock();
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
			if (RENDER_COLLISIONS) {
				List<ICollision> collisions = actor.getCollisions();
				for (ICollision coll : collisions) {
					renderables.add(new RenderableData(coll.getRenderable(gl),
							actor.getPosition(), actor.getRotation()));
				}
			}
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

		public void finalize() {
			tickBlocker.cancel();
			tickBlocker = null;
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
		while (!isFinalized && !isPaused) {
			if (callback != null)
				callback.tick();
			loop();
			if (engineSurface != null)
				engineSurface.requestRender();
			limiter.blockUntilOpen();
		}
		limiter.finalize();
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
	private class TemporaryQueue<T> {
		private Queue<T> actors;
		private Lock tempLock;

		public TemporaryQueue() {
			tempLock = new ReentrantLock();
			actors = new LinkedList<T>();
		}

		public boolean isEmpty() {
			return actors.isEmpty();
		}

		public void enqueue(T actor) {
			tempLock.lock();
			actors.add(actor);
			tempLock.unlock();
		}

		public T dequeue() {
			tempLock.lock();
			T actor = actors.remove();
			tempLock.unlock();
			return actor;
		}
	}

}
