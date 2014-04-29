package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;

public class TSPHeuristic2ExchangeMutation extends TSPPerturbativeHeuristic
{
	public TSPHeuristic2ExchangeMutation()
	{
		super();
	}
	
	public TSPHeuristic2ExchangeMutation(MovementAcceptance moveAcceptance)
	{
		super(moveAcceptance);
	}
	
	@Override
	public TSPPartialSolution run(TSPPartialSolution current)
	{
				
		int[] oldCircuit = current.getCurrentCircuit();
		int n = current.getCurrentLength();
		Random r = new Random();
		
		if (n <= 3)
		{
			return new TSPPartialSolution(current.getInstance(), oldCircuit);
		}
		
		int a = r.nextInt(n);
		int b = r.nextInt(n);
		
		while (a == b)
		{
			b = r.nextInt(n);
		}
		
		int[] newCircuit = new int[n];
		
		for (int i = 0; i < n; i++)
		{
			newCircuit[i] = oldCircuit[i];
		}

		newCircuit[a] = oldCircuit[b];
		newCircuit[b] = oldCircuit[a];
		
		TSPPartialSolution candidateSolution =
				new TSPPartialSolution(current.getInstance(), newCircuit);
		
		return candidateSolution;
	}
	

}
