package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Graph;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.PossibleColors;

public abstract class GCConstructiveHeuristicOrderBy extends GCConstructiveHeuristic {

			
	public GCConstructiveHeuristicOrderBy()
	{
		super();
	}
	
	
	public abstract ArrayList<Node> orderNodesByCriteria(GCPartialSolution current);
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		Graph graph = current.getInstance().getGraph();
		int[] colors = new int[graph.getSize()];
		for (int i = 0; i < graph.getSize(); i++)
		{
			colors[i] = current.getColors()[i];
		}
		
		ArrayList<Node> uncoloredGraph = orderNodesByCriteria(current);
		PossibleColors pc = current.getPossibleColors().duplicate();
		uncoloredGraph = graph.getUncoloredNodes(colors);
		
		int i = 0;
		while (uncoloredGraph.size() > 0 && i < numberOfAssignments)
		{
			Node nodeToColor = uncoloredGraph.get(0);
			ArrayList<Integer> possibColorsForNode = pc.getPossibleColorsForNode(nodeToColor.id);
			int color = possibColorsForNode.get(0);
			colors[nodeToColor.id] = color;
			
			pc.removePossibleColorFromNeighbors(graph, color, nodeToColor);
			
			uncoloredGraph.remove(nodeToColor);
			i++;
		}
		
		GCPartialSolution next = new GCPartialSolution(current.getInstance(), colors, pc);
			
		return next;
	}
}
