package br.unicamp.fee.dca.hyperlab.util.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import br.unicamp.fee.dca.hyperlab.BaseHeuristic;
import br.unicamp.fee.dca.hyperlab.PartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic.PopulationType;
import br.unicamp.fee.dca.hyperlabexamples.hyperheuristics.constructive.HyperHeuristicGenetic.SelectionMethod;

public class Population<T extends PartialSolution>
{
	private ArrayList<Individual<T>> population;
	private int defaultTournamentSize = 2;
	private T current;
	private int numberOfAssigmentsPerHeuristic;
	private BaseHeuristic<T>[] heuristics;
	
	public Population(BaseHeuristic<T>[] heuristics, T current, int numberOfAssigmentsPerHeuristic)
	{
		this.heuristics = heuristics;
		this.current = current;
		this.numberOfAssigmentsPerHeuristic = numberOfAssigmentsPerHeuristic;
		this.population = new ArrayList<Individual<T>>();
	}
	
	public void initializeRandomly(int numberOfIndividuals)
	{
		int numberOfGenes = (int) Math.ceil(current.getInstanceSize()/(double)numberOfAssigmentsPerHeuristic);
		population = new ArrayList<Individual<T>>();
		for (int i = 0; i < numberOfIndividuals; i++)
		{
			Individual<T> ind = new Individual<T>(current, heuristics, numberOfAssigmentsPerHeuristic);
			ind.initializeRandomly(numberOfGenes);
			population.add(ind);
		}
	}
	
	public ArrayList<Double> getPopulationCost()
	{
		ArrayList<Double> costs = new ArrayList<Double>();
		for (int i = 0; i < population.size(); i++)
		{
			costs.add(population.get(i).getCost());
		}
		return costs;
	}
	
	public int getSize()
	{
		return population.size();
	}

	public Population<T> reproduce(PopulationType populationType, int offspringPopulationSize) {
		Population<T> offspring = new Population<T>(this.heuristics, this.current, this.numberOfAssigmentsPerHeuristic);
		int i = 0;
		Collections.shuffle(population);
		while (offspring.getSize() < offspringPopulationSize)
		{
			if (i >= population.size() - 1)
			{
				i = 0;
				Collections.shuffle(population);
			}
			offspring.addIndividual((population.get(i)).reproduce(population.get(i+1)));
			i = i + 2;
		}
		
		if (populationType == PopulationType.MU_LAMBDA)
		{
			return offspring;
		}
		else
		{
			population.addAll(offspring.population);
		}
		return this;
	}

	private void addIndividual(Individual<T> individual) 
	{
		this.population.add(individual);	
	}

	public Individual<T> pickBest(ArrayList<Double> costs) {
		int i = costs.indexOf(Collections.min(costs));
		return population.get(i);
	}
	
	public Population<T> selection(ArrayList<Double> costs, SelectionMethod selectionMethod, int populationSize)
	{
		return selection(costs, selectionMethod, defaultTournamentSize, populationSize);
	}

	public Population<T> selection(ArrayList<Double> costs, SelectionMethod selectionMethod, int populationSize, int tournamentSize) {
		if (selectionMethod == SelectionMethod.ROULETTE)
		{
			return this.roulette(costs, populationSize);
		}
		if (selectionMethod == SelectionMethod.TOURNAMENT_WITH_REPETITION)
		{
			return this.tournament_with_repetition(costs, selectionMethod, populationSize, tournamentSize);
		}
		if (selectionMethod == SelectionMethod.TOURNAMENT_WITHOUT_REPETITION)
		{
			return this.tournament_without_repetition(costs, selectionMethod, populationSize, tournamentSize);
		}
		return null;
	}
	
	private Population<T> roulette(ArrayList<Double> costs, int populationSize)
	{
		ArrayList<Double> sum = new ArrayList<Double>();
		ArrayList<Double> correctedSum = new ArrayList<Double>();
		sum.add((double) 0);
		for (int i = 0; i < costs.size(); i++)
		{
			sum.add(sum.get(i) + 1/costs.get(i));
		}
		for (int i = 0; i < sum.size(); i++)
		{
			correctedSum.add(sum.get(i)/sum.get(sum.size() - 1));
		}
		Random r = new Random();
		Population<T> selectedPopulation = new Population<T>(heuristics, current, numberOfAssigmentsPerHeuristic);
		for (int i = 0; i < populationSize; i++)
		{
			double a = r.nextDouble();
			for (int j = 0; j < correctedSum.size(); j++)
			{
				if (a < correctedSum.get(j+1))
				{
					selectedPopulation.addIndividual(population.get(j));
					break;
				}
			}
		}
		return selectedPopulation;
	}

	private Population<T> tournament_without_repetition(ArrayList<Double> costs, SelectionMethod selectionMethod, int populationSize, int tournamentSize) 
	{
		Collections.shuffle(population);
		Population<T> selectedPopulation = new Population<T>(heuristics, this.current, this.numberOfAssigmentsPerHeuristic);
		int counter = 0;
		for (int i = 0; i < populationSize; i++)
		{
			Individual<T> newIndividual = null;
			if (counter + tournamentSize >= population.size())
			{
				counter = 0;
				Collections.shuffle(population);
			}
			for (int j = counter; j < counter + tournamentSize; j++)
			{
				if (newIndividual == null)
				{
					newIndividual = population.get(j);
				}
				else
				{
					if(population.get(j).getCost() < newIndividual.getCost())
					{
						newIndividual = population.get(j);
					}
				}
			}
			selectedPopulation.addIndividual(newIndividual);
		}
		return selectedPopulation;
	}
	
	private Population<T> tournament_with_repetition(ArrayList<Double> costs, SelectionMethod selectionMethod, int populationSize, int tournamentSize) 
	{
		Population<T> selectedPopulation = new Population<T>(this.heuristics, this.current, this.numberOfAssigmentsPerHeuristic);
		Individual<T> newIndividual = null;
		Random r = new Random();
		for (int i = 0; i < populationSize; i++)
		{
			newIndividual = null;
			for (int j = 0; j < tournamentSize; j++)
			{
				int a = r.nextInt(population.size());
				if (newIndividual == null)
				{
					newIndividual = population.get(a);
				}
				else
				{
					if (population.get(a).getCost() < newIndividual.getCost())
					{
						newIndividual = population.get(a);
					}
				}
			}
			selectedPopulation.addIndividual(newIndividual);
		}
		return selectedPopulation;
	}

	public void mutate(double mutation_rate) 
	{
		for (int i = 0; i < population.size(); i++)
		{
			population.get(i).mutate(mutation_rate);
		}
	}
	
	
}
