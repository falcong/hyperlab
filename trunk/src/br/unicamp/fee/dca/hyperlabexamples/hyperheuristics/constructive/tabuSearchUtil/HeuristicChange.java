package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.tabuSearchUtil;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;

public class HeuristicChange 
{
	int position;
	BaseHeuristic<?> heuristicRemoved;
	BaseHeuristic<?> newHeuristic;
	
	public HeuristicChange(int position, BaseHeuristic<?> heuristicRemoved, BaseHeuristic<?> newHeuristic) 
	{
		super();
		this.heuristicRemoved = heuristicRemoved;
		this.position = position;
		this.newHeuristic = newHeuristic;
	}

	public int getPosition() {
		return position;
	}

	public BaseHeuristic<?> getHeuristicRemoved() {
		return heuristicRemoved;
	}

	public BaseHeuristic<?> getNewHeuristic() {
		return newHeuristic;
	}
	
	public static <T> ArrayList<HeuristicChange> generateHeuristicChanges(ArrayList<BaseHeuristic<T>> oldList, ArrayList<BaseHeuristic<T>> newList)
	{
		ArrayList<HeuristicChange> moves = new ArrayList<HeuristicChange>();
		int n = oldList.size();
		for (int i = 0; i < n; i++)
		{
			if (oldList.get(i) != newList.get(i))
			{
				HeuristicChange move = new HeuristicChange(i, oldList.get(i), newList.get(i));
				moves.add(move);
			}
		}
		return moves;
	}
	
	public boolean breaksTabu(HeuristicChange tabu)
	{
		return (this.newHeuristic == tabu.heuristicRemoved
				&& this.position == tabu.position);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(position);
		sb.append("]");
		sb.append(heuristicRemoved.toString());
		sb.append("->");
		sb.append(newHeuristic.toString());
		return sb.toString();
	}
}
