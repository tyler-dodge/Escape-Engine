package edu.ncsu.uhp.escape.test.engine.collision;

import android.test.AndroidTestCase;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.collision.ICollision;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import edu.ncsu.uhp.escape.engine.utilities.*;

public class CollisionTest extends AndroidTestCase {
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

	public CollisionTest() {
		super();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testDoesCollide() {
		for (TestArgs arg : testArgs) {
			arg.eval();
		}
	}
}