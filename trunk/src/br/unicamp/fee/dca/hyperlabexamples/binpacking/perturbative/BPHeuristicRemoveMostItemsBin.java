package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive.BPHeuristicBestFit;

public class BPHeuristicRemoveMostItemsBin extends BPPerturbativeHeuristic
{

	@Override
	public BPPartialSolution run(
			BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();

		for (int k = 0; k < numberOfAssigments; k++)
		{
		
			BPBin bin = pickMostItemsUnfilledBin(current.getBins(), current.getBpInstance().getBinCapacity());
			for (int i : bin.getItemIndexes())
			{
				current.getUnallocatedItemIndexes().add(i);
			}
			current.getBins().remove(bin);		
		}
		
		int n = current.getUnallocatedItemIndexes().size();
		BPHeuristicBestFit bestFit = new BPHeuristicBestFit();
		
		current = bestFit.run(current, n);
		
		return current;
	}

	private BPBin pickMostItemsUnfilledBin(ArrayList<BPBin> bins, int capacity)
	{
		BPBin pickedBin = bins.get(0);
		for (BPBin bin : bins)
		{
			if (bin.getWeight() < capacity)
			{
				pickedBin = bin;
			}
		}
		for (BPBin bin : bins)
		{
			if (bin.getWeight() < capacity && bin.getItemIndexes().size() > pickedBin.getItemIndexes().size())
			{
				pickedBin = bin;
			}
		}
		return pickedBin;
	}

}
