package br.unicamp.fee.dca.hyperlabexamples.binpacking.perturbative;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPBin;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.BPPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive.BPHeuristicBestFit;

public class BPHeuristicRemoveRandomUnfilledBin extends BPPerturbativeHeuristic
{

	@Override
	public BPPartialSolution run(
			BPPartialSolution current, int numberOfAssigments)
	{
		current = (BPPartialSolution) current.copy();

		for (int k = 0; k < numberOfAssigments; k++)
		{
			ArrayList<BPBin> bins = getBinsNotFull(current.getBins(), current.getBpInstance().getBinCapacity());
			BPBin bin = selectRandomBin(bins);
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
