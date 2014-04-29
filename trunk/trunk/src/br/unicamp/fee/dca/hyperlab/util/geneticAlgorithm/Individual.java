package br.unicamp.fee.dca.hyperlab.util.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class Individual<T extends PartialSolution>
{
	private ArrayList<BaseHeuristic<T>> genes;
	private T current;
	private BaseHeuristic<T>[] heuristicsList;
	private int numberOfAssigmentsPerHeuristic;

	public ArrayList<BaseHeuristic<T>> getGenes() {
		return genes;
	}
	
	public Individual(T current, BaseHeuristic<T>[] heuristicsList, int numberOfAssigmentsPerHeuristic)
	{
		this.current = current;
		this.heuristicsList = heuristicsList;
		this.numberOfAssigmentsPerHeuristic = numberOfAssigmentsPerHeuristic;
	}

	public void setGenes(ArrayList<BaseHeuristic<T>> genes) {
		this.genes = genes;
	}
	
	public void initializeRandomly(int numberOfGenes)
	{
		this.genes = createRandomList(heuristicsList, numberOfGenes);
	}
	
	private ArrayList<BaseHeuristic<T>> createRandomList(BaseHeuristic<T>[] heuristics, int listSize)
	{
		Random r = new Random();
		int n = heuristics.length;
		ArrayList<BaseHeuristic<T>> newList = new ArrayList<BaseHeuristic<T>>();
		for (int i = 0; i < listSize; i++)
		{
			int a = r.nextInt(n);
			newList.add(heuristics[a]);
		}
		return newList;
	}
	
	public T getPartialSolution()
	{
		for (int i = 0; i < this.genes.size(); i++)
		{
			current = this.genes.get(i).run(current, numberOfAssigmentsPerHeuristic);
		}
		return current;
	}
	
	public double getCost()
	{
		this.getPartialSolution();
		return current.getCost();
	}
	
	public Individual<T> reproduce(Individual<T> otherParent)
	{
		Random r = new Random();
		int n = otherParent.genes.size();
		int a = r.nextInt(n);
		int b = r.nextInt(n);
		while (b == a)
		{
			b = r.nextInt(n);
		}
		if (b < a)
		{
			int c = a;
			a = b;
			b = c;
		}
		
		ArrayList<BaseHeuristic<T>> childGenes = new ArrayList<BaseHeuristic<T>>();
		
		for (int i = 0; i < n; i++)
		{
			if (i > a && i < b)
			{
				childGenes.add(otherParent.genes.get(i));
			}
			else
			{
				childGenes.add(this.genes.get(i));
			}
		}
		
		Individual<T> child = new Individual<T>(this.current, this.heuristicsList, this.numberOfAssigmentsPerHeuristic);
		child.setGenes(childGenes);
		return child;
	}
	
	public void mutate(double mutation_rate)
	{
		Random r = new Random();
		for (int i = 0; i < this.genes.size(); i++)
		{
			double a = r.nextDouble();
			if (a < mutation_rate)
			{
				int b = r.nextInt(heuristicsList.length);
				while (this.genes.get(i) == heuristicsList[b])
				{
					b = r.nextInt(heuristicsList.length);
				}
				this.genes.set(i, heuristicsList[b]);
					
			}
		}
	}
	
	
}
