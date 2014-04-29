package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.mixed;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class HyperHeuristicRandom extends HyperHeuristic
{
	private int iterations;
	private Random random;
	
	public HyperHeuristicRandom(int iterations)
	{
		this.iterations = iterations;
		this.random = new Random();
	}
	
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T tspCurrent, int numberOfAssignmentsPerHeuristic)
	{
		double bestCost = Double.MAX_VALUE;
		T bestInstance = null;
		List<BaseHeuristic<T>> bestHeuristicsUsed = null;
		for (int i = 0; i < iterations; i++)
		{
			@SuppressWarnings("unchecked")
			T instanceToTry = (T) tspCurrent.copy();
			List<BaseHeuristic<T>> heuristicsUsed = new LinkedList<BaseHeuristic<T>>();
			
			while (!instanceToTry.isComplete())
			{
				BaseHeuristic<T> h = heuristics[random.nextInt(heuristics.length)];
				instanceToTry = h.run(instanceToTry, numberOfAssignmentsPerHeuristic);
				heuristicsUsed.add(h);
			}
			
			double cost = instanceToTry.getCost();
			
			if (cost < bestCost)
			{
				bestCost = cost;
				bestInstance = instanceToTry;
				bestHeuristicsUsed = heuristicsUsed;
			}
		}
		printHeuristicsToFile(bestHeuristicsUsed);
		return bestInstance;
	}
}
