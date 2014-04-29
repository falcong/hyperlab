package br.unicamp.fee.dca.hyperlabexamples.tsp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;

import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class TSPPartialSolution extends PartialSolution
{
	protected int[] circuit;
	protected TSPInstance tspInstance;

	public TSPPartialSolution(TSPInstance tspInstance, int[] circuit)
	{
		this.tspInstance = tspInstance;
		this.circuit = circuit;
	}
	
	public TSPPartialSolution(TSPInstance tspInstance)
	{
		this.tspInstance = tspInstance;
		this.circuit = new int[0];
	}

	public TSPInstance getInstance()
	{
		return this.tspInstance;
	}
	
	public int[] getCurrentCircuit()
	{
		return circuit;
	}

	public void printSolution()
	{
		for (int i = 0; i < circuit.length; i++)
		{
			System.out.println("" + i + " - " + circuit[i]);
		}
	}

	public void plot(String fileName)
	{
		if (this.getInstance().getX() == null || this.getInstance().getY() == null)
		{
			System.out.println("Cannot print solution: the instance has no positions assigned for each vertex.");
		}
		else
		{
			BufferedImage bi = this.tspInstance.drawProblem();
			
			drawSolution(bi);
			
			try
			{
				ImageIO.write(bi, "PNG", new File(fileName + ".PNG"));
		    }
			catch (IOException ie)
			{
				ie.printStackTrace();
		    }
		}
	}
	
	void drawSolution(BufferedImage bi)
	{
		Graphics2D ig2 = bi.createGraphics();
		ig2.setPaint(Color.RED);

		int imageSize = 500;
		int imageBorder = 50;
		double maxX = tspInstance.max(tspInstance.getX());
		double maxY = tspInstance.max(tspInstance.getY());
		double minX = tspInstance.min(tspInstance.getX());
		double minY = tspInstance.min(tspInstance.getY());
		
		
		for (int i = 0; i < circuit.length - 1; i++)
		{
			int a = circuit[i];
			int b = circuit[i + 1];
			int x1 = (int)(((tspInstance.getX(a) - minX) / (maxX - minX)) * imageSize) + imageBorder;
			int y1 = (int)(((tspInstance.getY(a) - minY) / (maxY - minY)) * imageSize) + imageBorder;
			int x2 = (int)(((tspInstance.getX(b) - minX) / (maxX - minX)) * imageSize) + imageBorder;
			int y2 = (int)(((tspInstance.getY(b) - minY) / (maxY - minY)) * imageSize) + imageBorder;
			ig2.drawLine(x1, y1, x2, y2);
		}
		
		if (circuit.length == tspInstance.getLength())
		{
			int a = circuit[circuit.length - 2];
			int b = circuit[circuit.length - 1];
			int x1 = (int)(((tspInstance.getX(a) - minX) / (maxX - minX)) * imageSize) + imageBorder;
			int y1 = (int)(((tspInstance.getY(a) - minY) / (maxY - minY)) * imageSize) + imageBorder;
			int x2 = (int)(((tspInstance.getX(b) - minX) / (maxX - minX)) * imageSize) + imageBorder;
			int y2 = (int)(((tspInstance.getY(b) - minY) / (maxY - minY)) * imageSize) + imageBorder;
			ig2.drawLine(x1, y1, x2, y2);

			a = circuit[circuit.length - 1];
			b = circuit[0];
			x1 = (int)(((tspInstance.getX(a) - minX) / (maxX - minX)) * imageSize) + imageBorder;
			y1 = (int)(((tspInstance.getY(a) - minY) / (maxY - minY)) * imageSize) + imageBorder;
			x2 = (int)(((tspInstance.getX(b) - minX) / (maxX - minX)) * imageSize) + imageBorder;
			y2 = (int)(((tspInstance.getY(b) - minY) / (maxY - minY)) * imageSize) + imageBorder;
			ig2.drawLine(x1, y1, x2, y2);
		}
	}
	

	
	public double getCost()
	{
		if (circuit.length <= 1)
		{
			return 0;
		}
		
		double acc = 0;
		for (int i = 0; i < circuit.length; i++)
		{
			int a = circuit[i];
			int b = circuit[(i + 1) % circuit.length];
			acc += tspInstance.getDistance(a, b);			
		}
		return acc;
	}
	
	public int getInstanceSize()
	{
		return this.tspInstance.getLength();
	}
	
	public boolean isComplete()
	{
		return circuit.length == tspInstance.getLength();
	}
	
	public int getCurrentLength()
	{
		return circuit.length;
	}
	
	public TSPPartialSolution copy()
	{
		return new TSPPartialSolution(tspInstance, circuit);
	}
	
	public boolean isVertexCovered(int v)
	{
		for (int i = 0; i < circuit.length; i++)
		{
			if (circuit[i] == v)
			{
				return true;
			}
		}
		return false;
	}
	
	public int getActualCurrentIndex()
	{
		return circuit[circuit.length - 1];
	}	
	
	public TSPPartialSolution createNextStep(int indexToAdd)
	{
		int n = circuit.length;
		int[] newIndexes = new int[n + 1];
		System.arraycopy(circuit, 0, newIndexes, 0, n);
		newIndexes[n] = indexToAdd;
		return new TSPPartialSolution(tspInstance, newIndexes);
	}
	
	public void generateRandomSolution()
	{
		this.circuit = generateInitialCircuit(tspInstance);
	}
	
	public static int[] generateInitialCircuit(TSPInstance tspInstance)
	{
//		TSPHeuristicPFIH pfih = new TSPHeuristicPFIH();
//		TSPConstructivePartialSolution startSolution = new TSPConstructivePartialSolution(tspInstance);
//		while (!startSolution.isComplete())
//		{
//			startSolution = pfih.run(startSolution);
//		}
//		return startSolution.getCurrentCircuit();
//		
		Integer n = tspInstance.getLength();
		Integer[] circuit = new Integer[n];
		for (int i = 0; i < n; i++)
		{
			circuit[i] = i;
		}
		Collections.shuffle(Arrays.asList(circuit));
		
		int[] intCircuit = new int[n];
		for (int i = 0; i < n; i++)
		{
			intCircuit[i] = circuit[i];
		}
		return intCircuit;
	}

}
