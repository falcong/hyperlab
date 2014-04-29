package br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public class BPHeuristicWorstFit extends BPConstructiveHeuristic
{

	public static Comparator<BPBin> BinComparator = new Comparator<BPBin>()
			{
				public int compare(BPBin bin1, BPBin bin2) 
				{
					return bin2.getWeight() - bin1.getWeight();
				}
			};
	
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
			Collections.sort(current.getBins(), BinComparator);
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