package br.unicamp.fee.dca.hyperlabexamples.binpacking;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.binpacking.constructive.BPHeuristicBestFit;

public class BPPartialSolution extends PartialSolution
{
	protected BPInstance bpInstance;
	protected ArrayList<BPBin> bins;
	protected ArrayList<Integer> unallocatedItemIndexes;
	
	public BPPartialSolution(BPInstance bpInstance)
	{
		this.bpInstance = bpInstance;
		this.bins = new ArrayList<BPBin>();
		this.unallocatedItemIndexes = new ArrayList<Integer>();
		
		for (int i = 0; i < this.bpInstance.getNumberOfItems(); i++)
		{
			this.unallocatedItemIndexes.add(i);
		}
	}
	
	public PartialSolution copy()
	{
		@SuppressWarnings("unchecked")
		ArrayList<Integer> unallocatedCopy = (ArrayList<Integer>) this.getUnallocatedItemIndexes().clone();
		
		ArrayList<BPBin> binsCopy = new ArrayList<BPBin>();
		for (BPBin bin : this.getBins())
		{
			binsCopy.add(bin.copy());
		}
		
		BPPartialSolution bpSol = new BPPartialSolution(this.getBpInstance(), binsCopy, unallocatedCopy);
		
		return bpSol;
	}
	
	public BPPartialSolution(BPInstance bpInstance, ArrayList<BPBin> bins, ArrayList<Integer> unallocatedItemIndexes)
	{
		this.bpInstance = bpInstance;
		this.bins = bins;
		this.unallocatedItemIndexes = unallocatedItemIndexes;
	}

	public BPInstance getBpInstance()
	{
		return bpInstance;
	}

	public ArrayList<BPBin> getBins()
	{
		return bins;
	}

	public ArrayList<Integer> getUnallocatedItemIndexes()
	{
		return unallocatedItemIndexes;
	}
	
	@Override
	public double getCost()
	{
		/*
		int acc = 0;
		for (BPBin bin : this.getBins())
		{
			acc += (this.getBpInstance().getBinCapacity() - bin.getWeight());
		}
		return acc;
		
		*/
		
		return this.getBins().size();
		
		/*
		double fit = 1;
		int capacity = this.getBpInstance().getBinCapacity();
		for (BPBin bin : this.getBins())
		{
			fit += (bin.getWeight()/capacity)*(bin.getWeight()/capacity);
		}
		fit = fit / this.getBins().size();
		return fit;
		*/
		
	}

	@Override
	public boolean isComplete()
	{
		if (this.getUnallocatedItemIndexes().isEmpty())
		{
			return true;
		}
		return false;
	}

	public void generateRandomSolution()
	{
		BPHeuristicBestFit bestFit = new BPHeuristicBestFit();
		BPPartialSolution ps = bestFit.run(this, this.unallocatedItemIndexes.size());
		this.bins = ps.bins;
		this.unallocatedItemIndexes = ps.unallocatedItemIndexes;
		this.bpInstance = ps.bpInstance;
	}

	@Override
	public int getInstanceSize()
	{
		return this.getBpInstance().getNumberOfItems();
	}
	
	
}
