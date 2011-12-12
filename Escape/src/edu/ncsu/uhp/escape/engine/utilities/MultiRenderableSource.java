package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class MultiRenderableSource extends RenderSource {
	// private Resources res;
	private Point offsets;
	private RenderSource[] sources;
	private IRenderable[] renderables;

	public MultiRenderableSource(int id, RenderSource[] sources, Point offsets) {
		super(id);
		this.sources = sources;
		this.offsets = offsets;
		renderables = new IRenderable[sources.length];
	}

	@Override
	public MultiRenderable loadData(Context context, GL10 gl) {
		for (int i = 0; i < sources.length; i++) {
			if (renderables[i] == null)
				renderables[i] = sources[i].loadData(context, gl);
		}
		return new MultiRenderable(renderables, offsets);
	}

	public void SetRenderSource(int index, RenderSource source) {
		sources[index] = source;
		renderables[index] = null;
		reload();
	}

	public Point getOffsets() {
		return offsets;
	}
	
	public RenderSource[] getSources(){
		return this.sources;
	}

}
