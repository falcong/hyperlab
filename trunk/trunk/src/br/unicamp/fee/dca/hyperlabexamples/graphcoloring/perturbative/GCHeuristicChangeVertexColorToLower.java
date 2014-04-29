package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Graph;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.PossibleColors;

/* Changes the color of a random vertex to a lower numbered color*/
public class GCHeuristicChangeVertexColorToLower extends GCPerturbativeHeuristic {

			
	public GCHeuristicChangeVertexColorToLower()
	{
		super();
	}
	
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		Graph graph = current.getInstance().getGraph();
		int[] colors = current.getColors().clone();
		PossibleColors pc = current.getPossibleColors().duplicate();
		
		Random r  = new Random();
		for (int na = 0; na < numberOfAssignments; na++)
		{
			int p = r.nextInt(graph.getSize());
			for (int i = 0; i < graph.getSize(); i++)
			{
				int vertex = (p + i) % graph.getSize();
				if (!pc.getPossibleColorsForNode(vertex).isEmpty() && colors[vertex] != -1)
				{
					int oldColor = colors[vertex];
					int currentColor = oldColor;
					
					for (int color : pc.getPossibleColorsForNode(vertex))
					{
						if (color < currentColor)
						{
							currentColor = color;
						}	
					}
					if (currentColor != oldColor)
					{
						pc.addPossibleColorToNeighbors(graph, oldColor, graph.getNode(vertex), colors);
						pc.removePossibleColorFromNeighbors(graph, currentColor, graph.getNode(vertex));
						
						colors[vertex] = currentColor;
						break;
					}
				}
				
			}
		}
		
		GCPartialSolution next = new GCPartialSolution(current.getInstance(), colors, pc);
		return next;
		
	}
}
