package br.unicamp.fee.dca.hyperlabexamples.graphcoloring;

import java.util.Random;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.util.Sampler;

public class GCInstance {
	private Graph graph;
	private String name;
	
	public GCInstance()
	{
		this.graph = new Graph();
	}
	
	
	public Graph getGraph() {
		return graph;
	}

	public void generateRandomGraph(int n)
	{
		graph = new Graph();
		Random r = new Random();
		for (int i = 0; i < n; i++)
		{
			Node newNode = new Node();
			newNode.id = i;
			Sampler s = new Sampler(i);
			newNode.weight = r.nextInt(n*10);
			graph.addNode(newNode);
			
			int numEdges;
			if (i > 1)
			{
				numEdges = r.nextInt(Math.min(i-1, 2)) + 1;
			}
			else if (i == 1)
			{
				numEdges = 1;
			}
			else
			{
				numEdges = 0;
			}
			
			for (int j = 0; j < numEdges; j++)
			{
				int neighbor = s.getNextSample();
				newNode.adjacents.add(neighbor);
				graph.getNode(neighbor).adjacents.add(i);
			}
			
		}
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				graph.getNode(i).possibleColors.add(j);
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		GCInstance gc = new GCInstance();
		gc.generateRandomGraph(20);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
}
