package br.unicamp.fee.dca.hyperlabanalyzer;

public class GenericBenchmark
{
	public enum Metric
	{
		COST,
		TIME
	}
	
	private ValueSet[] mEntriesCost;
	private ValueSet[] mEntriesTime;
	
	public GenericBenchmark(int n)
	{
		mEntriesCost = new ValueSet[n];
		mEntriesTime = new ValueSet[n];
	}

	public ValueSet[] getEntriesCost()
	{
		return mEntriesCost;
	}

	public ValueSet[] getEntriesTime()
	{
		return mEntriesTime;
	}

	public ValueSet[] getMetric(Metric metric)
	{
		switch (metric)
		{
			case COST:
				return mEntriesCost;
			case TIME:
				return mEntriesTime;
			default:
				return null;
		}
	}

	public void addCostAndTimeValues(int i, ValueSet costValues, ValueSet timeValues)
	{
		mEntriesCost[i] = costValues;
		mEntriesTime[i] = timeValues;
	}
	
	public int getNumberOfEntries()
	{
		return mEntriesCost.length;
	}
}
