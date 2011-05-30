package edu.ncsu.uhp.escape;


import edu.ncsu.uhp.escape.engine.Engine;
import edu.ncsu.uhp.escape.engine.EngineSurface;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Logical container for the engine surface and engine.
 * 
 * @author Tyler Dodge
 *
 */
public class EscapeSurfaceView extends GLSurfaceView {
	private EngineSurface renderer;
	public EscapeSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		renderer=new EngineSurface();
		setRenderer(renderer);
	}
	public void setEngine(Engine engine)
	{
		renderer.setEngine(engine);
	}

}
