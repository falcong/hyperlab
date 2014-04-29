package br.unicamp.fee.dca.hyperlabanalyzer.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JPanel;

import br.unicamp.fee.dca.hyperlabanalyzer.MathUtils;

public class LineGraph extends JPanel
{
	private int mN;
	private Double[][] mValues;
	private String[] mHyperHeuristicNames;
	private String mUnit;
	private String mTitle;
	private Palette mPalette;
	
	public LineGraph(Double[][] values, String[] hyperHeuristicNames, Palette palette, String unit,
			String title)
	{
		assert (values.length == hyperHeuristicNames.length);
		
		mN = values[0].length;
		mValues = values;
		mHyperHeuristicNames = hyperHeuristicNames;
		mPalette = palette;
		mUnit = unit;
		mTitle = title;
	}

	private double mYMin;
	private double mYMax;
	private double mXScale;
	private double mYScale;
	private int mWidth;
	private int mHeight;
	private int mYXAxis;
	private int mYEnd;
	private int mXYAxis;
	private int mXEnd;
	private double mYAmplitude;

	private static final int TITLE_H = 20;
	private static final int BORDER_GAP = 50;
	private static final int PREF_W = 480;
	private static final int PREF_H = 360 + TITLE_H;
	public static final int DOT_RADIUS = 3;
	public static final int DOT_DIAMETER = DOT_RADIUS * 2 + 1;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		//g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());

		calculateDrawParameters();
		
		drawTitle(g2);
		
		drawAxis(g2);

		for (int series = 0; series < mValues.length; series++)
		{
			for (int i = 0; i < mN; i++)
			{
				drawBar(g2, series, i);
				if (i < mN - 1)
				{
					drawLink(g2, series, i, i + 1);
				}
			}
		}
		
		drawScaleLabels(g2);
	}

	private void calculateDrawParameters()
	{
		mYMin = Double.MAX_VALUE;
		mYMax = Double.MIN_VALUE;
		for (int i = 0; i < mValues.length; i++)
		{
			for (int j = 0; j < mValues[i].length; j++)
			{
				double v = mValues[i][j];
				if (v < mYMin)
				{
					mYMin = v;
				}
				if (v > mYMax)
				{
					mYMax = v;
				}
			}
		}
		if (mYMin > 0) mYMin = 0;
		if (mYMax < 0) mYMax = 0;
		
		mYAmplitude = mYMax - mYMin;

		mWidth = getWidth();
		mHeight = getHeight();
		
		// TODO: Handle division by 0.
		mXScale = ((double) mWidth - 2 * BORDER_GAP) / (mN);
		mYScale = ((double) (mHeight - TITLE_H) - 2 * BORDER_GAP) / (mYMax - mYMin);
	}

	private void drawTitle(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		Font f = g2.getFont();
		g2.setFont(new Font(f.getName(), Font.BOLD, 16));
		int stringWidth = g2.getFontMetrics().stringWidth(mTitle);
		g2.drawString(mTitle, mWidth / 2 - stringWidth / 2, TITLE_H + 15);
		g2.setFont(f);
	}

	private void drawAxis(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		mXEnd = mWidth - BORDER_GAP;
		mXYAxis = BORDER_GAP;
		mYXAxis = mHeight - BORDER_GAP;
		mYEnd = TITLE_H + BORDER_GAP;
		
		g2.drawLine(mXYAxis, mYXAxis, mXEnd, mYXAxis);
		
		g2.drawLine(mXYAxis, mYXAxis, mXYAxis, mYEnd);
		g2.drawLine(mXYAxis - 10, mYEnd + 10, mXYAxis, mYEnd);
		g2.drawLine(mXYAxis + 10, mYEnd + 10, mXYAxis, mYEnd);
		
		int stringWidth = g2.getFontMetrics().stringWidth(mUnit);
		g2.drawString(mUnit, mXYAxis - stringWidth / 2, mYEnd - 10);
	}

	private void drawBar(Graphics2D g2, int series, int i)
	{
		int x = getXCenterForEntry(i);
		
		// Median.
		g2.setColor(mPalette.getColor(series));
		int yAv = getYForValue(mValues[series][i]);
		g2.fillOval(x - DOT_RADIUS, yAv - DOT_RADIUS, DOT_DIAMETER, DOT_DIAMETER);

		// Bar labels.
		g2.setColor(Color.BLACK);
		String label = mHyperHeuristicNames[i];
		int stringWidth = g2.getFontMetrics().stringWidth(label);
		int stringHeight = g2.getFontMetrics().getHeight();
		int stringX = x - stringWidth / 2;
		int stringY = mHeight - BORDER_GAP + 5 + stringHeight + (i % 2 == 0 ? 0 : 20);
		g2.drawString(label, stringX, stringY);
	}

	private void drawLink(Graphics2D g2, int series, int from, int to)
	{
		int yFrom = getYForValue(mValues[series][from]);
		int xFrom = getXCenterForEntry(from);
		int yTo = getYForValue(mValues[series][to]);
		int xTo = getXCenterForEntry(to);
		g2.setColor(mPalette.getColor(series));
		g2.drawLine(xFrom, yFrom, xTo, yTo);
	}
	
	private void drawScaleLabels(Graphics2D g2)
	{
		int x1 = mXYAxis - 10;
		int x1Short = mXYAxis - 5;
		int x2 = mXYAxis;

		g2.setColor(Color.BLACK);
		ScaleDivisions divisions = calculateStep();
		int firstPlotted = MathUtils.floorToInt(mYMin / divisions.step);
		int lastPlotted = MathUtils.ceilToInt(mYMax / divisions.step);
		for (int i = firstPlotted; i <= lastPlotted; i++)
		{
			double value = i * divisions.step;
			int y = getYForValue(value);
			
			if (y <= mYXAxis && y > mYEnd + 10)
			{
				if (i % divisions.labelPeriod == 0)
				{
					g2.drawLine(x1, y, x2, y);
					NumberFormat formatter = DecimalFormat.getIntegerInstance(Locale.US);
					formatter.setMaximumFractionDigits(10);
					String labelText = formatter.format(value);
					int stringWidth = g2.getFontMetrics().stringWidth(labelText);
					int stringHeight = g2.getFontMetrics().getHeight();
					g2.drawString(labelText, x1 - stringWidth - 5, y + stringHeight / 2 - 3);
				}
				else
				{
					g2.drawLine(x1Short, y, x2, y);
				}
			}	
		}
	}

	private static class ScaleDivisions
	{
		public double step;
		public int labelPeriod;
	}
	
	private ScaleDivisions calculateStep()
	{
		int granularity = MathUtils.floorToInt(Math.log10(mYMax - mYMin));
		
		ScaleDivisions result = new ScaleDivisions();
		result.step = Math.pow(10, granularity);
		
		if (mYAmplitude / result.step < 2)
		{
			result.labelPeriod = 2;
			result.step /= 10;
		}
		else if (mYAmplitude / result.step < 5)
		{
			result.labelPeriod = 5;
			result.step /= 10;
		}
		else
		{
			result.labelPeriod = 1;
		}
		return result;
	}

	private int getXCenterForEntry(int i)
	{
		return (int) (BORDER_GAP + (i + 0.5) * mXScale);
	}
	
	private int getYForValue(double v)
	{
		return mHeight - (int) (BORDER_GAP + (v - mYMin) * mYScale);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	public Dimension getMinimumSize()
	{
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	public Dimension getMaximumSize()
	{
		return new Dimension(PREF_W, PREF_H);
	}
}
