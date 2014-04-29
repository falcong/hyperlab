package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.PossibleColors;

/*
 * Also known as SDO, or Saturation Degree Ordering
 */
public class GCHeuristicLeastPossibleColors extends GCConstructiveHeuristicOrderBy 
{
	class ColorComparator implements Comparator<Node>
	{
		private PossibleColors pc;
		
		public ColorComparator(PossibleColors newPc)
		{
			pc = newPc;
		}
		
		public int compare(Node node1, Node node2) 
		{
			return pc.getPossibleColorsForNode(node1.id).size() - pc.getPossibleColorsForNode(node2.id).size();
		}
	}

	
	public GCHeuristicLeastPossibleColors()
	{
		super();
	}
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		return super.run(current, numberOfAssignments);
	}

	public ArrayList<Node> orderNodesByCriteria(GCPartialSolution current) {
		PossibleColors pc = current.getPossibleColors();
		ColorComparator cc = new ColorComparator(pc);
		@SuppressWarnings("unchecked")
		ArrayList<Node> orderedNodes = (ArrayList<Node>) current.getInstance().getGraph().getNodes().clone();
		Collections.sort(orderedNodes, cc);
		
		return orderedNodes;
	}
}
