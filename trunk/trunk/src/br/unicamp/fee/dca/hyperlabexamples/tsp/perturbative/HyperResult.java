package br.unicamp.fee.dca.hyperlabexamples.tsp.perturbative;

import java.util.HashMap;
import java.util.Set;

public class HyperResult {
	
	private HashMap<String, Double> heuristicsSolutionValues;
	private HashMap<String, Double> heuristicsSolutionIterations;
	private HashMap<String, Long> heuristicsSolutionTimes;
	
	public HyperResult(Set<String> heuristicNames)
	{
		heuristicsSolutionValues = new HashMap<String, Double>();
		for (String s: heuristicNames)
		{
			heuristicsSolutionValues.put(s, 0.0);
		}
		heuristicsSolutionIterations = new HashMap<String, Double>();
		for (String s: heuristicNames)
		{
			heuristicsSolutionIterations.put(s, 0.0);
		}
		heuristicsSolutionTimes = new HashMap<String, Long>();
		for (String s: heuristicNames)
		{
			heuristicsSolutionTimes.put(s, (long)0);
		}
	}
	
	public HashMap<String, Double> getSolutionValues()
	{
		return heuristicsSolutionValues;
	}
	
	public HashMap<String, Double> getSolutionIterations()
	{
		return heuristicsSolutionIterations;
	}
	
	public HashMap<String, Long> getSolutionTimes()
	{
		return heuristicsSolutionTimes;
	}
	
	public void updateValue(double value, String heuristicsSetName)
	{
		updateHashmap(value, heuristicsSetName, heuristicsSolutionValues);
	}

	public void updateIteration(double value, String heuristicsSetName)
	{
		updateHashmap(value, heuristicsSetName, heuristicsSolutionIterations);
	}
	
	public void updateTime(Long value, String heuristicsSetName)
	{
		updateHashmapLong(value, heuristicsSetName, heuristicsSolutionTimes);
	}

	private void updateHashmap(double value, String heuristicsSetName, HashMap<String, Double> hashmap)
	{
		Double sum = hashmap.get(heuristicsSetName);
		sum += value;
		hashmap.put(heuristicsSetName, sum);
	}
	
	private void updateHashmapLong(Long value, String heuristicsSetName, HashMap<String, Long> hashmap)
	{
		Long sum = hashmap.get(heuristicsSetName);
		sum += value;
		hashmap.put(heuristicsSetName, sum);
	}
	
}
