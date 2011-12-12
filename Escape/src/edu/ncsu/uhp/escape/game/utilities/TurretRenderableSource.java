package edu.ncsu.uhp.escape.game.utilities;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import edu.ncsu.uhp.escape.engine.utilities.IRenderable;
import edu.ncsu.uhp.escape.engine.utilities.MultiRenderable;
import edu.ncsu.uhp.escape.engine.utilities.MultiRenderableSource;
import edu.ncsu.uhp.escape.engine.utilities.RenderSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class TurretRenderableSource extends MultiRenderableSource {
	// private Resources res;
	private Point offsets;
	private TurretCollision collisionSource;

	public TurretRenderableSource(int id, RenderSource[] sources, Point offsets, TurretCollision collisionSource) {
		super(id, sources, offsets);
		this.offsets = offsets;
		this.collisionSource = collisionSource;
	}

	@Override
	public MultiRenderable loadData(Context context, GL10 gl) {
		IRenderable[] renderables = new IRenderable[getSources().length + 1];
		for (int i = 0; i < getSources().length ; i++) {
			renderables[i] = getSources()[i].loadData(context, gl);
		}
		renderables[getSources().length] = collisionSource.getSource().loadData(context, gl);
		return new MultiRenderable(renderables, offsets);
	}

	public Point getOffsets() {
		return offsets;
	}

}
