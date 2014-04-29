package br.unicamp.fee.dca.hyperlabexamples.tsp;

import br.unicamp.fee.dca.hyperlab.Heuristic;

public abstract class TSPHeuristic extends Heuristic<TSPPartialSolution>
{

	public TSPHeuristic(MovementAcceptance moveAcceptance)
	{
		super(moveAcceptance);
	}
}
