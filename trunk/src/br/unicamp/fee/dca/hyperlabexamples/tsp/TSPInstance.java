package br.unicamp.fee.dca.hyperlabexamples.tsp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.Random;

import javax.imageio.ImageIO;

public class TSPInstance
{
	
	private String name;
	private int length;
	private double[][] distances;
	private double[] x;
	private double[] y;
	
	public void generateRandomInstance(int vertexCount)
	{
		length = vertexCount;

		Random r = new Random();
		
		x = new double[length];
		y = new double[length];
		for (int i = 0; i < length; i++)
		{
			x[i] = r.nextDouble();
			y[i] = r.nextDouble();
		}

		calculateDistanceTable();
	}

	public void calculateDistanceTable() 
	{
		distances = new double[length][length];
		for (int i = 0; i < length; i++)
		{
			for (int j = 0; j < i; j++)
			{
				double dx = x[i] - x[j];
				double dy = y[i] - y[j];
				distances[i][j] = (double) Math.sqrt(dy * dy + dx * dx);
			}
			
			{
				int j = i;
				distances[i][j] = Double.MAX_VALUE;
			}
		}
		
		for (int i = 0; i < length; i++)
		{
			for (int j = i + 1; j < length; j++)
			{
				distances[i][j] = distances[j][i];
			}
		}
	}

	public int getLength()
	{
		return length;
	}

	public double getX(int i)
	{
		return x[i];
	}

	public double getY(int i)
	{
		return y[i];
	}

	public double getDistance(int i, int j)
	{
		return distances[i][j];
	}
	
	public BufferedImage drawProblem()
	{
		int imageSize = 500;
		int imageBorder = 50;
		int width = imageSize + 2 * imageBorder;
		int height = imageSize + 2 * imageBorder;
		// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		// into integer pixels
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D ig2 = bi.createGraphics();
		ig2.setBackground(Color.WHITE);
		ig2.setPaint(Color.BLACK);
		
		ig2.clearRect(0, 0, width, height);
		
		Font font = new Font("TimesRoman", 0, 10);
		ig2.setFont(font);
		FontMetrics fontMetrics = ig2.getFontMetrics();
		/*
		String message = "www.java2s.com!";
		int stringWidth = fontMetrics.stringWidth(message);
		int stringHeight = fontMetrics.getAscent();
		ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
		*/
		double maxX = max(getX());
		double maxY = max(getY());
		double minX = min(getX());
		double minY = min(getY());
		
		double diffX = maxX - minX;
		double diffY = maxY - minY;
		
		int radius = 10;
		for (int i = 0; i < length; i++)
		{
			int x = (int)(((getX(i) - minX) / diffX) * imageSize) + imageBorder;
			int y = (int)(((getY(i) - minY) / diffY) * imageSize) + imageBorder;
			ig2.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);

			String message = "" + i;
			int stringWidth = fontMetrics.stringWidth(message);
			int stringHeight = fontMetrics.getAscent();
			ig2.drawString(message, x - stringWidth / 2, y + stringHeight / 4);
		}
		
		return bi;
	}
	
	public double max(double[] array)
	{
		double max = Double.MIN_VALUE;
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] > max)
			{
				max = array[i];
			}
		}
		return max;
	}
	
	public double min(double[] array)
	{
		double min = Double.MAX_VALUE;
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] < min)
			{
				min = array[i];
			}
		}
		return min;
	}

	public void plot(String fileName)
	{
		BufferedImage bi = drawProblem();
		
		try
		{
			ImageIO.write(bi, "PNG", new File(fileName + ".PNG"));
	    }
		catch (IOException ie)
		{
			ie.printStackTrace();
	    }
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[][] getDistances() {
		return distances;
	}

	public void setDistances(double[][] distances) {
		this.distances = distances;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
