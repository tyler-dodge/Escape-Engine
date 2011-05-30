package edu.ncsu.uhp.escape.engine.map;

/**
 * Logical structure of a 2 dimensional grid system that contains the
 * width and height properties, as well as the Id of each grid location.
 * 
 * @author Tyler Dodge and Bethany Vohlers
 *
 */
public class ResourceGrid {
	private int[][] ids;
	private int width, height;

	public ResourceGrid(int[][] newIds, int width, int height) {
		ids = new int[width][height];
		this.width = width;
		this.height = height;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				ids[x][y] = newIds[x][y];
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getId(int x, int y) {
		return ids[x][y];
	}
}
