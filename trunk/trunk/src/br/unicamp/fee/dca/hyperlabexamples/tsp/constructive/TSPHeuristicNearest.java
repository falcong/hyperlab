package br.unicamp.fee.dca.hyperlabexamples.tsp.constructive;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;

public class TSPHeuristicNearest extends TSPConstructiveHeuristic
{
	public TSPPartialSolution run(TSPPartialSolution tspCurrent)
	{
		if (tspCurrent.isComplete())
			return tspCurrent;
		return tspCurrent.createNextStep(determineNext(tspCurrent));
	}
	
	int determineNext(TSPPartialSolution tspCurrent)
	{
		if (tspCurrent.getCurrentCircuit().length == 0)
		{
			return 0;
		}
		else
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
	}
}
