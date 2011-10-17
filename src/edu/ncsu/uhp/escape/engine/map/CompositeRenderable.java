package edu.ncsu.uhp.escape.engine.map;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import edu.ncsu.uhp.escape.engine.raster.IRenderParameters;
import edu.ncsu.uhp.escape.engine.utilities.IRenderable;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Grid renderable object that holds the linkedlist of renderable tiles and their
 * dimensions and offsets.
 * 
 * @author Tyler Dodge and Bethany Vohlers
 *
 */
public class CompositeRenderable implements IRenderable {
	private LinkedList<IRenderable> tiles;
	private Point dimensions;
	private Point offsets;
	private float offsetX, offsetY, offsetZ;

	public CompositeRenderable(Point dimensions, Point offsets,
			LinkedList<IRenderable> tiles) {
		this.tiles = tiles;
		setOffsets(offsets);
		this.dimensions = dimensions;
	}

	public Point getDimensions() {
		return dimensions;
	}

	public Point getOffsets() {
		return offsets;
	}

	public void setOffsets(Point newOffsets) {
		this.offsets = newOffsets;
		this.offsetX = newOffsets.getX();
		this.offsetY = newOffsets.getY();
		this.offsetZ = newOffsets.getZ();
	}

	public void drawGL10(GL10 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(offsetX, offsetY, offsetZ);
		for (IRenderable tile : tiles) {
			tile.drawGL10(gl);
		}
		gl.glPopMatrix();
	}

	public void drawGL11(GL11 gl) {
		gl.glPushMatrix();
		gl.glTranslatef(offsetX, offsetY, offsetZ);
		for (IRenderable tile : tiles) {
			tile.drawGL11(gl);
		}
		gl.glPopMatrix();
	}

	public IRenderParameters getRenderParameters() {
		// TODO Auto-generated method stub
		return null;
	}
}
