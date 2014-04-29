package br.unicamp.fee.dca.hyperlabexamples.benchmark;

import java.io.File;
import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.benchmark.Benchmark;
import br.unicamp.fee.dca.hyperlab.benchmark.Problem;
import br.unicamp.fee.dca.hyperlab.benchmark.ProblemSuite;
import br.unicamp.fee.dca.hyperlab.benchmark.Results;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicRandom;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative.HyperHeuristicChoiceFunction;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative.HyperHeuristicGreedy;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic25Opt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic2ExchangeMutation;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic2Opt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic3Opt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristicOrOpt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristicPerturbPFIH;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPPerturbativeHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.tsp.util.TSlibLoader;

public class BenchmarkAllTSP
{
	public static void main(String[] s)
	{
		ProblemSuite problemSuite = createProblemSuite();
		HyperHeuristic[] hyperHeuristics = createHyperHeuristics();
		Benchmark b = new Benchmark(hyperHeuristics, problemSuite);
		Results results = b.run();
		results.writeToCsv("benchmark_results.csv");
	}
	
	static ProblemSuite createProblemSuite()
	{
		ProblemSuite problemSuite = new ProblemSuite();
		
		// TSP
		TSPPerturbativeHeuristic[] tspHeuristics = new TSPPerturbativeHeuristic[]
		{
			new TSPHeuristicPerturbPFIH(),
			new TSPHeuristic3Opt(),
			new TSPHeuristic2Opt(),
			new TSPHeuristicOrOpt(),
			new TSPHeuristic25Opt(),
			new TSPHeuristic2ExchangeMutation(),
		};
/*
		for (int instanceSize = 10; instanceSize <= 80; instanceSize += 10)
		{
			TSPPartialSolution[] problemInstances = new TSPPartialSolution[30];
			for (int i = 0; i < problemInstances.length; i++)
			{
				TSPInstance tspInstance = new TSPInstance();
				tspInstance.generateRandomInstance(instanceSize);
				problemInstances[i] = new TSPPartialSolution(tspInstance);
				problemInstances[i].generateRandomSolution();
			}
			problemSuite.add(new Problem<TSPPartialSolution>(tspHeuristics, problemInstances,
					String.format("TSP %d cities", instanceSize)));
		}
	*/
		TSlibLoader loader = new TSlibLoader();
		File folder = new File("C:\\Mestrado\\ALL_tsp");
		
		for (final File fileEntry : folder.listFiles()) 
		{
		    if (fileEntry.getName().endsWith(".tsp")) 
	        {
		    	ArrayList<TSPPartialSolution> problemInstancesArray = new ArrayList<TSPPartialSolution>();
		    	for (int k = 0; k < 10; k++)
			    {
		        	TSPInstance tspInstance = loader.read("C:\\Mestrado\\ALL_tsp\\" + fileEntry.getName());
		        	problemInstancesArray.add(new TSPPartialSolution(tspInstance));
			    }
		    	
		    	TSPPartialSolution[] problemInstances = new TSPPartialSolution[problemInstancesArray.size()];
				for (int i = 0; i < problemInstancesArray.size(); i++)
				{
					problemInstances[i] = problemInstancesArray.get(i);
					problemInstances[i].generateRandomSolution();
				}
				
				problemSuite.add(new Problem<TSPPartialSolution>(tspHeuristics, problemInstances,
						fileEntry.getName()));
	        }   
	    }
		return problemSuite;
	}
	
	static HyperHeuristic[] createHyperHeuristics()
	{
		HyperHeuristic[] hyperHeuristics = new HyperHeuristic[]
		{
			new HyperHeuristicChoiceFunction(1000, 5, 8, 0.0000045),
			new HyperHeuristicChoiceFunction(1000, 8, 4, 0.00045),
			new HyperHeuristicGreedy(80),
			new HyperHeuristicRandom(5000),
		};
		return hyperHeuristics;
	}
}
