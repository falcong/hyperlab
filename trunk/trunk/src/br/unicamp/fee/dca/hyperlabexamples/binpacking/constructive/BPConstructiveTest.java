package br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPInstance;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGreedy;


public class BPConstructiveTest {
	
	public static void main(String[] args)
	{
		BPConstructiveHeuristic[] heuristics = new BPConstructiveHeuristic[]
		{
			new BPHeuristicFirstFitDecreasingBinCentric(),
			new BPHeuristicNextFit(),
			new BPHeuristicFirstFitDecreasing(),
			new BPHeuristicWorstFit(),
			new BPHeuristicBestFit(),
		};

		//int instanceSize = 30;
		int instanceCount = 1;
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			//GCInstance gcInstance = new GCInstance();
			//gcInstance.generateRandomGraph(instanceSize);
			BPInstance bpi = new BPInstance();
			bpi = bpi.createRandomInstance(300, 1110);
			BPPartialSolution bpInitial = new BPPartialSolution(bpi);
			
			HyperHeuristic hyper = new HyperHeuristicGreedy();
			hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			BPPartialSolution bpFinal = hyper.run(heuristics, bpInitial, 5);
			System.out.println("Number of bins: " + bpFinal.getBins().size());
			System.out.println("(bin weight) items: index(weight) index(weight) ..");
			for (BPBin bin : bpFinal.getBins())
			{
				System.out.println(bin.toString());
			}
		}
	}
}
