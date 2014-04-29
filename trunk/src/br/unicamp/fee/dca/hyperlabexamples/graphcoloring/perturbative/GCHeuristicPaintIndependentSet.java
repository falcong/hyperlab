package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Graph;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.PossibleColors;

/* Finds a maximal independent set and paints all its vertex with the same color*/
public class GCHeuristicPaintIndependentSet extends GCPerturbativeHeuristic {

			
	public GCHeuristicPaintIndependentSet()
	{
		super();
	}
	
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		Graph graph = current.getInstance().getGraph();
		PossibleColors pc = current.getPossibleColors();
		int[] colors = current.getColors().clone();
		
		
		
		Random r = new Random();
		for (int na = 0; na < numberOfAssignments; na++)
		{
			
			@SuppressWarnings("unchecked")
			ArrayList<Node> nodes = (ArrayList<Node>) graph.getNodes().clone();

			ArrayList<Node> remainingNodes = new ArrayList<Node>();
			
			while (!nodes.isEmpty())
			{
				int p = r.nextInt(nodes.size());
				Node selectedNode = nodes.get(p);
				remainingNodes.add(selectedNode);
				removeNeighborsFromArray(nodes, selectedNode);
				nodes.remove(selectedNode);
			}
			
			for (int i = 0; i < graph.getSize(); i++)
			{
				boolean possibleColor = true;
				for (Node node : remainingNodes)
				{
					if (!pc.getPossibleColorsForNode(node.id).contains(i))
					{
						possibleColor = false;
						break;
					}
				}
				if (possibleColor)
				{
					for (Node node : remainingNodes)
					{
						pc.addPossibleColorToNeighbors(graph, colors[node.id], node, colors);
						pc.removePossibleColorFromNeighbors(graph, i, node);
						colors[node.id] = i;	
					}
				}
			}
		}
		
		GCPartialSolution next = new GCPartialSolution(current.getInstance(), colors, pc);
		return next;
		
	}
	
	private void removeNeighborsFromArray(ArrayList<Node> array, Node node)
	{
		for (int i : node.adjacents)
		{
			removeNodeFromArray(array, i);
		}
	}
	
	private void removeNodeFromArray(ArrayList<Node> array, int nodeIndex)
	{
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).id == nodeIndex)
			{
				array.remove(i);
				break;
			}
		}
		
	}
}
