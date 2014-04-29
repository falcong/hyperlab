package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.util.LoopingIterator;

public class TSPHeuristic3Opt extends TSPPerturbativeHeuristic
{
	
	public TSPHeuristic3Opt()
	{
		super();
	}
	
	public TSPHeuristic3Opt(MovementAcceptance moveAcceptance)
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
		
		if (n > 3)
		{
			
			int[] startingNumbers = new int[3];
			startingNumbers[0] = r.nextInt(n-3);
			startingNumbers[1] = startingNumbers[0] + r.nextInt((n - startingNumbers[0]-2));
			startingNumbers[2] = startingNumbers[1] + r.nextInt((n - startingNumbers[1]-1));
				
			int a = startingNumbers[0];
			
			
			LoopingIterator iterateA = new LoopingIterator(0, n-3, a);
			
			int b = startingNumbers[1];
			LoopingIterator iterateB = new LoopingIterator(a + 1, n-2, b);
			
			int c = startingNumbers[2];
			LoopingIterator iterateC = new LoopingIterator(b + 1, n-1, c);
			while (!iterateA.isOver())
			{
				while (!iterateB.isOver())
				{
					while(!iterateC.isOver())
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
						int k = j;
						int baseForm = r.nextInt(4);							
						for (int deltaForm = 0; deltaForm < 4; deltaForm++)
						{
							j = k;
							int form = (baseForm + deltaForm) % 4;
							if (form == 0)
							{
								for (int i = b; i < c; i++)
								{
									newCircuit[j] = oldCircuit[i];
									j++;
								}
								for (int i = 0; i < (b - a); i++)
								{
									newCircuit[j] = oldCircuit[b - 1 - i];
									j++;
								}
							}
							if (form == 1)
							{
								for (int i = 0; i < (b - a); i++)
								{
									newCircuit[j] = oldCircuit[b - 1 - i];
									j++;
								}
								for (int i = 0; i < (c - b); i++)
								{
									newCircuit[j] = oldCircuit[c - 1 - i];
									j++;
								}
							}
							if (form == 2)
							{
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
							}
							if (form == 3)
							{
								for (int i = b; i < c; i++)
								{
									newCircuit[j] = oldCircuit[i];
									j++;
								}
								for (int i = a; i < b; i++)
								{
									newCircuit[j] = oldCircuit[i];
									j++;
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
						c = iterateC.getNext();
					}
					b = iterateB.getNext();
					c = startingNumbers[2];
					if (c < b){
						c = b + 1;
					}
					iterateC.reset(b + 1, n-1, c);
				}
				a = iterateA.getNext();
				b = startingNumbers[1];
				if (b < a){
					b = a + 1;
				}
				iterateB.reset(a + 1, n-2, b);
			}
		}

		TSPPartialSolution candidateSolution =
				new TSPPartialSolution(current.getInstance(), currentCircuit);
		
		return candidateSolution;
	}
}
