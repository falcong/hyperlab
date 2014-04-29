package br.unicamp.fee.dca.hyperlabexamples.binpacking;

import java.util.ArrayList;

public class BPBin
{
	private ArrayList<Integer> itemIndexes;
	private BPInstance instance;
	
	public BPBin(BPInstance instance)
	{
		itemIndexes = new ArrayList<Integer>();
		this.instance = instance;
	}

	public ArrayList<Integer> getItemIndexes()
	{
		return itemIndexes;
	}
	
	public int getWeight()
	{
		int acc = 0;
		for (Integer i : itemIndexes)
		{
			acc = acc + this.instance.getItemSizes().get(i);
		}
		return acc;
	}
	
	public void addItem(int itemIndex)
	{
		itemIndexes.add(itemIndex);
	}
	
	public String toString()
	{
		String str = "(";
		str = str + this.getWeight();
		str = str + ") items: ";
		for (Integer i : itemIndexes)
		{
			str = str + i + "(" + this.instance.getItemSizes().get(i) + ") ";
		}
		return str;
	}
	
	@SuppressWarnings("unchecked")
	public BPBin copy()
	{
		BPBin newbin = new BPBin(this.instance);
		newbin.itemIndexes = (ArrayList<Integer>) this.itemIndexes.clone();
		return newbin;
	}
}
