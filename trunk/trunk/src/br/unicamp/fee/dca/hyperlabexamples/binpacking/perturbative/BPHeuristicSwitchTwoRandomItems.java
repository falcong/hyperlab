package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPInstance;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;

public class BPHeuristicSwitchTwoRandomItems extends BPPerturbativeHeuristic
{

	@Override
	public BPPartialSolution run(BPPartialSolution current, int numberOfAssigments)
	{
		for (int i = 0; i < numberOfAssigments; i++)
		{
			current = run2(current, numberOfAssigments);
		}
		return current;
	}
	
	
	private BPPartialSolution run2(BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();
		BPInstance instance = current.getBpInstance();
		ArrayList<BPBin> binsNotFull = getBinsNotFull(current.getBins(), instance.getBinCapacity());
		ArrayList<Integer> itemWeights = instance.getItemSizes();
		
		BPBin bin1 = selectRandomBin(binsNotFull);
		int item1 = selectRandomItemFromBin(bin1);
	
		for (BPBin bin2 : binsNotFull)
		{
			if (!bin1.equals(bin2))
			{
				for (Integer item2 : bin2.getItemIndexes())
				{
					if (bin1.getWeight() + itemWeights.get(item2) - itemWeights.get(item1) <= instance.getBinCapacity()
							&&
							bin2.getWeight() + itemWeights.get(item1) - itemWeights.get(item2) <= instance.getBinCapacity())
					{
						bin1.addItem(item2);
						bin1.getItemIndexes().remove((Integer) item1);
						bin2.addItem(item1);
						bin2.getItemIndexes().remove((Integer) item2);
						return current;
					}
				}
			}
		}
		return current;
	}
	
	private BPBin selectRandomBin(ArrayList<BPBin> bins)
	{
		int size = bins.size();
		Random r = new Random();
		int binIndex = r.nextInt(size);
		BPBin bin = bins.get(binIndex);
		return bin;
	}
	
	private Integer selectRandomItemFromBin(BPBin bin)
	{
		Random r = new Random();
		int item = r.nextInt(bin.getItemIndexes().size());
		int itemIndex = bin.getItemIndexes().get(item);
		return itemIndex;
	}

	private ArrayList<BPBin> getBinsNotFull(ArrayList<BPBin> bins, int capacity)
	{
		@SuppressWarnings("unchecked")
		ArrayList<BPBin> binsNotFull = (ArrayList<BPBin>) bins.clone();
		for (BPBin bin : bins)
		{
			if (bin.getWeight() >= capacity)
			{
				binsNotFull.remove(bin);
			}
		}
		return binsNotFull;
	}

	

}
