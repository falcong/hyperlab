package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPInstance;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;


/* Looks for up to three items to fill completely a random bin*/
public class BPHeuristicSearchForItemToFillABin extends BPPerturbativeHeuristic
{

	@Override
	public BPPartialSolution run(
			BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();
		ArrayList<Integer> weights = current.getBpInstance().getItemSizes();

		for (int k = 0; k < numberOfAssigments; k++)
		{
			BPInstance instance = current.getBpInstance();
			ArrayList<BPBin> binsNotFull = getBinsNotFull(current.getBins(), instance.getBinCapacity());
			ArrayList<Integer> itemWeights = instance.getItemSizes();
			int capacity = instance.getBinCapacity();
			
			BPBin binToFill = selectRandomBin(binsNotFull);
			
			int missing = capacity - binToFill.getWeight();
			
			for (BPBin bin : current.getBins())
			{
				if (bin != binToFill)
				{
					for (int item : bin.getItemIndexes())
					{
						if (itemWeights.get(item) == missing)
						{
							bin.getItemIndexes().remove((Integer) item);
							binToFill.getItemIndexes().add(item);
							return current;
						}
					}
				}
			}
			
			for (BPBin bin1 : current.getBins())
			{
				if (bin1 != binToFill)
				{
					for (int item1 : bin1.getItemIndexes())
					{
						for (BPBin bin2 : current.getBins())
						{
							for (int item2 : bin2.getItemIndexes())
							{
								if (item1 != item2
										&& weights.get(item1) + weights.get(item2) == missing) 
								{
									bin1.getItemIndexes().remove((Integer) item1);
									bin2.getItemIndexes().remove((Integer) item2);
									binToFill.getItemIndexes().add(item1);
									binToFill.getItemIndexes().add(item2);
									return current;
								}
							}
						}
					}
				}
			}
			
			for (BPBin bin1 : current.getBins())
			{
				if (bin1 != binToFill)
				{
					for (int item1 : bin1.getItemIndexes())
					{
						for (BPBin bin2 : current.getBins())
						{
							for (int item2 : bin2.getItemIndexes())
							{
								for (BPBin bin3 : current.getBins())
								{
									for (int item3 : bin3.getItemIndexes())
									{
										if (item2 != item3 
												&& item1 != item2
												&& item1 != item3
												&& weights.get(item1) + weights.get(item2) + weights.get(item3) == missing) 
										{
											bin1.getItemIndexes().remove((Integer) item1);
											bin2.getItemIndexes().remove((Integer) item2);
											bin3.getItemIndexes().remove((Integer) item3);
											binToFill.getItemIndexes().add(item1);
											binToFill.getItemIndexes().add(item2);
											binToFill.getItemIndexes().add(item3);
											return current;
										}
									}
								}
							}
						}
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