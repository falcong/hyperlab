package br.unicamp.fee.dca.hyperlabexamples.tsp.constructive;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;

public class TSPHeuristicAcutest extends TSPConstructiveHeuristic
{
	public TSPPartialSolution run(TSPPartialSolution tspCurrent)
	{
		if (tspCurrent.isComplete())
			return tspCurrent;
		return tspCurrent.createNextStep(determineNext(tspCurrent));
	}
	         
	double angleWeight;
	double distanceWeight;
	
	public TSPHeuristicAcutest(double angleWeight, double distanceWeight)
	{
		this.angleWeight = angleWeight;
		this.distanceWeight = distanceWeight;
	}
	
	int determineNext(TSPPartialSolution tspCurrent)
	{
		if (tspCurrent.getCurrentCircuit().length == 0)
		{
			return 0;
		}
		else if (tspCurrent.getCurrentCircuit().length == 1)
		{
			return determineNearestToVertex(tspCurrent, tspCurrent.getCurrentCircuit()[0]);
		}
		else	
		{
			TSPInstance tspInstance = tspCurrent.getInstance();
			int n = tspCurrent.getCurrentCircuit().length;

			int penultimateVertex = tspCurrent.getCurrentCircuit()[n - 2];
			int lastVertex = tspCurrent.getCurrentCircuit()[n - 1];
			if (tspCurrent.getInstance().getX() == null || tspCurrent.getInstance().getY() == null)
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				for (int j = 0; j < tspCurrent.getInstanceSize(); j++)
				{
					if (!tspCurrent.isVertexCovered(j))
					{
						list.add(j);
					}
				}
				Random r = new Random();
				if (list.size() > 0)
				{
					int i = r.nextInt(list.size());
					assert (list.get(i) >= 0);
					return list.get(i);
				}
				else
				{
					return -1;
				}
			}
			else
			{
				double Ax = tspInstance.getX(penultimateVertex);
				double Ay = tspInstance.getY(penultimateVertex);
				double Bx = tspInstance.getX(lastVertex);
				double By = tspInstance.getY(lastVertex);
				double BAangle = Math.atan2(By - Ay, Bx - Ax);
				
				int best = -1;
				double bestFitness = Double.MAX_VALUE;
				for (int i = 0; i < tspInstance.getLength(); i++)
				{
					if (!tspCurrent.isVertexCovered(i))
					{
						double Cx = tspInstance.getX(i);
						double Cy = tspInstance.getY(i);
						double BCangle = Math.atan2(By - Cy, Bx - Cx);
						
						double angle = getShortAngle(BCangle, BAangle);
						double distance = tspInstance.getDistance(i, lastVertex);
						double fitness =  angleWeight * angle + distanceWeight * distance;
						if (fitness < bestFitness)
						{
							bestFitness = fitness;
							best = i;
						}
					}
				}
				assert (best >= 0 );
				return best;
			}
		}
	}
	
	double getShortAngle(double a1, double a2)
	{
	    double angle = (Math.abs(a1 - a2)) % 360.0;

	    if (angle > 180)
	    {
	        angle = 360 - angle;
	    }

	    return angle;
	};
}
