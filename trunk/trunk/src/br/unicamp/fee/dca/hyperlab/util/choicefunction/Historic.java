package br.unicamp.fee.dca.hyperlab.util.choicefunction;

import java.util.ArrayList;

class Historic {
	private ArrayList<Double> historic;
	
	public Historic()
	{
		historic = new ArrayList<Double>();
		
	}
	
	public ArrayList<Double> getHeuristicHistory()
	{
		return historic;
	}
	
	public double getAverage()
	{
		int size = historic.size();
		double sum = 0;
		for (int i = 0; i < size; i++)
		{
			sum += historic.get(i);
		}
		if (size > 0)
			return sum / size;
		else 
			return 0;
	}
	
	public double getRecentAverage(int iterations)
	{
		int size = historic.size();
		if (size < iterations)
		{
			iterations = size;
		}
		double sum = 0;
		for (int i = size - iterations; i < size; i++)
		{
			sum += historic.get(i);
		}
		if (size > 0)
			return sum / Math.max(iterations, size);
		else 
			return 0;
	}
	
	public void updateHistoric(double performance)
	{
		historic.add(performance);
	}
}
