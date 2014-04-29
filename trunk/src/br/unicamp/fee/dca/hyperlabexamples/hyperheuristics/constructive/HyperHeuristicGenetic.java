package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive;

import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlab.util.geneticAlgorithm.Individual;
import br.unicamp.fee.dca.hyperlab.util.geneticAlgorithm.Population;

public class HyperHeuristicGenetic extends HyperHeuristic
{
	private int iterations;
	private int parentsPopulationSize;
	private int offspringPopulationSize;
	private int i;
	private SelectionMethod selectionMethod = SelectionMethod.ROULETTE;
	private int tournamentSize = 2;
	private PopulationType popType = PopulationType.MU_PLUS_LAMBDA;
	private double mutation_rate = 0.0001;
	
	public enum SelectionMethod
	{ROULETTE, TOURNAMENT_WITH_REPETITION, TOURNAMENT_WITHOUT_REPETITION};
	
	public enum PopulationType
	{MU_PLUS_LAMBDA, MU_LAMBDA};

	public HyperHeuristicGenetic(int iterations, int parentsPopulationSize, int offspringPopulationSize)
	{
		this.iterations = iterations;
		this.parentsPopulationSize = parentsPopulationSize;
		this.offspringPopulationSize = offspringPopulationSize;
	}
	
	public void setSelectionMethod(SelectionMethod method)
	{
		this.selectionMethod = method;
	}
	
	public void setTournamentSize(int tournamentSize) 
	{
		this.tournamentSize = tournamentSize;
	}

	public void setPopType(PopulationType popType) {
		this.popType = popType;
	}

	public int getLastIteration()
	{
		return i;
	}
	
	public double getMutation_rate() {
		return mutation_rate;
	}

	public void setMutation_rate(double mutation_rate) {
		this.mutation_rate = mutation_rate;
	}

	@Override
	public <T extends PartialSolution> T run(BaseHeuristic<T>[] heuristics, T current, int numberOfAssigmentsPerHeuristic) 
	{
		Population<T> pop = new Population<T>(heuristics, current, numberOfAssigmentsPerHeuristic);
		pop.initializeRandomly(parentsPopulationSize);
		Individual<T> best = null;
		ArrayList<Double> costs;
		for (int iteration = 0; iteration < iterations; iteration++)
		{
			pop = pop.reproduce(popType, offspringPopulationSize);
			costs = pop.getPopulationCost();
			
			if(best == null || best.getCost() > pop.pickBest(costs).getCost())
			{
				best = pop.pickBest(costs);
			}
			
			pop = pop.selection(costs, selectionMethod, parentsPopulationSize, tournamentSize);
			pop.mutate(mutation_rate);
		}
		return best.getPartialSolution();
	}


	
	

	

	
}