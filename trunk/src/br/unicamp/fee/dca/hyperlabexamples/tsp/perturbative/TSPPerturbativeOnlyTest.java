package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.perturbative.HyperHeuristicGreedy;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;
import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.tsp.util.TSlibLoader;

public class TSPPerturbativeOnlyTest
{
	
	private HashMap<String, TSPPerturbativeHeuristic[]> heuristicsSets = new HashMap<String, TSPPerturbativeHeuristic[]>();
	private int instanceCount;
	
	
	//used only during generation of instances
	@SuppressWarnings("unused")
	private int instanceSize;
	
	public TSPPerturbativeOnlyTest(int instanceSize, int instanceCount){
		this.instanceCount = instanceCount;
		this.instanceSize = instanceSize;
		
		heuristicsSets.put("heuristics", new TSPPerturbativeHeuristic[]
				{
					new TSPHeuristicPerturbPFIH(),
					new TSPHeuristic3Opt(),
					new TSPHeuristic2Opt(),
					new TSPHeuristicOrOpt(),
					new TSPHeuristic25Opt(),
					new TSPHeuristic2ExchangeMutation(),
				});
	}
	
	public void run()
	{
		HashMap<String, Integer> modes = new HashMap<String, Integer>();
		modes.put("Free", 1000);
		
		HashMap<String, HyperResult> modeResults = new HashMap<String, HyperResult>();
		for (String s: modes.keySet())
		{
			modeResults.put(s, new HyperResult(heuristicsSets.keySet()));
		}
		
		for (int i = 10; i < 10 + instanceCount; i++)
		{
			System.out.println("i " + i);
			//TSPInstance tspInstance = new TSPInstance();
			//tspInstance.generateRandomInstance(instanceSize);
			TSlibLoader loader = new TSlibLoader();
			TSPInstance tspInstance = loader.read("C:\\Mestrado\\ALL_tsp\\swiss42.tsp");
			TSPPartialSolution tspInitial = new TSPPartialSolution(tspInstance, TSPPartialSolution.generateInitialCircuit(tspInstance));
			
			for (Map.Entry<String, Integer> mode: modes.entrySet())
			{
				System.out.println("mode " + mode.getKey());
				String modeName = mode.getKey();
				int modeIterations = mode.getValue();
				runHyperHeuristicsSet(tspInitial, modeResults.get(modeName), modeIterations);
			}
		}
		
		for (String s: modes.keySet())
		{
			printSolutionCostAndIterations(s, modeResults.get(s));
		}
	}
	
	private void printSolutionCostAndIterations(String s, HyperResult result)
	{
		printSolutionValues(s + "Cost", result.getSolutionValues());
		printSolutionValues(s + "Iterations", result.getSolutionIterations());
		printSolutionValues(s + "Time", result.getSolutionTimes());
	}

	
	private void printSolutionValues(String kind, HashMap<String, ? extends Object> heuristicsSolutionValues) 
	{
		try
		{
			PrintWriter writer = new PrintWriter("c:\\test\\TSPPerturbInstanceSolution-" + kind + ".txt", "UTF-8");
			
			for (Map.Entry<String, ? extends Object> e: heuristicsSolutionValues.entrySet())
			{
				String heuristicsSetName = e.getKey();
				Object heuristicsValue = e.getValue();
				writer.println(heuristicsSetName + ": " + heuristicsValue + "\n");
			}
			
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void runHyperHeuristicsSet(TSPPartialSolution tspInitial,
			HyperResult result,
			int iterations)
	{
		HyperHeuristicGreedy hyper = new HyperHeuristicGreedy(iterations);
		//HyperHeuristicChoiceFunction hyper = new HyperHeuristicChoiceFunction(1000, 5, 8, 0.0007);
		hyper.setLogFileName("Log");
		for (Map.Entry<String, TSPPerturbativeHeuristic[]> e: heuristicsSets.entrySet())
		{
			//tspInitial.plot("c:\\test\\TSPinstanceInitial2");
			System.out.println(tspInitial.getCost());
			
			System.out.println("e " + e.getKey());
			String heuristicsSetName = e.getKey();
			TSPPerturbativeHeuristic[] heuristicsSet = e.getValue();
			
			long start = System.nanoTime();
			TSPPartialSolution tspFinal = hyper.run(heuristicsSet, tspInitial, 1);
			long elapsedTime = System.nanoTime() - start;
			
			System.out.println("cost:" + tspFinal.getCost());
			
			result.updateValue(tspFinal.getCost()/instanceCount, heuristicsSetName);
			result.updateIteration(hyper.getLastIteration()/instanceCount, heuristicsSetName);
			result.updateTime(elapsedTime, heuristicsSetName);
			//tspFinal.plot("c:\\test\\TSPinstanceSolution2");
		}
	}
	
	public static void main(String[] args)
	{
		TSPPerturbativeOnlyTest a = new TSPPerturbativeOnlyTest(10, 1);
		a.run();
	}
}
