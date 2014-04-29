package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public class BPHeuristicSwitchItemFromLeastFullBin extends BPPerturbativeHeuristic
{

	@Override
	public BPPartialSolution run(
			BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();
		ArrayList<Integer> weights = current.getBpInstance().getItemSizes();

		for (int k = 0; k < numberOfAssigments; k++)
		{
		
			BPBin leastFullBin = pickLeastFullBin(current.getBins());
			int itemToSwitch = selectRandomItemFromBin(leastFullBin);
			
			for (BPBin bin : current.getBins())
			{
				if (bin != leastFullBin)
				{
					for (int item2 : bin.getItemIndexes())
					{
						if (weights.get(item2) < weights.get(itemToSwitch) 
								&& bin.getWeight() - weights.get(item2) + weights.get(itemToSwitch) < current.getBpInstance().getBinCapacity())
						{
							bin.getItemIndexes().remove((Integer) item2);
							leastFullBin.getItemIndexes().remove((Integer) itemToSwitch);
							bin.getItemIndexes().add(itemToSwitch);
							leastFullBin.getItemIndexes().add(item2);
							return current;
						}
					}
				}
			}
			
			for (BPBin bin : current.getBins())
			{
				if (bin != leastFullBin)
				{
					for (int item2 : bin.getItemIndexes())
					{
						for (int item3 : bin.getItemIndexes())
						{
							if (item2 != item3 
									&& weights.get(item2) + weights.get(item3) < weights.get(itemToSwitch) 
									&& bin.getWeight() - weights.get(item2) - weights.get(item3) + weights.get(itemToSwitch) < current.getBpInstance().getBinCapacity())
							{
								bin.getItemIndexes().remove((Integer) item2);
								bin.getItemIndexes().remove((Integer) item3);
								leastFullBin.getItemIndexes().remove((Integer) itemToSwitch);
								bin.getItemIndexes().add(itemToSwitch);
								leastFullBin.getItemIndexes().add(item2);
								leastFullBin.getItemIndexes().add(item3);
								return current;
								
							}
						}
					}
				}
			}
			
		}
		
		return current;
	}

	private Integer selectRandomItemFromBin(BPBin bin)
	{
		Random r = new Random();
		int item = r.nextInt(bin.getItemIndexes().size());
		int itemIndex = bin.getItemIndexes().get(item);
		return itemIndex;
	}
	
	private BPBin pickLeastFullBin(ArrayList<BPBin> bins)
	{
		BPBin leastFullBin = bins.get(0);
		for (BPBin bin : bins)
		{
			if (bin.getWeight() < leastFullBin.getWeight())
			{
				leastFullBin = bin;
			}
		}
		return leastFullBin;
	}

}
