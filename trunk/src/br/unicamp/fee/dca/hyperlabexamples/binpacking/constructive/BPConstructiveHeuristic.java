package br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public abstract class BPConstructiveHeuristic extends BaseHeuristic<BPPartialSolution>
{
	public boolean isConstructive()
	{
		return true;
	}
}
