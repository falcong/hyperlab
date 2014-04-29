package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCHeuristic;

public abstract class GCPerturbativeHeuristic extends GCHeuristic
{
	public GCPerturbativeHeuristic()
	{
		super();
	}
	
	public boolean isConstructive()
	{
		return false;
	}
}
