package edu.ncsu.uhp.escape.test.engine.utilities;

import edu.ncsu.uhp.escape.engine.utilities.EulerAngles;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;
import android.test.AndroidTestCase;

public class EulerAnglesTest extends AndroidTestCase {
	public static float[] testYaws = { 0, (float) Math.PI / 4,
			(float) Math.PI / 3, (float) Math.PI / 2, (float) Math.PI };
	public static float[] testPitches = { 0, (float) Math.PI / 4,
			(float) Math.PI / 3, (float) Math.PI / 2, (float) Math.PI };
	public static float[] testRolls = { 0, (float) Math.PI / 4,
			(float) Math.PI / 3, (float) Math.PI / 2, (float) Math.PI };

	public EulerAnglesTest() {
		super();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testEulerAnglesConstructor() {
		for (int i = 0; i < testYaws.length; i++) {
			for (int j = 0; j < testPitches.length; j++) {
				for (int k = 0; k < testRolls.length; k++) {
					EulerAngles testAngles = new EulerAngles(testYaws[i],
							testPitches[j], testRolls[k]);
					assertEquals(testYaws[i], testAngles.getYaw());
					assertEquals(testPitches[j], testAngles.getPitch());
					assertEquals(testRolls[k], testAngles.getRoll());
				}
			}
		}
	}

	public void testRotate() {
		for (int i = 0; i < testYaws.length; i++) {
			for (int j = 0; j < testPitches.length; j++) {
				for (int k = 0; k < testRolls.length; k++) {
					for (int a = 0; a < testYaws.length; a++) {
						for (int b = 0; b < testPitches.length; b++) {
							for (int c = 0; c < testRolls.length; c++) {
								EulerAngles testAngles = new EulerAngles(
										testYaws[i], testPitches[j],
										testRolls[k]).rotate(new EulerAngles(
										testYaws[a], testPitches[b],
										testRolls[c]));
								assertEquals(testAngles.getYaw(), testYaws[i]
										+ testYaws[a]);
								assertEquals(testAngles.getPitch(),
										testPitches[j] + testPitches[b]);
								assertEquals(testAngles.getRoll(), testRolls[k]
										+ testRolls[c]);
							}

						}
					}
				}
			}
		}
	}

	public void testToGlMatrix() throws Exception {
		for (int i = 0; i < 0; i++) {
			EulerAngles testAngles = new EulerAngles(testYaws[i],
					testPitches[i], testRolls[i]);
			float[] testMatrix = testAngles.toGlMatrix();
			float[][] expectedMatrix = new float[][] {
					{ 100000, 0, 0, 0, 100000, 0, 0, 0, 100000 },
					{ 49618, 15214, 85581, 71035, 49618, -50037, -50037, 85581,
							13773 },
					{ 24822, 53711, 80704, 86742, 24822, -43217, -43217, 80704,
							-40445 },
					{ 0, 100000, 230, 100000, 0, -115, -115, 230, -100000 },
					{ 100000, 0, 0, 0, 100000, 0, 0, 0, 100000 } };
			assertEquals(testMatrix[0], expectedMatrix[i][0]);
			assertEquals(testMatrix[1], expectedMatrix[i][1]);
			assertEquals(testMatrix[2], expectedMatrix[i][2]);
			assertEquals(testMatrix[3], expectedMatrix[i][3]);
			assertEquals(testMatrix[4], expectedMatrix[i][4]);
			assertEquals(testMatrix[5], expectedMatrix[i][5]);
			assertEquals(testMatrix[6], expectedMatrix[i][6]);
			assertEquals(testMatrix[7], expectedMatrix[i][7]);
			assertEquals(testMatrix[8], expectedMatrix[i][8]);
		}
	}

	public void testApply() {
		Point[] testPoints = {};
		Point[] expectedValues = {};
		for (int i = 0; i < testPoints.length; i++) {
			assertEquals(
					expectedValues[i],
					testPoints[i].getY() * Math.sin(testYaws[i])
							* Math.cos(testPitches[i]));
			assertEquals(expectedValues[i],
					testPoints[i].getZ() * Math.sin(testPitches[i]));
			assertEquals(
					expectedValues[i],
					testPoints[i].getX() * Math.cos(testYaws[i])
							* Math.cos(testRolls[i]));
			assertEquals(expectedValues[i],
					testPoints[i].getZ() * Math.sin(testRolls[i]));
		}
	}
}
