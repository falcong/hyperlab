package br.unicamp.fee.dca.hyperlabexamples.tsp.constructive;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;

public abstract class TSPConstructiveHeuristic extends TSPHeuristic
{
	public TSPConstructiveHeuristic()
	{
		super(MovementAcceptance.FIRST_IMPROVEMENT);
	}

	protected int determineNearestToVertex(TSPPartialSolution tspCurrent, int baseVertex)
	{
		TSPInstance tspInstance = tspCurrent.getInstance();
		int lastVertex = tspCurrent.getActualCurrentIndex();
		
		int best = -1;
		double bestDist = Double.MAX_VALUE;
		for (int i = 0; i < tspInstance.getLength(); i++)
		{
			if (!tspCurrent.isVertexCovered(i))
			{
				double distance = tspInstance.getDistance(i, lastVertex);
				if (distance < bestDist)
				{
					bestDist = distance;
					best = i;
				}
			}
		}
		assert (best >= 0);
		return best;
	}
	
	public boolean isConstructive()
	{
		return true;
	}
}
