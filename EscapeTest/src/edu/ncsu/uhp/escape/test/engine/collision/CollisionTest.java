package edu.ncsu.uhp.escape.test.engine.collision;

import android.content.BroadcastReceiver;
import android.test.AndroidTestCase;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.collision.OB_BroadPhase;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

public class CollisionTest extends AndroidTestCase {

	public CollisionTest() {
		super();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testBroadPhase() {
		OB_BroadPhase coll1 = new OB_BroadPhase(new Point(1, 1, 1), new Point(
				0, 0, 0));
		OB_BroadPhase coll2 = new OB_BroadPhase(new Point(1, 1, 1), new Point(
				0, 0, 0));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
	}

	public void testNoCollision() {
		BoxCollision coll1 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		BoxCollision coll2 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		assertFalse(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(3, 0, 0),
				ZAxisRotation.getIdentity()));
		assertFalse(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 3, 0),
				ZAxisRotation.getIdentity()));
		assertFalse(coll1.doesCollide(new Point(3, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		assertFalse(coll1.doesCollide(new Point(0, 3, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
	}

	public void testSimpleCollision() {
		BoxCollision coll1 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		BoxCollision coll2 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
	}
}