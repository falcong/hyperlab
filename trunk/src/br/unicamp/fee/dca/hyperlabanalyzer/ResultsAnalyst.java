package br.unicamp.fee.dca.hyperlabanalyzer;

import br.unicamp.fee.dca.hyperlab.benchmark.Results;
import br.unicamp.fee.dca.hyperlab.benchmark.Results.RunResult;

public class ResultsAnalyst
{
	private Results mResults;
	private GenericBenchmark[] mProblemBenchmarks;
	private GenericBenchmark[] mHyperHeuristicBenchmark;

	public ResultsAnalyst(Results results)
	{
		mResults = results;
		mProblemBenchmarks = new GenericBenchmark[results.getProblemNumber()];
		for (int p = 0; p < mResults.getProblemNumber(); p++)
		{
			mProblemBenchmarks[p] = new GenericBenchmark(results.getHyperHeuristicNumber());
		}
		
		mHyperHeuristicBenchmark = new GenericBenchmark[results.getHyperHeuristicNumber()];
		for (int hh = 0; hh < mResults.getHyperHeuristicNumber(); hh++)
		{
			mHyperHeuristicBenchmark[hh] = new GenericBenchmark(results.getProblemNumber());
		}
		
		digestAllProblemInstances();
	}

	private void digestAllProblemInstances()
	{
		for (int hh = 0; hh < mResults.getHyperHeuristicNumber(); hh++)
		{
			for (int p = 0; p < mResults.getProblemNumber(); p++)
			{
				digestProblemInstances(mResults.getRunResults(hh, p), hh, p);
			}
		}
	}

	private void digestProblemInstances(RunResult[] runResults, int hh, int p)
	{
		int instanceCount = runResults.length;
		
		double[] costs = new double[instanceCount];
		double[] times = new double[instanceCount];
		for (int i = 0; i < instanceCount; i++)
		{
			RunResult rr = runResults[i];
			costs[i] += rr.cost;
			times[i] += rr.timeElapsed;
		}
		ValueSet costValues = new ValueSet(costs);
		ValueSet timeValues = new ValueSet(times);
//		System.out.println("costValues " + costValues.getSummaryString());
//		System.out.println("timeValues " + timeValues.getSummaryString());
		mProblemBenchmarks[p].addCostAndTimeValues(hh, costValues, timeValues);
		mHyperHeuristicBenchmark[hh].addCostAndTimeValues(p, costValues, timeValues);
	}

	public Results getResults()
	{
		return mResults;
	}

	public GenericBenchmark[] getProblemBenchmarks()
	{
		return mProblemBenchmarks;
	}

	public GenericBenchmark[] getHyperHeuristicBenchmark()
	{
		return mHyperHeuristicBenchmark;
	}
}
