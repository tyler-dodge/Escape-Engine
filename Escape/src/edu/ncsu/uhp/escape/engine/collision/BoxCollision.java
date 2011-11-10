package edu.ncsu.uhp.escape.engine.collision;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.ColorSource;
import edu.ncsu.uhp.escape.engine.utilities.IRenderable;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

/**
 * Collision Detection
 * 
 * @author Tim Mervine
 * 
 */
public class BoxCollision extends MultiPhaseCollision {

	private RenderSource source;
	/**
	 * Constructs a Collision Box from the Points dimension and offsets.
	 * 
	 * @param dimension
	 * @param offsets
	 */
	public BoxCollision(Point dimension, Point offsets) {
		super(new OB_BroadPhase(dimension, offsets), new AABB_NarrowPhase(
				dimension, offsets));
		this.source=new ColorSource(this.hashCode(), 255,0,255, 255, dimension, offsets);
	}
	public IRenderable getRenderable(GL10 gl)
	{
		return source.getData(gl);
	}
}