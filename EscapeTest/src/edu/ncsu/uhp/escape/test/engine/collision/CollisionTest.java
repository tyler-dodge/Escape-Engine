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
	public void testBroadPhaseOffsets() {
		OB_BroadPhase coll1 = new OB_BroadPhase(new Point(1, 1, 1), new Point(
				1, 0, 0));
		OB_BroadPhase coll2 = new OB_BroadPhase(new Point(1, 1, 1), new Point(
				0, 0, 0));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(-0.5f, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, -0.5f, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		
	}
	public void testBroadPhaseNoCollision() {
		OB_BroadPhase coll1 = new OB_BroadPhase(new Point(1, 1, 1), new Point(
				0, 0, 0));
		OB_BroadPhase coll2 = new OB_BroadPhase(new Point(1, 1, 1), new Point(
				0, 0, 0));
		assertFalse(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(3.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertFalse(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(-3.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertFalse(coll1.doesCollide(new Point(3.5f, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		assertFalse(coll1.doesCollide(new Point(-3.5f, 0, 0),
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

	public void testSimpleCollisionWithOffsets() {
		BoxCollision coll1 = new BoxCollision(new Point(1, 1, 1), new Point(1,
				1, 0));
		BoxCollision coll2 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(-0.5f, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, -0.5f, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				ZAxisRotation.getIdentity()));
	}
	public void testRotatedCollision() {
		BoxCollision coll1 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		BoxCollision coll2 = new BoxCollision(new Point(1, 1, 1), new Point(0,
				0, 0));
		ZAxisRotation rotation=new ZAxisRotation((float)(Math.PI/4.0f));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				rotation, coll2, new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0, 0, 0),
				rotation, coll2, new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity()));
		assertTrue(coll1.doesCollide(new Point(0.5f, 0, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				rotation));
		assertTrue(coll1.doesCollide(new Point(0, 0.5f, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				rotation));
		assertTrue(coll1.doesCollide(new Point(0, 1.0f, 0),
				ZAxisRotation.getIdentity(), coll2, new Point(0, 0, 0),
				rotation));				
	}
	private class TestArgs {
		private Point position;
		private IRotation rotation;
		private ICollision collision;
		private Point otherPosition;
		private IRotation otherRotation;
		private ICollision otherCollision;
		private boolean expectedResponse;
		private String uniqueId;

		public TestArgs(String uniqueId, boolean expectedResponse, Point point,
				IRotation rotation, ICollision collision, Point otherPoint,
				IRotation otherRotation, ICollision otherCollision) {
			this.position = point;
			this.uniqueId = uniqueId;
			this.expectedResponse = expectedResponse;
			this.rotation = rotation;
			this.collision = collision;
			this.otherPosition = otherPoint;
			this.otherRotation = otherRotation;
			this.otherCollision = otherCollision;
		}

		public void eval() {
			assertTrue(this.toString(),
					expectedResponse == collision.doesCollide(position,
							rotation, otherCollision, otherPosition,
							otherRotation));
		}

		public String toString() {
			return this.uniqueId + ": Expected Response: " + expectedResponse;
		}
	}

	private static final ICollision standardCollision = new BoxCollision(
			new Point(1, 1, 1), new Point(0, 0, 0));

	private TestArgs test0 = new TestArgs(
	// Test id
			"Test No Collision",
			// Expected collision response
			false,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(10, 10, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, 0),
			// Other collision box
			standardCollision);
	private TestArgs test1 = new TestArgs(
	// Test id
			"Test Same Box",
			// Expected collision response
			true,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(0, 0, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, 0),
			// Other collision box
			standardCollision);
	private TestArgs test2 = new TestArgs(
	// Test id
			"Test Same Edge",
			// Expected collision response
			true,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(1, 0, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, 0),
			// Other collision box
			standardCollision);
	private TestArgs test3 = new TestArgs(
	// Test id
			"Test Same Corner",
			// Expected collision response
			true,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(1, 1, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, 0),
			// Other collision box
			standardCollision);
	private TestArgs test4 = new TestArgs(
	// Test id
			"Test Same Box One Rotated 45",
			// Expected collision response
			true,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(0, 0, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, (float) Math.PI / 4),
			// Other collision box
			standardCollision);
	private TestArgs test5 = new TestArgs(
	// Test id
			"Test Different Height No Collision",
			// Expected collision response
			false,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(0, 0, 10),
			// Other collision box's rotation
			new EulerAngles(0, 0, 0),
			// Other collision box
			standardCollision);
	private TestArgs test6 = new TestArgs(
	// Test id
			"Test Different Heigh With Collision",
			// Expected collision response
			true,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(0, 0, 0.5f),
			// Other collision box's rotation
			new EulerAngles(0, 0, 0),
			// Other collision box
			standardCollision);
	private TestArgs test7 = new TestArgs(
	// Test id
			"Test Rotated Box No Collision",
			// Expected collision response
			false,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(10, 10, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, (float) Math.PI / 4),
			// Other collision box
			standardCollision);
	private TestArgs test8 = new TestArgs(
	// Test id
			"Test Rotated Box No Collision Corners in range of sides",
			// Expected collision response
			false,
			// This collision box's position
			new Point(0, 0, 0),
			// This collision box's rotation
			new EulerAngles(0, 0, 0),
			// This collision box
			standardCollision,
			// Other collision box's position
			new Point(1, 1.1f, 0),
			// Other collision box's rotation
			new EulerAngles(0, 0, (float) Math.PI / 4),
			// Other collision box
			standardCollision);;
	private TestArgs[] testArgs = { test0, test1, test2, test3, test4, test5,
			test6, test7, test8 };

	public void testDoesCollide() {
		for (TestArgs arg : testArgs) {
			arg.eval();
		}
	}
}