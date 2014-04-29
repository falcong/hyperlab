package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive;


import java.util.LinkedList;
import java.util.List;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class HyperHeuristicGreedy extends HyperHeuristic
{
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T currentAnswer, int numberOfAssignmentsPerHeuristic)
	{
		List<BaseHeuristic<T>> heuristicsUsed = new LinkedList<BaseHeuristic<T>>();
		
		while (!currentAnswer.isComplete())
		{
			T bestAnswer = null;
			BaseHeuristic<T> bestHeuristic = null;
			double bestCost = Double.MAX_VALUE; 
			for (BaseHeuristic<T> heuristic : heuristics)
			{
				T candidate = heuristic.run(currentAnswer, numberOfAssignmentsPerHeuristic);
				double candidateCost = candidate.getCost();
				if (candidate.getCost() < bestCost)
				{
					bestCost = candidateCost;
					bestAnswer = candidate;
					bestHeuristic = heuristic;
				}
			}
			currentAnswer = bestAnswer;
			heuristicsUsed.add(bestHeuristic);
		}

		printHeuristicsToFile(heuristicsUsed);
		return currentAnswer;
	}
}
