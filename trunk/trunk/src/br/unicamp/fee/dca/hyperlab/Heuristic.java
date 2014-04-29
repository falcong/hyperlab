package br.unicamp.fee.dca.hyperlab;

public abstract class Heuristic<T> extends BaseHeuristic<T>
{
	
	public enum MovementAcceptance
	{FIRST_IMPROVEMENT, BEST_IMPROVEMENT, BEST_MOVE};
	
	/* 
	 * FIRST_IMPROVEMENT will return the first solution found that is better than the current one, 
	 * or the current one if it does not find any
	 * BEST_IMPROVEMENT will return the best solution found, even if it is the current one
	 * BEST_MOVE will return the best solution found, even if it is worse than the current one
	 */
	
	protected MovementAcceptance movementAcceptance = MovementAcceptance.FIRST_IMPROVEMENT;
	
	public Heuristic(MovementAcceptance movementAcceptance){
		this.movementAcceptance = movementAcceptance;
	}
	
	public Heuristic(){
	}
	
	public abstract T run(T current);
	
	public T run(T current, int numberOfAssignments)
	{
		for (int i = 0; i < numberOfAssignments; i++)
		{
			current = run(current);
		}
		return current;
	}
}
