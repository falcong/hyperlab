package br.unicamp.fee.dca.hyperlabexamples.graphcoloring;

import java.util.ArrayList;

public class Node {
	public int id;
	public int weight;
	public ArrayList<Integer> adjacents;
	public ArrayList<Integer> possibleColors;
	
	public Node()
	{
		adjacents = new ArrayList<Integer>();
		possibleColors = new ArrayList<Integer>();
	}
}
