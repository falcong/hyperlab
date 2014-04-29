package br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public class BPHeuristicNextFit extends BPConstructiveHeuristic
{

	@Override
	public BPPartialSolution run(BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();
		int binCapacity = current.getBpInstance().getBinCapacity();
		ArrayList<Integer> unallocatedIndexes = current.getUnallocatedItemIndexes();
		ArrayList<Integer> weights = current.getBpInstance().getItemSizes();
		
		int assigned = 0;
		while (assigned < numberOfAssigments && !unallocatedIndexes.isEmpty())
		{
			int nextItemIndex = unallocatedIndexes.get(0);
			int weight = weights.get(nextItemIndex);
			boolean allocated = false;
			for (BPBin bin : current.getBins())
			{
				if (bin.getWeight() + weight <= binCapacity)
				{
					bin.addItem(nextItemIndex);
					allocated = true;
					break;
				}
			}
			if (!allocated)
			{
				BPBin bin = new BPBin(current.getBpInstance());
				bin.addItem(nextItemIndex);
				current.getBins().add(bin);
			}
			unallocatedIndexes.remove((Integer) nextItemIndex);
			assigned++;
		}
		
		return current;
	}

}