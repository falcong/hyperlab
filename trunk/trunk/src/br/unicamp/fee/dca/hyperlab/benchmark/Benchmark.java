package br.unicamp.fee.dca.hyperlab.benchmark;

import java.util.List;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.util.Timer;

public class Benchmark
{
	Results results;
	HyperHeuristic[] hyperHeuristics;
	ProblemSuite problemSuite;
	
	public Benchmark(HyperHeuristic[] hyperHeuristics, ProblemSuite problemSuite)
	{
		this.results = null;
		this.hyperHeuristics = hyperHeuristics;
		this.problemSuite = problemSuite;
	}
	
	public Results run()
	{
		int hyperHeuristicNumber = hyperHeuristics.length;
		List<Problem<?>> problems = problemSuite.getProblems();
		int problemNumber = problems.size();
		
		System.out.println("Running benchmark for " + hyperHeuristics.length + " hyper-heuristic"
			+ (hyperHeuristicNumber != 1 ? "s" : "") + " and " + problemNumber + " problem"
			+ (problemNumber != 1 ? "s" : "") + ".");

		Timer timer = new Timer();
		
		results = new Results(hyperHeuristics, problems);
		for (int hh = 0; hh < hyperHeuristicNumber; hh++)
		{
			HyperHeuristic hyperHeuristic = hyperHeuristics[hh];
			for (int p = 0; p < problemNumber; p++)
			{
				Problem<?> problem = problems.get(p);
				System.out.println("Solving problem " + problem.problemName + "...");
				problem.runHyperHeuristic(hh, p, hyperHeuristic, results);
				System.out.println("...finished.");
			}
		}
		
		double totalTimeElapsed = timer.stopAndGetElapsedTime();
		System.out.println("Finished running benchmark. Took " + totalTimeElapsed + " seconds.");

		return results;
	}
}
