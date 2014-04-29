package br.unicamp.fee.dca.hyperlab.util;

public class Timer
{
	static final long NANOSECONDS_IN_A_SECOND = 1000000000;
	private long startTime;
	private long endTime;
	private double timeElapsed;
	private boolean timeCalculated;
	
	public Timer()
	{
		startTime = System.nanoTime();
		timeCalculated = false;
	}
	
	public void stop()
	{
		endTime = System.nanoTime();
	}
	
	public double getElapsedTime()
	{
		if (!timeCalculated)
		{
			timeElapsed = (((double)endTime) - startTime) / NANOSECONDS_IN_A_SECOND;
			timeCalculated = true;
		}
		else
		{
			long presentTime = System.nanoTime();
			timeElapsed = (((double)presentTime) - startTime) / NANOSECONDS_IN_A_SECOND;
		}
		return timeElapsed;
	}
	
	public double stopAndGetElapsedTime()
	{
		stop();
		return getElapsedTime();
	}
}
