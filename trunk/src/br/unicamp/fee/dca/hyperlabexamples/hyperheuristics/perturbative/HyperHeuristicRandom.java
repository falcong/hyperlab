package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative;


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
		double bestCost = tspCurrent.getCost();
		T bestInstance = tspCurrent;
		List<BaseHeuristic<T>> heuristicsUsed = new LinkedList<BaseHeuristic<T>>();
		for (int i = 0; i < iterations; i++)
		{
			@SuppressWarnings("unchecked")
			T instanceToTry = (T) bestInstance.copy();
			
			BaseHeuristic<T> h = heuristics[random.nextInt(heuristics.length)];
			instanceToTry = h.run(instanceToTry, numberOfAssignmentsPerHeuristic);
			
			double cost = instanceToTry.getCost();
			
			if (cost < bestCost)
			{
				bestCost = cost;
				bestInstance = instanceToTry;
				heuristicsUsed.add(h);
			}
		}
		printHeuristicsToFile(heuristicsUsed);
		return bestInstance;
	}
}
