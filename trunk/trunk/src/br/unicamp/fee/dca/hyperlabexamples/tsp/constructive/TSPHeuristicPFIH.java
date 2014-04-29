package br.unicamp.fee.dca.hyperlabexamples.tsp.constructive;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;

public class TSPHeuristicPFIH extends TSPConstructiveHeuristic
{
	public TSPPartialSolution run(TSPPartialSolution tspCurrent)
	{
		if (tspCurrent.isComplete())
			return tspCurrent;
		if (tspCurrent.getCurrentCircuit().length == 0)
		{
			return tspCurrent.createNextStep(0);
		}
		else if (tspCurrent.getCurrentCircuit().length == 1)
		{
			int a = determineNearestToVertex(tspCurrent, tspCurrent.getCurrentCircuit()[0]);
			return tspCurrent.createNextStep(a);
		}
		else			
		{
			return pfih(tspCurrent);
		}
	}
	
	TSPPartialSolution pfih(TSPPartialSolution tspCurrent)
	{
//		System.out.println("===================");
//		System.out.println("Starting with ");
//		tspCurrent.printSolution();
		
		TSPInstance tspInstance = tspCurrent.getInstance();
		int[] circuit = tspCurrent.getCurrentCircuit();
		assert(circuit.length >= 2);
		
		double bestDelta = Double.MAX_VALUE;
		int bestChangeVertex = -1;
		int bestChangeEdge = -1;
		
		// For each edge AB
		for (int i = 0; i < circuit.length; i++)
		{
			if (i == 1 && circuit.length == 2) continue;
			
			int Ai = circuit[i];
			int Bi = circuit[(i + 1) % circuit.length];

			if (Ai == -1 || Bi == -1)
			{
				System.out.println("oi");
			}
			
			double ABcost = tspCurrent.getInstance().getDistance(Ai, Bi);
			
			// For each vertex P not in the circuit
			for (int j = 0; j < tspInstance.getLength(); j++)
			{
				if (!tspCurrent.isVertexCovered(j))
				{
	
					double APcost = tspCurrent.getInstance().getDistance(Ai, j);
					double BPcost = tspCurrent.getInstance().getDistance(j, Bi);
					
					// Consider inserting that vertex
					double circuitCostDelta = APcost + BPcost - ABcost;
					
//					System.out.println("Considering splitting " + Ai + "->" + Bi + " with " + j);
//					System.out.println("delta=" + circuitCostDelta
//							+ " APcost=" + APcost
//							+ " BPcost=" + BPcost
//							+ " ABcost=" + ABcost);
					if (circuitCostDelta < bestDelta)
					{
//						System.out.println("new record");
						bestDelta = circuitCostDelta;
						bestChangeVertex = j;
						bestChangeEdge = i;
					}
				}
			}
		}

		assert(bestChangeVertex >= 0);
		assert(bestChangeEdge >= 0);

		int positionToInsertAt = bestChangeEdge + 1;
		
//		System.out.println("settled for inserting " + bestChangeVertex + " at " + positionToInsertAt + " with delta " + bestDelta);
		int[] newIndexes = new int[circuit.length + 1];
		System.arraycopy(circuit, 0,
				newIndexes, 0,
				positionToInsertAt);
		newIndexes[positionToInsertAt] = bestChangeVertex;
		System.arraycopy(circuit, positionToInsertAt,
				newIndexes, positionToInsertAt + 1,
				circuit.length - positionToInsertAt);
		
		TSPPartialSolution tspNext = new TSPPartialSolution(tspInstance, newIndexes);
//		System.out.println("Ending with ");
//		tspNext.printSolution();
		return tspNext;
	}
	
	double distanceBetweenPoints(double Ax, double Ay, double Bx, double By)
	{
		double dx = Ax - Bx;
		double dy = Ay - By;
		return Math.sqrt(dx * dx + dy * dy);
	}
}
