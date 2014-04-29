package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.tabuSearchUtil;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlab.util.tabusearch.Movement;

public class ConstructiveMovement extends Movement
{
	private ArrayList<HeuristicChange> heuristicChanges;
	
	public ConstructiveMovement(ArrayList<HeuristicChange> HeuristicChanges)
	{
		this.heuristicChanges = HeuristicChanges;
	}

	public ArrayList<HeuristicChange> getHeuristicChanges() 
	{
		return heuristicChanges;
	}
	
	@Override
	public boolean breaksTabu(Movement tabu)
	{
		if (tabu instanceof ConstructiveMovement)
		{
			ConstructiveMovement tsppm = (ConstructiveMovement) tabu;
			for (HeuristicChange myChange: heuristicChanges)
			{
				for (HeuristicChange tabuChange: tsppm.getHeuristicChanges())
				{
//					String print2 = "check if my " + myChange + " breaks " + tabuChange;
					if (myChange.breaksTabu(tabuChange))
					{
//						System.out.println("YES");
//						System.out.println(print1);
//						System.out.println(print2);
						return true;
					}
				}
			}
		}
		
		//System.out.println("nope");
		return false;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(|");
		sb.append(heuristicChanges.size());
		sb.append("|");
		for (HeuristicChange cc: heuristicChanges)
		{
			sb.append(cc.toString());
			sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}
}
