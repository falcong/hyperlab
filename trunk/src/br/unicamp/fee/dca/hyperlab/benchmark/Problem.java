package br.unicamp.fee.dca.hyperlab.benchmark;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlab.util.Timer;

public class Problem<T extends PartialSolution>
{
	BaseHeuristic<T>[] heuristics;
	T[] problemInstances;
	String problemName;

	public Problem(BaseHeuristic<T>[] heuristics, T[] problemInstances)
	{
		init(heuristics, problemInstances, null);
	}

	public Problem(BaseHeuristic<T>[] heuristics, T[] problemInstances, String problemName)
	{
		init(heuristics, problemInstances, problemName);
	}
	
	private void init(BaseHeuristic<T>[] heuristics, T[] problemInstances, String problemName)
	{
		this.heuristics = heuristics;
		this.problemInstances = problemInstances;
		this.problemName = problemName;
	}

	public void runHyperHeuristic(int hh, int p, HyperHeuristic hyperHeuristic, Results results)
	{
		for (int i = 0; i < problemInstances.length; i++)
		{
			T problemInstance = problemInstances[i];
			
			Timer timer = new Timer();
			T result = hyperHeuristic.run(heuristics, problemInstance, 1);
			double timeElapsed = timer.stopAndGetElapsedTime();
			
			results.register(hh, p, i, result.getCost(), timeElapsed);
		}
	}

	public String getName()
	{
		return problemName;
	}
}
