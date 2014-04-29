package br.unicamp.fee.dca.hyperlab;

public abstract class BaseHeuristic<T>
{
	public BaseHeuristic(){
	}
	
	public abstract T run(T current, int numberOfAssigments);
	
	public abstract boolean isConstructive();
}
