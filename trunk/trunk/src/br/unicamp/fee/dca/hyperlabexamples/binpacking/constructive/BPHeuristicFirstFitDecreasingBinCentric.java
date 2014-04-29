package br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public class BPHeuristicFirstFitDecreasingBinCentric extends BPConstructiveHeuristic
{

	@Override
	public BPPartialSolution run(BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();
		BPBin lastBin = null;
		if (current.getBins().isEmpty())
		{
			BPBin newBin = new BPBin(current.getBpInstance());
			current.getBins().add(newBin);
			lastBin = newBin;
		}
		else
		{
			int lastBinIndex = current.getBins().size() - 1;
			lastBin = current.getBins().get(lastBinIndex);
		}
		int binCapacity = current.getBpInstance().getBinCapacity();
		int maxSize = binCapacity - lastBin.getWeight();
		ArrayList<Integer> unallocatedIndexes = current.getUnallocatedItemIndexes();
		ArrayList<Integer> weights = current.getBpInstance().getItemSizes();
		
		int assigned = 0;
		while (assigned < numberOfAssigments && !unallocatedIndexes.isEmpty())
		{
			int bestIndex = -1;
			for (int i = 0; i < unallocatedIndexes.size(); i++)
			{
				if (weights.get(unallocatedIndexes.get(i)) <= maxSize)
				{
					if (bestIndex == -1 || weights.get(unallocatedIndexes.get(i)) > weights.get(bestIndex))
					{
						bestIndex = unallocatedIndexes.get(i);
					}
				}
			}
			if (bestIndex == -1)
			{
				BPBin newBin = new BPBin(current.getBpInstance());
				current.getBins().add(newBin);
				maxSize = binCapacity;
				lastBin = newBin;
			}
			else
			{
				lastBin.addItem(bestIndex);
				assigned++;
				maxSize = binCapacity - lastBin.getWeight();
				unallocatedIndexes.remove((Integer) bestIndex);
			}
		}
		
		return current;
	}

}