package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public abstract class BPPerturbativeHeuristic extends BaseHeuristic<BPPartialSolution>
{
	public boolean isConstructive()
	{
		return false;
	}
}
