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
import br.unicamp.fee.dca.hyperlabanalyzer.ValueSet;

public class QuartileGraph extends JPanel
{
	private int mN;
	private ValueSet[] mEntries;
	private String[] mHyperHeuristicNames;
	private String mUnit;
	private String mTitle;

	public QuartileGraph(ValueSet[] entries, String[] hyperHeuristicNames, String unit,
			String title)
	{
		assert (entries.length == hyperHeuristicNames.length);
		
		mN = entries.length;
		mEntries = entries;
		mHyperHeuristicNames = hyperHeuristicNames;
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
	private static final int STOPPER_W = 14;
	private static final int HALF_STOPPER_W = STOPPER_W / 2;
	private static final int QUARTILES_W = 14;
	private static final int HALF_QUARTILES_W = QUARTILES_W / 2;

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
		
		for (int i = 0; i < mN; i++)
		{
			drawBar(g2, i);
		}
		
		drawScaleLabels(g2);
	}

	private void calculateDrawParameters()
	{
		mYMin = ValueSet.getMinInArray(mEntries);
		if (mYMin > 0)
		{
			mYMin = 0;
		}
		mYMax = ValueSet.getMaxInArray(mEntries);
		if (mYMax < 0)
		{
			mYMax = 0;
		}
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

	private void drawBar(Graphics2D g2, int i)
	{
		// Min to max.
		g2.setColor(Color.BLACK);
		ValueSet valueSet = mEntries[i];
		int x = getXCenterForEntry(i);
		int y1 = getYForValue(valueSet.getMin());
		int y2 = getYForValue(valueSet.getMax());
		g2.drawLine(x, y1, x, y2);
		g2.drawLine(x - HALF_STOPPER_W, y1, x + HALF_STOPPER_W, y1);
		g2.drawLine(x - HALF_STOPPER_W, y2, x + HALF_STOPPER_W, y2);

		// Central quartiles.
		int y3 = getYForValue(valueSet.getValueAtPercentage(0.25));
		int y4 = getYForValue(valueSet.getValueAtPercentage(0.75));

		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(x - HALF_QUARTILES_W, y4, QUARTILES_W, y3 - y4);
		g2.setColor(Color.BLACK);
		g2.drawRect(x - HALF_QUARTILES_W, y4, QUARTILES_W, y3 - y4);
		
		// Median.
		g2.setColor(Color.BLACK);
		int yAv = getYForValue(valueSet.getValueAtPercentage(0.5));
		g2.drawLine(x - HALF_QUARTILES_W, yAv, x + HALF_QUARTILES_W, yAv);

		// Bar labels.
		String label = mHyperHeuristicNames[i];
		int stringWidth = g2.getFontMetrics().stringWidth(label);
		int stringHeight = g2.getFontMetrics().getHeight();
		int stringX = x - stringWidth / 2;
		int stringY = mHeight - BORDER_GAP + 5 + stringHeight + (i % 2 == 0 ? 0 : 20);
		g2.drawString(label, stringX, stringY);
	}

	private void drawScaleLabels(Graphics2D g2)
	{
		int x1 = mXYAxis - 10;
		int x1Short = mXYAxis - 5;
		int x2 = mXYAxis;
		
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
	public Dimension getPreferredSize()
	{
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
