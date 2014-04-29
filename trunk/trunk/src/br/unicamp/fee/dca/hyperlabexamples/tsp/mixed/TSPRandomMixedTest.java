package br.unicamp.fee.dca.hyperlabexamples.tsp.mixed;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.Heuristic.MovementAcceptance;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.mixed.HyperHeuristicRandom;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.constructive.TSPHeuristicAcutest;
import br.unicamp.fee.dca.hyperlabexamples.tsp.constructive.TSPHeuristicNearest;
import br.unicamp.fee.dca.hyperlabexamples.tsp.constructive.TSPHeuristicPFIH;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic25Opt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic2ExchangeMutation;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic2Opt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristic3Opt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristicOrOpt;
import br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative.TSPHeuristicPerturbPFIH;

public class TSPRandomMixedTest
{
	public static void main(String[] args)
	{
		TSPHeuristic[] heuristics = new TSPHeuristic[]
		{
			new TSPHeuristicNearest(),
			new TSPHeuristicAcutest(-1.0, 2.0),
			new TSPHeuristicPFIH(),
			new TSPHeuristic3Opt(MovementAcceptance.BEST_IMPROVEMENT),
			new TSPHeuristic2Opt(MovementAcceptance.BEST_IMPROVEMENT),
			new TSPHeuristicOrOpt(MovementAcceptance.BEST_IMPROVEMENT),
			new TSPHeuristic25Opt(MovementAcceptance.BEST_IMPROVEMENT),
			new TSPHeuristicPerturbPFIH(MovementAcceptance.BEST_IMPROVEMENT),
			new TSPHeuristic2ExchangeMutation(),
		};
		
		
		int instanceSize = 30;
		int instanceCount = 3;
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			TSPInstance tspInstance = new TSPInstance();
			tspInstance.generateRandomInstance(instanceSize);
			TSPPartialSolution tspInitial = new TSPPartialSolution(tspInstance);
			
			//tspInstance.plot("c:\\test\\problem" + i);
			
			HyperHeuristic hyper = new HyperHeuristicRandom(500);
			hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			TSPPartialSolution tspFinal = hyper.run(heuristics, tspInitial, 1);
			
			tspFinal.plot("c:\\test\\TSPinstance" + i + "solution");
		}
	}
}