package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.perturbative;

//import br.unicamp.fee.dca.hyperboleexamples.hyperheuristics.perturbative.HyperHeuristicChoiceFunction;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCInstance;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative.HyperHeuristicRandom;


public class GCPerturbativeTest {
	
	public static void main(String[] args)
	{
		GCPerturbativeHeuristic[] heuristics = new GCPerturbativeHeuristic[]
		{
			new GCHeuristicChangeVertexColor(),
			new GCHeuristicChangeVertexColorToLower(),
			new GCHeuristicPaintIndependentSet(),
		};

		int instanceSize = 50;
		int instanceCount = 1;
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			GCInstance gcInstance = new GCInstance();
			gcInstance.generateRandomGraph(instanceSize);
			//GCInstanceLoader iL = new GCInstanceLoader();
			//GCInstance gcInstance = iL.read("C:\\Mestrado\\hyperbole\\trunk\\src\\br\\unicamp\\fee\\dca\\hyperboleexamples\\graphcoloring\\Instances\\queen10_10.col");
			GCPartialSolution gcInitial = new GCPartialSolution(gcInstance);
			gcInitial.generateRandomSolution();

			//HyperHeuristic hyper = new HyperHeuristicChoiceFunction(100, 5, 8, 0.00004);
			HyperHeuristic hyper = new HyperHeuristicRandom(100);
			hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			GCPartialSolution gcFinal = hyper.run(heuristics, gcInitial, 1);
			gcFinal.printColors();
			gcFinal.printGraphToGif("C:/Program Files (x86)/Graphviz2.34/bin/dot.exe", "C:/Users/Eduardo/Desktop/graphviz-java-api/sample");
		}
	}
}
