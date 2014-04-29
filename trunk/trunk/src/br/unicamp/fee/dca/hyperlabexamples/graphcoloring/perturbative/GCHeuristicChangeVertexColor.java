package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Graph;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.PossibleColors;

/* Changes the color of a random vertex to a random color*/
public class GCHeuristicChangeVertexColor extends GCPerturbativeHeuristic {

			
	public GCHeuristicChangeVertexColor()
	{
		super();
	}
	
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		Graph graph = current.getInstance().getGraph();
		int[] colors = current.getColors().clone();
		PossibleColors pc = current.getPossibleColors().duplicate();
		
		for (int na = 0; na < numberOfAssignments; na++)
		{
			Random r  = new Random();
			for (int i = 0; i < graph.getSize(); i++)
			{
				int p = r.nextInt(graph.getSize());
				if (!pc.getPossibleColorsForNode(p).isEmpty() && colors[p] != -1)
				{
					int colorIndex = r.nextInt(pc.getPossibleColorsForNode(p).size());
					int color = pc.getPossibleColorsForNode(p).get(colorIndex);
					int oldColor = colors[p];
			
					pc.removePossibleColorFromNeighbors(graph, color, graph.getNode(p));
					pc.addPossibleColorToNeighbors(graph, oldColor, graph.getNode(p), colors);
					
					colors[p] = color;
					break;
				}
				
			}
		}
		
		GCPartialSolution next = new GCPartialSolution(current.getInstance(), colors, pc);
		return next;
		
	}
}
