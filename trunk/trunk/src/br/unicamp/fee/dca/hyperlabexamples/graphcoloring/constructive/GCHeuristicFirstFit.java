package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;

public class GCHeuristicFirstFit extends GCConstructiveHeuristicOrderBy
{
	
	public GCHeuristicFirstFit()
	{
		super();
	}
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		return super.run(current, numberOfAssignments);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Node> orderNodesByCriteria(GCPartialSolution current) {
		return (ArrayList<Node>) current.getInstance().getGraph().getNodes().clone();
	}
}
