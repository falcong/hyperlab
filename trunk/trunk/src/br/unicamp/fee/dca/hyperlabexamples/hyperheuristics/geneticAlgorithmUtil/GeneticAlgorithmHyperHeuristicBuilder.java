package br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.geneticAlgorithmUtil;

import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic.PopulationType;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic.SelectionMethod;

public class GeneticAlgorithmHyperHeuristicBuilder
{
	private SelectionMethod selectionMethod = SelectionMethod.ROULETTE;
	private int tournamentSize = 2;
	private PopulationType popType = PopulationType.MU_PLUS_LAMBDA;
	private double mutationRate = 0.0001;
	private int iterations;
	private int parentsPopulationSize;
	private int offspringPopulationSize;
	
	public GeneticAlgorithmHyperHeuristicBuilder settournamentSize(int tournamentSize)
	{
		this.tournamentSize = tournamentSize;
		return this;
	}
	
	public GeneticAlgorithmHyperHeuristicBuilder setSelectionMethod(SelectionMethod selectionMethod)
	{
		this.selectionMethod = selectionMethod;
		return this;
	}
	
	public GeneticAlgorithmHyperHeuristicBuilder setPopulationType(PopulationType populationType)
	{
		this.popType = populationType;
		return this;
	}
	
	public GeneticAlgorithmHyperHeuristicBuilder setNumberOfIterations(int iterations)
	{
		this.iterations = iterations;
		return this;
	}
	
	public GeneticAlgorithmHyperHeuristicBuilder setParentsPopulationSize(int parentsPopulationSize)
	{
		this.parentsPopulationSize = parentsPopulationSize;
		return this;
	}
	
	public GeneticAlgorithmHyperHeuristicBuilder setOffspringPopulationSize(int offspringPopulationSize)
	{
		this.offspringPopulationSize = offspringPopulationSize;
		return this;
	}
	
	public GeneticAlgorithmHyperHeuristicBuilder setMutationRate(double mutationRate)
	{
		this.mutationRate = mutationRate;
		return this;
	}
	
	public HyperHeuristicGenetic build()
	{
		HyperHeuristicGenetic hhgen = new HyperHeuristicGenetic(iterations, parentsPopulationSize, offspringPopulationSize);
		hhgen.setMutation_rate(mutationRate);
		hhgen.setPopType(popType);
		hhgen.setSelectionMethod(selectionMethod);
		hhgen.setTournamentSize(tournamentSize);
		return hhgen;
	}
}
