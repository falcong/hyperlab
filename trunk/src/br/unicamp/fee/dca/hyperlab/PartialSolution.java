package br.unicamp.fee.dca.hyperlab;

public abstract class PartialSolution
{
	public abstract double getCost();
	public abstract boolean isComplete();
	public abstract PartialSolution copy();
	public abstract int getInstanceSize();
}
