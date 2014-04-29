package br.unicamp.fee.dca.hyperlabexamples.binpacking;

import java.util.ArrayList;
import java.util.Random;

public class BPInstance
{
	private int binCapacity;
	private int numberOfItems;
	private ArrayList<Integer> itemSizes;

	public int getBinCapacity()
	{
		return binCapacity;
	}

	public int getNumberOfItems()
	{
		return numberOfItems;
	}

	public ArrayList<Integer> getItemSizes()
	{
		return itemSizes;
	}

	public BPInstance createRandomInstance(int binCapacity, int numberOfItems)
	{
		BPInstance bpInst = new BPInstance();
		bpInst.binCapacity = binCapacity;
		bpInst.numberOfItems = numberOfItems;
		bpInst.itemSizes = new ArrayList<Integer>();
		Random r = new Random();
		bpInst.itemSizes.add(1);
		for (int i = 0; i < numberOfItems - 1; i++)
		{
			if (i < numberOfItems / 3)
			{
				bpInst.itemSizes.add(r.nextInt(binCapacity/3 - 1) + 1);
			}
			else if (i < 2 * numberOfItems / 3)
			{
				bpInst.itemSizes.add(r.nextInt(binCapacity/2 - 1) + 1);
			}
			else
			{
				bpInst.itemSizes.add(r.nextInt(binCapacity - 1) + 1);
			}
		}
		
		return bpInst;
	}
	
	
}