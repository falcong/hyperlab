package br.unicamp.fee.dca.hyperlabanalyzer;

public class MathUtils
{
	private MathUtils()
	{
	}

	public static double lerp(double t, double a, double b)
	{
		return a * (1.0 - t) + b * (t);
	}
	
	public static double inverseLerp(double v, double a, double b)
	{
		return (v - a) / (b - a);
	}

	public static int floorToInt(double v)
	{
		return (int) Math.floor(v);
	}

	public static int ceilToInt(double v)
	{
		return (int) Math.ceil(v);
	}
}
