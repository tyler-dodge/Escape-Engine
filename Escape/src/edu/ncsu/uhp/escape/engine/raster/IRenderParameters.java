package edu.ncsu.uhp.escape.engine.raster;

public interface IRenderParameters {
	public void setUp();
	public void cleanUp();
	public IRenderParameters getParent();
	public boolean hasParent();
}
