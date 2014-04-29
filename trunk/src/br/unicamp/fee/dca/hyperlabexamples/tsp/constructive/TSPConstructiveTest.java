package br.unicamp.fee.dca.hyperlabexamples.tsp.constructive;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGreedy;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicRandom;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic.PopulationType;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic.SelectionMethod;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.geneticAlgorithmUtil.GeneticAlgorithmHyperHeuristicBuilder;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.util.TSlibLoader;

public class TSPConstructiveTest
{
	public static void main(String[] args)
	{
		TSPConstructiveHeuristic[] heuristics = new TSPConstructiveHeuristic[]
		{
			new TSPHeuristicNearest(),
			new TSPHeuristicAcutest(-1.0, 2.0),
			new TSPHeuristicPFIH(),
		};

		int instanceSize = 30;
		int instanceCount = 3;
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			//TSPInstance tspInstance = new TSPInstance();
			TSlibLoader loader = new TSlibLoader();
			TSPInstance tspInstance = loader.read("C:\\Mestrado\\ALL_tsp\\att48.tsp");
			//tspInstance.generateRandomInstance(instanceSize);
			TSPPartialSolution tspInitial = new TSPPartialSolution(tspInstance);
			
			//tspInstance.plot("c:\\test\\problem" + i);
			
			HyperHeuristic hyper = new GeneticAlgorithmHyperHeuristicBuilder().setMutationRate(0.001).setNumberOfIterations(500).setOffspringPopulationSize(100).setParentsPopulationSize(50).setPopulationType(PopulationType.MU_LAMBDA).setSelectionMethod(SelectionMethod.TOURNAMENT_WITH_REPETITION).settournamentSize(3).build();
		    hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			TSPPartialSolution tspFinal = hyper.run(heuristics, tspInitial, 1);
			
			tspFinal.plot("c:\\test\\TSPinstance" + i + "solution");
		}
	}
}
