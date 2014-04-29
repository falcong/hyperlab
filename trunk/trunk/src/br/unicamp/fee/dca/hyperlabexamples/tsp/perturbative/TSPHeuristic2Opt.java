package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;

public class TSPHeuristic2Opt extends TSPPerturbativeHeuristic
{
	
	public TSPHeuristic2Opt()
	{
		super();
	}
	
	public TSPHeuristic2Opt(MovementAcceptance moveAcceptance)
	{
		super(moveAcceptance);
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
		
		if (n <= 3)
		{
			return new TSPPartialSolution(current.getInstance(), currentCircuit);
		}
		
		int baseA = r.nextInt(n);
		
		for (int deltaA = 0; deltaA < n; deltaA++)
		{
			int a = (baseA + deltaA) % n;
			int baseB = r.nextInt(n);
			int b;
			for (int deltaB = 1; (b = a + baseB + deltaB) <= n; deltaB++)
			{
				int[] newCircuit = new int[n];
				
				for (int i = 0; i < n; i++)
				{
					if (i < a || i >= b)
					{
						newCircuit[i] = oldCircuit[i];
					}
					else
					{
						newCircuit[i] = oldCircuit[a + b - 1 - i];
					}
				}
				
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
			}
		}
		
		TSPPartialSolution candidateSolution =
				new TSPPartialSolution(current.getInstance(), currentCircuit);
		
		return candidateSolution;
	}
}
