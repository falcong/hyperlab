package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.mixed;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlab.util.choicefunction.ChoiceFunction;

public class HyperHeuristicMixedRandomAndCF extends HyperHeuristic
{
	private int iterations;
	private Random random;
	private double weightHeuristic;
	private double weightPairOfHeuristics;
	private double weightTimeSinceHeuristic;
	
	
	public HyperHeuristicMixedRandomAndCF(int iterations, double weightHeuristic, double weightPairOfHeuristics, double weightTimeSinceHeuristic)
	{
		this.iterations = iterations;
		this.random = new Random();
		this.weightHeuristic = weightHeuristic;
		this.weightPairOfHeuristics = weightPairOfHeuristics;
		this.weightTimeSinceHeuristic = weightTimeSinceHeuristic;
	}
	
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssignmentsPerHeuristic)
	{
		double bestCost = Double.MAX_VALUE;
		T bestInstance = null;
		List<BaseHeuristic<T>> bestHeuristicsUsed = null;
		
		ArrayList<BaseHeuristic<T>> constructive = new ArrayList<BaseHeuristic<T>>();
		ArrayList<BaseHeuristic<T>> perturbative = new ArrayList<BaseHeuristic<T>>();
		
		for (int i = 0; i < heuristics.length; i++)
		{
			if (heuristics[i].isConstructive())
			{
				constructive.add(heuristics[i]);
			}
			else
			{
				perturbative.add(heuristics[i]);
			}
		}
		
		@SuppressWarnings("unchecked")
		T instanceToTry = (T) current.copy();
		List<BaseHeuristic<T>> heuristicsUsed = new LinkedList<BaseHeuristic<T>>();
	
		
		
		ChoiceFunction cf = new ChoiceFunction(weightHeuristic, weightPairOfHeuristics, weightTimeSinceHeuristic, perturbative.size());
	    bestCost = Double.MAX_VALUE;
	    
	    //int g = 0;
	    
		for (int i = 0; i < iterations; i++)
		{

		    
			//long time = System.nanoTime();

	    	
		    while (!instanceToTry.isComplete())
			{
				BaseHeuristic<T> h = constructive.get(random.nextInt(constructive.size()));
				instanceToTry = h.run(instanceToTry, numberOfAssignmentsPerHeuristic);
				heuristicsUsed.add(h);
				
				//((TSPPartialSolution)instanceToTry).plot("c:\\test\\TSPinstance" + g + "solvingConstruct");

			    int nextHeur = cf.chooseHeuristic();
				double previousCost = instanceToTry.getCost();
				
				//System.out.println("previous cost: " + previousCost);
				
				instanceToTry = perturbative.get(nextHeur).run(instanceToTry, numberOfAssignmentsPerHeuristic);
				double nextCost = instanceToTry.getCost();
				
				//System.out.println("next cost: " + nextCost);

				//((TSPPartialSolution)instanceToTry).plot("c:\\test\\TSPinstance" + g + "solvingPerturb");
				
				cf.updateHistory(nextHeur, previousCost - nextCost);
				heuristicsUsed.add(perturbative.get(nextHeur));
				//g++;
				
			}
		    
		    bestCost = instanceToTry.getCost();
			bestInstance = instanceToTry;
			bestHeuristicsUsed = heuristicsUsed;
		    
		    //g++;
		    
		    int nextHeur = cf.chooseHeuristic();
			double previousCost = instanceToTry.getCost();
			
			//System.out.println("previous cost: " + previousCost);
			
			instanceToTry = perturbative.get(nextHeur).run(instanceToTry, numberOfAssignmentsPerHeuristic);
			double nextCost = instanceToTry.getCost();
			
			//System.out.println("next cost: " + nextCost);

			//((TSPPartialSolution)instanceToTry).plot("c:\\test\\TSPinstance" + g + "solvingPerturb Phase 2");
			
			cf.updateHistory(nextHeur, previousCost - nextCost);
			heuristicsUsed.add(perturbative.get(nextHeur));
		    
		    
		    if(nextCost < bestCost)
			{
				bestCost = nextCost;
				bestInstance = instanceToTry;
				bestHeuristicsUsed = heuristicsUsed;
			}
			
			//long newTime = System.nanoTime();
			//System.out.println("time: " + (newTime - time));
			//time = newTime;
			
		}
		printHeuristicsToFile(bestHeuristicsUsed);
		return bestInstance;
	}
}
