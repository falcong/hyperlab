package br.unicamp.fee.dca.hyperlabanalyzer;

import java.util.Arrays;

public class ValueSet
{
	private double[] mValues;
	private int mCount;
	private double mTotal;
	private double mAverage;
	private double mVariance;
	private double mStandardDeviation;

	public ValueSet(double[] values)
	{
		mValues = values;
		mCount = mValues.length;
		Arrays.sort(mValues);
		double totalOfSquares = 0;
		mTotal = 0;
		for (int i = 0; i < mCount; i++)
		{
			mTotal += mValues[i];
			totalOfSquares += mValues[i] * mValues[i];
		}
		mAverage = mTotal / mCount;
		mVariance = (totalOfSquares - (mTotal * mTotal) / mCount) / (mCount - 1);
		mStandardDeviation = Math.sqrt(mVariance);
	}

	public double[] getValues()
	{
		return mValues;
	}

	public int getCount()
	{
		return mCount;
	}

	public double getTotal()
	{
		return mTotal;
	}

	public double getAverage()
	{
		return mAverage;
	}

	public double getVariance()
	{
		return mVariance;
	}

	public double getStandardDeviation()
	{
		return mStandardDeviation;
	}
	
	public double getMin()
	{
		return mValues[0];
	}
	
	public double getMax()
	{
		return mValues[mCount - 1];
	}

	public String getSummaryString()
	{
		return ("[Average: " + mAverage
				 + ", " + "Min: " + getMin()
				 + ", " + "Max: " + getMax()
				 + "]");
	}
	
	public static double getMinInArray(ValueSet[] valueSets)
	{
		double lowest = Double.POSITIVE_INFINITY;
		for (ValueSet set: valueSets)
		{
			if (set.getMin() < lowest)
			{
				lowest = set.getMin();
			}
		}
		return lowest;
	}
	
	public static double getMaxInArray(ValueSet[] valueSets)
	{
		double highest = Double.NEGATIVE_INFINITY;
		for (ValueSet set: valueSets)
		{
			if (set.getMax() > highest)
			{
				highest = set.getMax();
			}
		}
		return highest;
	}

	public double getValueAtPercentage(double p)
	{
		double index = MathUtils.lerp(p, 0, mCount - 1);
		int prev = MathUtils.floorToInt(index);
		int next = (prev < mCount ? prev + 1 : prev);
		double result = MathUtils.lerp(index - prev, mValues[prev], mValues[next]);
		return result;
	}
}
