package br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public class BPHeuristicFirstFitDecreasing extends BPConstructiveHeuristic
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
			int maxIndex = unallocatedIndexes.get(0);
			for (Integer index : unallocatedIndexes)
			{
				if (weights.get(index) > weights.get(maxIndex))
				{
					maxIndex = index;
				}
			}
			boolean allocated = false;
			for (BPBin bin : current.getBins())
			{
				if (weights.get(maxIndex) + bin.getWeight() <= binCapacity)
				{
					bin.addItem(maxIndex);
					allocated = true;
					break;
				}
			}
			if (!allocated)
			{
				BPBin bin = new BPBin(current.getBpInstance());
				bin.addItem(maxIndex);
				current.getBins().add(bin);
			}
			unallocatedIndexes.remove((Integer) maxIndex);
			assigned++;
		}
		
		return current;
	}

}