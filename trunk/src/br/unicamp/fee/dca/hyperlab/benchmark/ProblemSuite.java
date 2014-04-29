package br.unicamp.fee.dca.hyperlab.benchmark;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class ProblemSuite
{
	List<Problem<?>> problems;
	
	public ProblemSuite()
	{
		this.problems = new ArrayList<Problem<?>>();
	}
	
	public <T extends PartialSolution> void add(Problem<T> problem)
	{
		this.problems.add(problem);
	}
	
	public List<Problem<?>> getProblems()
	{
		return problems;
	}
}
