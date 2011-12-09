package edu.ncsu.uhp.escape.engine.collision;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Color;
import android.content.Context;
import edu.ncsu.uhp.escape.engine.utilities.ColorBoxSource;
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
		this.source=new ColorBoxSource(System.identityHashCode(this), 255,0,255, 255, dimension, offsets);
	}
	
	public BoxCollision(Point dimension, Point offsets, int color){
		super(new OB_BroadPhase(dimension, offsets), new AABB_NarrowPhase(
				dimension, offsets));
		this.source=new ColorBoxSource(System.identityHashCode(this), Color.red(color), Color.green(color), Color.blue(color), Color.alpha(color), dimension, offsets);
	}
	
	public BoxCollision(Point dimension, Point offsets, RenderSource source){
		super(new OB_BroadPhase(dimension, offsets), new AABB_NarrowPhase(
				dimension, offsets));
		this.source= source;
	}
	
	public IRenderable getRenderable(Context context,GL10 gl)
	{
		return source.getData(context,gl);
	}
	
	public RenderSource getSource(){
		return source;
	}
	
	public void setSource(RenderSource source){
		this.source = source;
	}
}