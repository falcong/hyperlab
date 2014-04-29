package br.unicamp.fee.dca.hyperlabexamples.tsp.util;

public class LoopingIterator {

	private int intervalStart;
	private int intervalEnd;
	private int current;
	private int counter = 0;
	
	public LoopingIterator(int intervalStart, int intervalEnd, int currentNumber){
		this.intervalEnd = intervalEnd;
		this.intervalStart = intervalStart;
		this.current = currentNumber;
		if (this.current < intervalStart)
			this.current = intervalStart;
		if (this.current > intervalEnd)
			this.current = intervalStart;
	}
	
	public int getNext(){
		counter++;
		current++;
		if (current > intervalEnd){
			current = intervalStart;
		}
		return current;
	}
	
	public boolean isOver(){
		if ((counter >= (intervalEnd - intervalStart)) && (counter > 0)){
			return true;
		}
		return false;
	}
	
	public void setNewStartingNumber(int currentNumber){
		this.current = currentNumber;
	}
	
	public void reset(int intervalStart, int intervalEnd, int currentNumber){
		this.intervalEnd = intervalEnd;
		this.intervalStart = intervalStart;
		this.current = currentNumber;
		this.counter = 0;

		if (this.current < intervalStart)
			this.current = intervalStart;
		if (this.current > intervalEnd)
			this.current = intervalStart;
	}
}
