package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.mixed;

import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCInstance;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicFirstFit;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicHighestWeight;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicLargestColorDegree;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicLargestDegree;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicLargestDegreeAndColorDegree;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicLargestDegreeAndHighestWeight;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicLeastPossibleColors;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive.GCHeuristicLeastPossibleColorsAndLargestDegree;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative.GCHeuristicChangeVertexColor;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative.GCHeuristicChangeVertexColorToLower;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative.GCHeuristicPaintIndependentSet;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.mixed.HyperHeuristicMixedRandomAndCF;

public class GCMixedTest
{
	public static void main(String[] args)
	{
		GCHeuristic[] heuristics = new GCHeuristic[]
		{
			new GCHeuristicLargestDegree(),
			new GCHeuristicLargestColorDegree(),
			new GCHeuristicLeastPossibleColors(),
			new GCHeuristicLargestDegreeAndColorDegree(),
			new GCHeuristicFirstFit(),
			new GCHeuristicLeastPossibleColorsAndLargestDegree(),
			new GCHeuristicLargestDegreeAndHighestWeight(),
			new GCHeuristicHighestWeight(),
			new GCHeuristicChangeVertexColor(),
			new GCHeuristicChangeVertexColorToLower(),
			new GCHeuristicPaintIndependentSet(),
		};
		
		
		int instanceSize = 30;
		int instanceCount = 3;
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			GCInstance gcInstance = new GCInstance();
			gcInstance.generateRandomGraph(instanceSize);
			GCPartialSolution gcInitial = new GCPartialSolution(gcInstance);
			
			//tspInstance.plot("c:\\test\\problem" + i);
			
			HyperHeuristic hyper = new HyperHeuristicMixedRandomAndCF(1000, 5, 8, 0.0000045);
			hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			GCPartialSolution gcFinal = hyper.run(heuristics, gcInitial, 1);
			
			gcFinal.printColors();
			System.out.println("Number of colors: " + gcFinal.getCost());
			gcFinal.printGraphToGif("C:/Program Files (x86)/Graphviz2.34/bin/dot.exe", "C:/test/gc");
		}
	}
}
