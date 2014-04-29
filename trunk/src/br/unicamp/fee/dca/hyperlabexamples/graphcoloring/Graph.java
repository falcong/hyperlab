package br.unicamp.fee.dca.hyperlabexamples.graphcoloring;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Node> nodes;
	
	public Graph()
	{
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node node)
	{
		this.nodes.add(node);
	}
	
	public Node getNode(int i)
	{
		return nodes.get(i);
	}
	
	public int getSize()
	{
		return nodes.size();
	}
	
	public ArrayList<Node> getNodes()
	{
		return this.nodes;
	}

	public ArrayList<Node> getUncoloredNodes(int[] colors)
	{
		int n = this.getSize();
		@SuppressWarnings("unchecked")
		ArrayList<Node> uncoloredNodes = (ArrayList<Node>) nodes.clone();
		for (int i = 0; i < n; i++)
		{
			if (colors[i] != -1)
			{
				uncoloredNodes = removeNodeById(uncoloredNodes, i);
			}
		}
		return uncoloredNodes;
	}
	
	public ArrayList<Node> removeNodeById(ArrayList<Node> nodes, int id)
	{
		Node toRemove = null;
		@SuppressWarnings("unchecked")
		ArrayList<Node> newGraph = (ArrayList<Node>) nodes.clone();
		for (int i = 0; i < newGraph.size(); i++)
		{
			if (newGraph.get(i).id == id)
			{
				toRemove = newGraph.get(i);
				break;
			}
		}
		if (toRemove != null)
		{
			newGraph.remove(toRemove);
		}
		return newGraph;
	}
	
	
}
