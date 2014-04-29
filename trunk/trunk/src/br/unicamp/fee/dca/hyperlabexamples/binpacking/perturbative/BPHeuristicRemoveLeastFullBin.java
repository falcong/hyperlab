package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive.BPHeuristicBestFit;

public class BPHeuristicRemoveLeastFullBin extends BPPerturbativeHeuristic
{

	@Override
	public BPPartialSolution run(
			BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();

		for (int k = 0; k < numberOfAssigments; k++)
		{
		
			BPBin bin = pickLeastFullBin(current.getBins());
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
