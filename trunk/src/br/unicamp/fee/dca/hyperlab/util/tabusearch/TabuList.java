package br.unicamp.fee.dca.hyperlab.util.tabusearch;

import java.util.LinkedList;

import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class TabuList<T extends PartialSolution> {
	private LinkedList<Movement> tabuList;
	private int size;
	private LinkedList<Integer> iterationList;
	private int currentIteration;
	
	public TabuList(int tabuListSize)
	{
		tabuList = new LinkedList<Movement>();
		iterationList = new LinkedList<Integer>();
		this.size = tabuListSize;
	}
	
	public int getTabuListSize()
	{
		return this.size;
	}
	
	public void add(Movement movement, int iteration)
	{
		tabuList.add(movement);
		iterationList.add(iteration);
	}
	
	public void iterationPassed()
	{
		while (tabuList.size() > 0 && iterationList.get(0) <= (currentIteration - size))
		{
			iterationList.remove(0);
			tabuList.remove(0);
		}
		currentIteration++;
	}
	
	public boolean isTabu(Movement movement)
	{
		for (int i = 0; i < tabuList.size(); i++)
		{
			if (movement.breaksTabu(tabuList.get(i)))
			{
				return true;
			}
		}
		return false;
	}
}
