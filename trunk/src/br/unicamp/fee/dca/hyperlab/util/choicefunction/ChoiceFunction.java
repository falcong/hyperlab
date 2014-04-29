package br.unicamp.fee.dca.hyperlab.util.choicefunction;

import java.util.Random;


public class ChoiceFunction {

	private double weightHeuristic = 1;
	private double weightPairOfHeuristics = 1;
	private double weightTime = 1;
	private int numberOfHeuristics = 1;
	private HistoricMapping performanceMapping;
	private long[] timeSinceHeuristic;
	private int lastHeuristic = 0;
	private long time;
	
	public ChoiceFunction(double weightHeuristic2, double weightPairOfHeuristics2, double weightTimeSinceHeuristic, int numberOfHeuristics)
	{
		this.weightHeuristic = weightHeuristic2;
		this.weightPairOfHeuristics = weightPairOfHeuristics2;
		this.weightTime = weightTimeSinceHeuristic;
		this.numberOfHeuristics = numberOfHeuristics;
		performanceMapping = new HistoricMapping(numberOfHeuristics);
		timeSinceHeuristic = new long[numberOfHeuristics];
		time = System.nanoTime();
	}
	
	public int chooseHeuristic()
	{
		float[] weight = new float[numberOfHeuristics];
		for (int i = 0; i < numberOfHeuristics; i++)
		{
			double individualAverage = performanceMapping.getAverage(i);
			double pairAverage = performanceMapping.getAverage(lastHeuristic, i);
			weight[i] = (float) (weightHeuristic * individualAverage
							+ weightPairOfHeuristics * pairAverage
							+ weightTime * timeSinceHeuristic[i]);
		}
		Random generator = new Random();
		
		float max = 0;
		int nextHeuristic = 0;
		
		for (int i = 0; i < numberOfHeuristics; i++)
		{
			if (weight[i] > max)
			{
				nextHeuristic = i;
				max = weight[i];
			}
			else if (weight[i] == max && generator.nextBoolean())
			{
				nextHeuristic = i;
			}
		}
		
		return nextHeuristic;
	}
	
	public void updateHistory(int heuristic, double performance)
	{
		performanceMapping.updateHistoric(lastHeuristic, heuristic, performance);
		performanceMapping.updateHistoric(heuristic, performance);
		lastHeuristic = heuristic;
		long newTime = System.nanoTime();
		for (int i = 0; i < numberOfHeuristics; i++)
		{
			if (i == lastHeuristic)
			{
				timeSinceHeuristic[i] = 0;
			}
			else
			{
				timeSinceHeuristic[i] += (newTime - time);
			}
		}
		time = newTime;
	}
}
