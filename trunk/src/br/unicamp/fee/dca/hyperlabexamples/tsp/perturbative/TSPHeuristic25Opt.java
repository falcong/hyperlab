package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.util.LoopingIterator;

public class TSPHeuristic25Opt extends TSPPerturbativeHeuristic
{	
	public TSPHeuristic25Opt()
	{
		super();
	}
	
	public TSPHeuristic25Opt(MovementAcceptance moveAcceptance)
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
			return new TSPPartialSolution(current.getInstance(), oldCircuit);
		}
		
		
		int[] startingNumbers = new int[2];
		startingNumbers[0] = r.nextInt(n-3);
		startingNumbers[1] = startingNumbers[0] + r.nextInt((n - startingNumbers[0]-2));
			
		int a = startingNumbers[0];
		
		
		LoopingIterator iterateA = new LoopingIterator(0, n - 3, a);
		
		int b = startingNumbers[1];
		LoopingIterator iterateB = new LoopingIterator(a + 1, n - 2, b);
		int c = b + 1;
		
		while (!iterateA.isOver())
		{
			while (!iterateB.isOver())
			{
				int[] newCircuit = new int[n];
				int j = 0;
				
				for (int i = c; i < n; i++)
				{
					newCircuit[j] = oldCircuit[i];
					j++;
				}
				for (int i = 0; i < a; i++)
				{
					newCircuit[j] = oldCircuit[i];
					j++;
				}
				for (int i = 0; i < (c - b); i++)
				{
					newCircuit[j] = oldCircuit[c - 1 - i];
					j++;
				}
				for (int i = a; i < b; i++)
				{
					newCircuit[j] = oldCircuit[i];
					j++;
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
				b = iterateB.getNext();
				c = b + 1;
			}
			a = iterateA.getNext();
			b = startingNumbers[1];
			if (b < a){
				b = a + 1;
			}
			iterateB.reset(a + 1, n - 2, b);
		}

		TSPPartialSolution candidateSolution =
				new TSPPartialSolution(current.getInstance(), currentCircuit);
		return candidateSolution;
	}

}
