package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlab.util.tabusearch.TabuList;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.tabuSearchUtil.ConstructiveMovement;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.tabuSearchUtil.HeuristicChange;

public class HyperHeuristicTabu extends HyperHeuristic
{
	private int iterations;
	private int tabuMemory;
	private int i;

	public HyperHeuristicTabu(int iterations, int tabuMemory)
	{
		this.iterations = iterations;
		this.tabuMemory = tabuMemory;
	}
	
	public int getLastIteration()
	{
		return i;
	}
	

	@Override
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssigmentsPerHeuristic) 
	{
		int listSize = (int) Math.ceil(current.getInstanceSize()/(double)numberOfAssigmentsPerHeuristic);
		ArrayList<BaseHeuristic<T>> heuristicsList = new ArrayList<BaseHeuristic<T>>();
		TabuList<T> tabuList = new TabuList<T>(tabuMemory);
		
		heuristicsList = createRandomList(heuristics, listSize);
		
		for (int iteration = 0; iteration < iterations; iteration++)
		{
			ArrayList<BaseHeuristic<T>> newHeuristicsList = shake(heuristicsList, heuristics, current, numberOfAssigmentsPerHeuristic);
			ArrayList<HeuristicChange> changes = HeuristicChange.generateHeuristicChanges(heuristicsList, newHeuristicsList);
			ConstructiveMovement move = new ConstructiveMovement(changes);
			if (!tabuList.isTabu(move))
			{
				heuristicsList = newHeuristicsList;
				tabuList.add(move, iteration);
			}
			tabuList.iterationPassed();
		}
		
		return getPartialSolution(current, heuristicsList, numberOfAssigmentsPerHeuristic);
	}
	
	private <T> T getPartialSolution(T current, ArrayList<BaseHeuristic<T>> heuristicsList, int numberOfAssigmentsPerHeuristic)
	{
		for (int i = 0; i < heuristicsList.size(); i++)
		{
			current = heuristicsList.get(i).run(current, numberOfAssigmentsPerHeuristic);
		}
		return current;
	}
	
	private <T> ArrayList<BaseHeuristic<T>> createRandomList(BaseHeuristic<T>[] heuristics, int listSize)
	{
		Random r = new Random();
		int n = heuristics.length;
		ArrayList<BaseHeuristic<T>> newList = new ArrayList<BaseHeuristic<T>>();
		for (int i = 0; i < listSize; i++)
		{
			int a = r.nextInt(n);
			newList.add(heuristics[a]);
		}
		return newList;
	}
	
	private <T extends PartialSolution> ArrayList<BaseHeuristic<T>> shake(ArrayList<BaseHeuristic<T>> heuristicsList, BaseHeuristic<T>[] heuristics, T current, int numberOfAssigmentsPerHeuristic)
	{
		Random r = new Random();
		int a  = r.nextInt(2);
		if (a < 1)
		{
			heuristicsList = switchRandomHeuristic(heuristicsList, heuristics, current, numberOfAssigmentsPerHeuristic);
		}
		else
		{
			heuristicsList = switchTwoRandomHeuristics(heuristicsList, current, numberOfAssigmentsPerHeuristic);
		}
		return heuristicsList;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends PartialSolution> ArrayList<BaseHeuristic<T>> switchRandomHeuristic(ArrayList<BaseHeuristic<T>> heuristicsList, BaseHeuristic<T>[] possibleHeuristics, T current, int numberOfAssigmentsPerHeuristic)
	{
				
		ArrayList<BaseHeuristic<T>> newList = (ArrayList<BaseHeuristic<T>>) heuristicsList.clone();
		Random r = new Random();
		
		int b = r.nextInt(newList.size());
		
		ArrayList<BaseHeuristic<T>> bestList = null;
		double min = Double.MAX_VALUE; 
		
		for (int i = 0; i < possibleHeuristics.length; i++)
		{
			newList.set(b, possibleHeuristics[i]);
			T p = getPartialSolution(current, heuristicsList, numberOfAssigmentsPerHeuristic);
			if (p.getCost() < min)
			{
				min = p.getCost();
				bestList = (ArrayList<BaseHeuristic<T>>) newList.clone();
			}
		}
		
		
		return bestList;
	}
	
	@SuppressWarnings("unchecked")
	private <T extends PartialSolution> ArrayList<BaseHeuristic<T>> switchTwoRandomHeuristics(ArrayList<BaseHeuristic<T>> heuristicsList, T current, int numberOfAssigmentsPerHeuristic)
	{
				
		ArrayList<BaseHeuristic<T>> newList;
		Random r = new Random();
		
		ArrayList<BaseHeuristic<T>> bestList = null;
		double min = Double.MAX_VALUE; 
		
		int b = r.nextInt(heuristicsList.size());
		
		
		for (int i = 0; i < heuristicsList.size(); i++)
		{
			newList = (ArrayList<BaseHeuristic<T>>) heuristicsList.clone();
			newList.set(i, heuristicsList.get(b));
			newList.set(b, heuristicsList.get(i));
			T p = getPartialSolution(current, heuristicsList, numberOfAssigmentsPerHeuristic);
			if (p.getCost() < min)
			{
				min = p.getCost();
				bestList = (ArrayList<BaseHeuristic<T>>) newList.clone();
			}
		}
		
		return bestList;
	}
}

	