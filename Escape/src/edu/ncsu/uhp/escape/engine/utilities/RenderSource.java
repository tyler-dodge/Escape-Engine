package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

/**
 * Holds the RenderSource Id and sourceData
 * 
 * @author Tyler Dodge
 * 
 */
public abstract class RenderSource {
	private int sourceId;
	private IRenderable sourceData;

	public RenderSource(int id) {
		this.sourceId = id;
	}

	public RenderSource() {
		this.sourceId = System.identityHashCode(this);
	}

	public int getSourceId() {
		return sourceId;
	}

	public IRenderable getData(Context context, GL10 gl) {
		if (sourceData == null)
			sourceData = loadData(context, gl);
		return sourceData;
	}

	public void reload() {
		sourceData = null;
	}

	public abstract IRenderable loadData(Context context, GL10 gl);
}
