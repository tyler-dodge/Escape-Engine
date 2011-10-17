package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import edu.ncsu.uhp.escape.engine.raster.IRenderParameters;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Contains render data for use by the engine.
 * 
 * @author Tyler Dodge
 * 
 */
public interface IRenderable {

	/**
	 * Gets the dimensions of renderable. Used to determine whether or not to
	 * render object
	 * 
	 * @return the dimensions
	 */
	public Point getDimensions();

	/**
	 * Gets the offsets of renderable. Used to offset the Renderable from a
	 * Point associated with it, like an Actor position.
	 * 
	 * @return the offsets
	 */
	public Point getOffsets();

	public void setOffsets(Point newOffsets);

	/**
	 * Renders this renderable in opengl using gl10
	 * 
	 * @param gl
	 */
	public void drawGL10(GL10 gl);

	/**
	 * Renders this renderable in opengl using gl11
	 * 
	 * @param gl
	 */
	public void drawGL11(GL11 gl);
	
	public IRenderParameters getRenderParameters();
}
