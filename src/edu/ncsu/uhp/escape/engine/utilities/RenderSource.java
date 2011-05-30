package edu.ncsu.uhp.escape.engine.utilities;

import javax.microedition.khronos.opengles.GL10;

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

	public int getSourceId() {
		return sourceId;
	}

	public IRenderable getData(GL10 gl) {
		if (sourceData==null)
			sourceData=loadData(gl);
		return sourceData;
	}
	public abstract IRenderable loadData(GL10 gl);
}
