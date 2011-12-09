package edu.ncsu.uhp.escape.game.utilities;

import android.graphics.Color;
import edu.ncsu.uhp.escape.engine.collision.BoxCollision;
import edu.ncsu.uhp.escape.engine.utilities.ColorSource;
import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class TurretCollision extends BoxCollision{
	Point dimension;
	Point offsets;

	public TurretCollision(Point dimension, Point offsets, int color) {
		this(dimension, offsets, Color.red(color), Color.green(color), Color.blue(color), Color.alpha(color));
	}
	
	public TurretCollision(Point dimension, Point offsets, int r, int g, int b, int a) {
		super(dimension, offsets, new ColorSource(r, g, b, a, dimension, offsets));
		this.dimension = dimension;
		this.offsets = offsets;
	}

	public void changeColor(int color){
		changeColor(Color.red(color), Color.green(color), Color.blue(color), Color.alpha(color));
	}
	
	public void changeColor(int r, int g, int b, int a){
		setSource(new ColorSource(r, g, b, a, dimension, offsets));
	}
	
}
