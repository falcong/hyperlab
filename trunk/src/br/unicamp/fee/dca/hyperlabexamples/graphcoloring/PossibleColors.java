package br.unicamp.fee.dca.hyperlabexamples.graphcoloring;

import java.util.ArrayList;

public class PossibleColors {
	private ArrayList<ArrayList<Integer>> nodesPossibleColors;

	@SuppressWarnings("unchecked")
	public PossibleColors(Graph graph)
	{
		int n = graph.getSize();
		nodesPossibleColors = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < n; i++)
		{
			Node e = graph.getNode(i);
			nodesPossibleColors.add((ArrayList<Integer>) e.possibleColors.clone());
		}
	}
	
	public void removePossibleColorFromNeighbors(Graph graph, int color, Node node)
	{
		for (int i = 0; i < node.adjacents.size(); i++)
		{
			int neighborId = node.adjacents.get(i);
			ArrayList<Integer> possible = nodesPossibleColors.get(neighborId);
			if (possible.contains((Object) color))
			{
				possible.remove((Object) color);
			}
		}
	}
	
	public void addPossibleColorToNeighbors(Graph graph, int color, Node node, int[] colors)
	{
		for (int i = 0; i < node.adjacents.size(); i++)
		{
			int neighborId = node.adjacents.get(i);
			ArrayList<Integer> possible = nodesPossibleColors.get(neighborId);
			boolean possibleColor = true;
			for(int neighbor : graph.getNode(neighborId).adjacents)
			{
				if (colors[neighbor] == color && neighbor != node.id)
				{
					possibleColor = false;
					break;
				}
			}
			if (!possible.contains((Object) color) && possibleColor)
			{
				possible.add((Integer) color);
			}
		}
	}
	
	public ArrayList<Integer> getPossibleColorsForNode(int nodeId)
	{
		return nodesPossibleColors.get(nodeId);
	}
	
	@SuppressWarnings("unchecked")
	public PossibleColors duplicate()
	{
		Graph g = new Graph();
		PossibleColors pc = new PossibleColors(g);
		for (ArrayList<Integer> e:nodesPossibleColors)
		{
			pc.nodesPossibleColors.add((ArrayList<Integer>) e.clone());
		}
		return pc;
	}
}
