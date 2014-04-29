package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPHeuristic;

public abstract class TSPPerturbativeHeuristic extends TSPHeuristic
{
	public TSPPerturbativeHeuristic()
	{
		super(MovementAcceptance.FIRST_IMPROVEMENT);
	}
	
	public TSPPerturbativeHeuristic(MovementAcceptance moveAcceptance)
	{
		super(moveAcceptance);
	}
	
	public boolean isConstructive()
	{
		return false;
	}
}
