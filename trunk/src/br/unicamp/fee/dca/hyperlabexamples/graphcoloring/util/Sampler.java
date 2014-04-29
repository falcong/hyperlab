package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.util;

import java.util.ArrayList;
import java.util.Collections;

public class Sampler {
	private ArrayList<Integer> numbers =  new ArrayList<Integer>();
	
	public Sampler(int n){
		for (int i = 0; i < n; i++)
		{
			numbers.add(i);
		}
		Collections.shuffle(numbers);
	}
	
	public int getNextSample(){
		int number = numbers.remove(0);
		return number;
	}
	
}
