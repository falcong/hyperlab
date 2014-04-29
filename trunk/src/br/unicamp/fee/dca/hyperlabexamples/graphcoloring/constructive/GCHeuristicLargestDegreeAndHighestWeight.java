package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;


public class GCHeuristicLargestDegreeAndHighestWeight extends GCConstructiveHeuristicOrderBy
{
	
	public static Comparator<Node> NodeComparator = new Comparator<Node>()
		{
			public int compare(Node node1, Node node2) 
			{
				if (node2.adjacents.size() == node1.adjacents.size())
				{
					return node2.weight - node1.weight;
				}
				return node2.adjacents.size() - node1.adjacents.size();
			}
		};
	
	public GCHeuristicLargestDegreeAndHighestWeight()
	{
		super();
	}
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		return super.run(current, numberOfAssignments);
	}

	public ArrayList<Node> orderNodesByCriteria(GCPartialSolution current) {
		@SuppressWarnings("unchecked")
		ArrayList<Node> orderedNodes = (ArrayList<Node>) current.getInstance().getGraph().getNodes().clone();
		Collections.sort(orderedNodes, NodeComparator);
		return orderedNodes;
	}
}
