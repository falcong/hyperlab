package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative;

import java.util.LinkedList;
import java.util.List;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlab.util.choicefunction.ChoiceFunction;

public class HyperHeuristicChoiceFunction extends HyperHeuristic
{

	private int iterations;
	private int i;
	private double weightHeuristic;
	private double weightPairOfHeuristics;
	private double weightTimeSinceHeuristic;

	public HyperHeuristicChoiceFunction(int iterations, double weightHeuristic, double weightPairOfHeuristics, double weightTimeSinceHeuristic)
	{
		this.iterations = iterations;
		this.weightHeuristic = weightHeuristic;
		this.weightPairOfHeuristics = weightPairOfHeuristics;
		this.weightTimeSinceHeuristic = weightTimeSinceHeuristic;
	}
	
	public int getLastIteration()
	{
		return i;
	}
	
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssignmentsPerHeuristic)
	{ 
		List<BaseHeuristic<T>> heuristicsUsed = new LinkedList<BaseHeuristic<T>>();
		ChoiceFunction cf = new ChoiceFunction(weightHeuristic, weightPairOfHeuristics, weightTimeSinceHeuristic, heuristics.length);
		T nextSol = current;
		T bestSol = current;
		double bestCost = current.getCost();
		//long time = System.nanoTime();
		for (i = 0; i < iterations; i++)
		{
			int nextHeur = cf.chooseHeuristic();
			double previousCost = current.getCost();
			//System.out.println("previous cost: " + previousCost);
			nextSol = heuristics[nextHeur].run(nextSol, numberOfAssignmentsPerHeuristic);
			double nextCost = nextSol.getCost();
			//System.out.println("next cost: " + nextCost);
			current = nextSol;
			if(nextCost < bestCost)
			{
				bestCost = nextCost;
				bestSol = nextSol;
			}
			
			cf.updateHistory(nextHeur, previousCost - nextCost);
			heuristicsUsed.add(heuristics[nextHeur]);
			//long newTime = System.nanoTime();
			//System.out.println("time: " + (newTime - time));
			//time = newTime;
		}
		//System.out.println("next cost: " + bestCost);

		printHeuristicsToFile(heuristicsUsed);
		return bestSol;
	}
}
