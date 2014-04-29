package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive;

//import br.unicamp.fee.dca.hyperboleexamples.hyperheuristics.constructive.HyperHeuristicGenetic.PopulationType;
//import br.unicamp.fee.dca.hyperboleexamples.hyperheuristics.constructive.HyperHeuristicGenetic.SelectionMethod;
//import br.unicamp.fee.dca.hyperboleexamples.hyperheuristics.geneticAlgorithmUtil.GeneticAlgorithmHyperHeuristicBuilder;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCInstance;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGreedy;


public class GCConstructiveTest {
	
	public static void main(String[] args)
	{
		GCConstructiveHeuristic[] heuristics = new GCConstructiveHeuristic[]
		{
			new GCHeuristicLargestDegree(),
			new GCHeuristicLargestColorDegree(),
			new GCHeuristicLeastPossibleColors(),
			new GCHeuristicLargestDegreeAndColorDegree(),
			new GCHeuristicFirstFit(),
			new GCHeuristicLeastPossibleColorsAndLargestDegree(),
			new GCHeuristicLargestDegreeAndHighestWeight(),
			new GCHeuristicHighestWeight(),
		};

		int instanceSize = 60;
		int instanceCount = 1;
		
		HyperHeuristic hyper = new HyperHeuristicGreedy();
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			GCInstance gcInstance = new GCInstance();
			gcInstance.generateRandomGraph(instanceSize);
			//GCInstanceLoader iL = new GCInstanceLoader();
			//GCInstance gcInstance = iL.read("C:\\Mestrado\\hyperbole\\trunk\\src\\br\\unicamp\\fee\\dca\\hyperboleexamples\\graphcoloring\\Instances\\queen5_5.col");
			GCPartialSolution gcInitial = new GCPartialSolution(gcInstance);
			
			//HyperHeuristic hyper = new GeneticAlgorithmHyperHeuristicBuilder().setMutationRate(0.001).setNumberOfIterations(1000).setOffspringPopulationSize(100).setParentsPopulationSize(50).setPopulationType(PopulationType.MU_LAMBDA).setSelectionMethod(SelectionMethod.TOURNAMENT_WITH_REPETITION).settournamentSize(4).build();
			
			hyper.setLogFileName("c:\\test\\TSPinstance" + i + "path");
			GCPartialSolution gcFinal = hyper.run(heuristics, gcInitial, 5);
			System.out.println(gcFinal.getCost());
			gcFinal.printColors();
			gcFinal.printGraphToGif("C:/Program Files (x86)/Graphviz2.34/bin/dot.exe", "C:/Users/Eduardo/Desktop/graphviz-java-api/sample");
		}
	}
}
