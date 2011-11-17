package edu.ncsu.uhp.escape.engine.map;


import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import edu.ncsu.uhp.escape.engine.actionresponse.BaseActionResponse;
import edu.ncsu.uhp.escape.engine.actionresponse.IActionResponse;
import edu.ncsu.uhp.escape.engine.collision.ICollidable;
import edu.ncsu.uhp.escape.engine.utilities.IRenderable;
import edu.ncsu.uhp.escape.engine.utilities.IRotation;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Standard 2D Map that holds tiles.
 * 
 * @author Tyler Dodge and Bethany Vohlers
 *
 */
public class TileMap extends Map<TileMap> {

	private Point position;
	private IRotation rotation;
	private Tile[][] tiles;
	private IRenderable renderable;
	private Point dimensions;
	private int width;
	private int height;

	public TileMap(Context context, Point dimensions, Tile[][] tiles) {
		super(dimensions);
		this.tiles = tiles;
		width = tiles.length;
		height = tiles[0].length;
		this.dimensions = dimensions;
	}

	@Override
	public TileMap asDataType() {
		return this;
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point newPosition) {
		this.position = newPosition;
	}

	public IRotation getRotation() {
		return rotation;
	}

	@Override
	public boolean doesCollide(ICollidable checkCollision) {

		return false;
	}

	@Override
	public IRenderable getRenderable(Context context, GL10 gl) {
		if (renderable == null) {
			int[][] resourceIds = new int[width][height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					resourceIds[x][y] = tiles[x][y].getResourceId();
				}
			}
			ResourceGrid grid = new ResourceGrid(resourceIds, width, height);
			renderable = new GridImageSource(context, 0, grid, new Point(0, 0,
					0), dimensions, 128).getData(context,gl);
		}
		return renderable;
	}

	@Override
	public IActionResponse<Map<TileMap>> createDefaultResponse() {
		return new BaseActionResponse<Map<TileMap>>();
	}
}
