package br.unicamp.fee.dca.hyperlab;

import java.io.PrintWriter;
import java.util.List;

public abstract class HyperHeuristic
{
	private String logFileName;
	private String name;

	public HyperHeuristic() {
		this.name = null;
	}

	public HyperHeuristic(String name) {
		this.name = name;
	}
	
	public abstract <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssignmentsPerHeuristic);
	
	protected <T extends PartialSolution> void printHeuristicsToFile(List<BaseHeuristic<T>> hs)
	{
		if (logFileName == null)
		{
			return;
		}
		
		try
		{
			PrintWriter writer = new PrintWriter(logFileName + ".txt", "UTF-8");
			
			for (BaseHeuristic<T> h : hs)
			{
				writer.println(h.getClass());
			}
			
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setLogFileName(String logFileName)
	{
		this.logFileName = logFileName; 
	}

	public String getName()
	{
		return (name != null ? name : getClass().getSimpleName());
	}
}
