package br.unicamp.fee.dca.hyperlab.util.choicefunction;

import java.util.ArrayList;
import java.util.HashMap;

class HistoricMapping {
	private HashMap<String, Historic> mapping;
	
	public HistoricMapping(int numberOfHeuristics)
	{
		mapping = new HashMap<String, Historic>();

		for (int i = 0; i < numberOfHeuristics; i++)
		{
			mapping.put(Integer.toString(i), new Historic());
			for (int j = 0; j < numberOfHeuristics; j++)
			{
				mapping.put(i + "," + j, new Historic());
			}
		}
	}
	
	public ArrayList<Double> getHeuristicHistory(int heuristicIndex)
	{
		return mapping.get(Integer.toString(heuristicIndex)).getHeuristicHistory();
	}
	
	public ArrayList<Double> getHeuristicHistory(int firstHeuristicIndex, int secondHeuristicIndex)
	{
		return mapping.get(firstHeuristicIndex + "," + secondHeuristicIndex).getHeuristicHistory();
	}
	
	
	public double getAverage(int heuristicIndex)
	{
		return mapping.get(Integer.toString(heuristicIndex)).getAverage();
	}
	
	public double getAverage(int firstHeuristicIndex, int secondHeuristicIndex)
	{
		return mapping.get(firstHeuristicIndex + "," + secondHeuristicIndex).getAverage();
	}
	
	public double getRecentAverage(int heuristicIndex, int iterations)
	{
		return mapping.get(Integer.toString(heuristicIndex)).getRecentAverage(iterations);
	}
	
	public double getRecentAverage(int firstHeuristicIndex, int secondHeuristicIndex, int iterations)
	{
		return mapping.get(firstHeuristicIndex + "," + secondHeuristicIndex).getRecentAverage(iterations);
	}
	
	public void updateHistoric(int heuristicIndex, double performance)
	{
		mapping.get(Integer.toString(heuristicIndex)).updateHistoric(performance);
	}
	
	public void updateHistoric(int firstHeuristicIndex, int secondHeuristicIndex, double performance)
	{
		mapping.get(firstHeuristicIndex + "," + secondHeuristicIndex).updateHistoric(performance);
	}
	
}
