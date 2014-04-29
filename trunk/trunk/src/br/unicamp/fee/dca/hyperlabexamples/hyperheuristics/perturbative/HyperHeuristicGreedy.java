package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative;

import java.util.LinkedList;
import java.util.List;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class HyperHeuristicGreedy extends HyperHeuristic
{
	private int iterations;
	private int i;

	public HyperHeuristicGreedy(int iterations)
	{
		this.iterations = iterations;
	}
	
	public int getLastIteration()
	{
		return i;
	}
	

	@Override
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssignmentsPerHeuristic) {
		Run<T> run = new Run<T>(heuristics, current, numberOfAssignmentsPerHeuristic);
		return run.run();
	}

	private class Run<T extends PartialSolution>
	{
		private T current;
		private List<BaseHeuristic<T>> heuristicsUsed;
		private BaseHeuristic<T>[] heuristics;
		double previousCost;
		int lastChange;
		int numberOfAssignmentsPerHeuristic;
		
		public Run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssignmentsPerHeuristic)
		{
			this.heuristics = heuristics;
			this.current = current;
			heuristicsUsed = new LinkedList<BaseHeuristic<T>>();
			previousCost = Double.MAX_VALUE;
			lastChange = 0;
			this.numberOfAssignmentsPerHeuristic = numberOfAssignmentsPerHeuristic;
		}

		public T run()
		{
			for (i = 0; i < iterations; i++)
			{
				boolean shouldContinue = runIteration();
				if (!shouldContinue)
				{
					break;
				}
			}

			printHeuristicsToFile(heuristicsUsed);
			return current;
		}

		T bestAnswer;
		BaseHeuristic<T> bestHeuristic;
		double bestCost; 
		
		private boolean runIteration()
		{
			bestAnswer = null;
			bestHeuristic = null;
			bestCost = Double.MAX_VALUE;
			
			for (BaseHeuristic<T> heuristic : heuristics)
			{
				tryHeuristic(heuristic);
			}
			
			if (previousCost == bestCost)
			{
				lastChange++;
			}
			else
			{
				lastChange = 0;
			}
			current = bestAnswer;
			heuristicsUsed.add(bestHeuristic);
			previousCost = bestCost;
			return (lastChange < 3);
		}

		private void tryHeuristic(BaseHeuristic<T> heuristic)
		{
			T candidate = heuristic.run(current, numberOfAssignmentsPerHeuristic);
			double candidateCost = candidate.getCost();
			if (candidate.getCost() < bestCost)
			{
				bestCost = candidateCost;
				bestAnswer = candidate;
				bestHeuristic = heuristic;
			}
		}
	}
}
