package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.constructive.TSPHeuristicPFIH;


public class TSPHeuristicPerturbPFIH extends TSPPerturbativeHeuristic
{

	int numberOfCities = 3;
	
	public TSPHeuristicPerturbPFIH(MovementAcceptance moveAcceptance)
	{
		super(moveAcceptance);
		this.numberOfCities = 3;
	}
		
	
	public TSPHeuristicPerturbPFIH(MovementAcceptance moveAcceptance, int numberOfCities)
	{
		super(moveAcceptance);
		this.numberOfCities = numberOfCities;
	}
	
	
	public TSPHeuristicPerturbPFIH(int numberOfCities)
	{
		this.numberOfCities = numberOfCities;
	}
	
	public TSPHeuristicPerturbPFIH()
	{
		super();
		this.numberOfCities = 3;
	}
	
	@Override
	public TSPPartialSolution run(TSPPartialSolution current)
	{
		
		double currentCost;
		int[] currentCircuit;
		
		if (this.movementAcceptance == MovementAcceptance.FIRST_IMPROVEMENT || 
				this.movementAcceptance == MovementAcceptance.BEST_IMPROVEMENT)
		{
			currentCost = current.getCost();
			currentCircuit = current.getCurrentCircuit();
		}
		else
		{
			currentCost = Double.MAX_VALUE;
			currentCircuit = current.getCurrentCircuit();
		}
		
		int[] oldCircuit = current.getCurrentCircuit();
		int n = current.getCurrentLength();
		Random r = new Random();
		
		if (n <= numberOfCities)
		{
			return new TSPPartialSolution(current.getInstance(), currentCircuit);
		}
		int[] citiesToRemove = new int[numberOfCities];
		citiesToRemove[0] = r.nextInt(n - numberOfCities);
		for (int i = 1; i < numberOfCities; i++)
		{
			citiesToRemove[i] = citiesToRemove[i - 1] + 1 + r.nextInt(n - numberOfCities - citiesToRemove[i - 1] + i - 1) ;
		}
			
		
		int[] newCircuit = new int[n-numberOfCities];
		int j = 0;
		int k = 0;
		int a = citiesToRemove[j];
		
		for (int i = 0; i < n-1; i++)
		{
			if (i < a || j >= numberOfCities-1)
			{
				newCircuit[k] = oldCircuit[i];
				k++;
			}
			else
			{
				j++;
				a = citiesToRemove[j];
			}	
		}
		
		TSPPartialSolution constr = new TSPPartialSolution(current.getInstance(), newCircuit);
		
		TSPHeuristicPFIH pfih = new TSPHeuristicPFIH();
		
		for (int g = 0; g < numberOfCities; g++)
		{
			constr = pfih.run(constr);
		}
		
		newCircuit = constr.getCurrentCircuit();
		
		TSPPartialSolution candidateSolution =
				new TSPPartialSolution(current.getInstance(), newCircuit);
		
		if (candidateSolution.getCost() < currentCost)
		{
			if (this.movementAcceptance == MovementAcceptance.FIRST_IMPROVEMENT)
			{
				return candidateSolution;
			}
			else
			{
				currentCircuit = newCircuit;
				currentCost = candidateSolution.getCost();
			}
		}
		
		candidateSolution = new TSPPartialSolution(current.getInstance(), currentCircuit);
		
		return candidateSolution;
	}
	

}
