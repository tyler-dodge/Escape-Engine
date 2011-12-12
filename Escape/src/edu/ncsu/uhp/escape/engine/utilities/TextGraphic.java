package edu.ncsu.uhp.escape.engine.utilities;

import edu.ncsu.uhp.escape.engine.utilities.math.Point;

public class TextGraphic extends Graphic {
	public TextGraphic(Point position, String text, float height) {
		this(position, text, height,255,0,0,0);

	}
	public TextGraphic(Point position, String text, float height, int r, int g,
			int b, int a) {
		super(position, ZAxisRotation.getIdentity(), new TextRenderSource(0,
				text, height, new Point(0, 0, 0), r, g, b, a));

	}
}
