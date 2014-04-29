package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPInstance;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative.HyperHeuristicRandom;


public class BPPerturbativeTest {
	
	public static void main(String[] args)
	{
		BPPerturbativeHeuristic[] heuristics = new BPPerturbativeHeuristic[]
		{
			new BPHeuristicRemoveLeastFullBin(),
			new BPHeuristicRemoveMostItemsBin(),
			new BPHeuristicSwitchTwoRandomItems(),
			new BPHeuristicSwitchItemFromLeastFullBin(),
			new BPHeuristicRemoveRandomUnfilledBin(),
			new BPHeuristicSearchForItemToFillABin(),
		};

		int instanceCount = 1;
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			BPInstance bpi = new BPInstance();
			bpi = bpi.createRandomInstance(300, 100);
			BPPartialSolution bpInitial = new BPPartialSolution(bpi);
			bpInitial.generateRandomSolution();
			
			//HyperHeuristic hyper = new HyperHeuristicChoiceFunction(100, 5, 8, 0.00004);
			//HyperHeuristic hyper = new HyperHeuristicGreedy(200);
			HyperHeuristic hyper = new HyperHeuristicRandom(1000);
			hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			BPPartialSolution bpFinal = hyper.run(heuristics, bpInitial, 1);
			System.out.println("Number of bins: " + bpFinal.getBins().size());
			System.out.println("(bin weight) items: index(weight) index(weight) ..");
			for (BPBin bin : bpFinal.getBins())
			{
				System.out.println(bin.toString());
			}
		}
	}
}
