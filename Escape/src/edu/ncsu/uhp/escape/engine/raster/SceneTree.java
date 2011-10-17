package edu.ncsu.uhp.escape.engine.raster;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.microedition.khronos.opengles.GL10;

import edu.ncsu.uhp.escape.engine.utilities.IRenderable;

public class SceneTree {
	private class SceneNode {
		public SceneNode(IRenderParameters value)
		{
			this();
			this.value=value;
		}
		public SceneNode()
		{
			children=new Hashtable<Integer, SceneTree.SceneNode>();
		}
		public IRenderParameters value;
		public HashSet<IRenderable> renderables;
		public Hashtable<Integer, SceneNode> children;
	}
	private SceneNode root;
	public SceneTree()
	{
		root=new SceneNode();
	}
	public void traverse(GL10 gl)
	{
		traverse(root,gl);
	}
	public void addRenderable(IRenderable renderable)
	{
		IRenderParameters params=renderable.getRenderParameters();
		SceneNode finalNode=addRenderable(renderable,params);
		finalNode.renderables.add(renderable);
	}
	private SceneNode addRenderable(IRenderable renderable, IRenderParameters parameters)
	{
		SceneNode currentNode=root;
		if (parameters.hasParent())
			currentNode=addRenderable(renderable,parameters.getParent());
		SceneNode nextNode=currentNode.children.get(parameters.hashCode());
		if (nextNode==null)
		{
			nextNode=new SceneNode(parameters);
			currentNode.children.put(new Integer(parameters.hashCode()), 
									 nextNode);
		}
		return nextNode;
	}
	private void traverse(SceneNode node, GL10 gl)
	{
		node.value.setUp();
		for (IRenderable renderable:node.renderables)
		{
			renderable.drawGL10(gl);
		}
		for (Entry<Integer, SceneNode> entry: node.children.entrySet())
		{
			traverse(entry.getValue(),gl);
		}
		node.value.cleanUp();
	}
}
