package br.unicamp.fee.dca.hyperlabanalyzer.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SeriesLabels extends JPanel
{
	private int mN;
	private String[] mNames;
	private Palette mPalette;
	
	public SeriesLabels(Palette palette, String[] names)
	{
		mNames = names;
		mN = names.length;
		mPalette = palette;
	}

	private static final int PADDING_GAP = 20;
	private static final int ITEM_HEIGHT = 40;
	private static final int LINE_LENGTH = 40;
	private static final int PREF_W = 480;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < mN; i++)
		{
			int yCenter = PADDING_GAP + i * ITEM_HEIGHT + ITEM_HEIGHT / 2;
			g2.setColor(mPalette.getColor(i));
			g2.drawLine(PADDING_GAP, yCenter, PADDING_GAP + LINE_LENGTH, yCenter);
			g2.fillOval(PADDING_GAP + LINE_LENGTH / 2 - LineGraph.DOT_RADIUS,
					yCenter - LineGraph.DOT_RADIUS, LineGraph.DOT_DIAMETER, LineGraph.DOT_DIAMETER);
			
			g2.setColor(Color.BLACK);
			g2.drawString(mNames[i], PADDING_GAP + LINE_LENGTH + 10,
					PADDING_GAP + 3 + ITEM_HEIGHT / 2 + i * ITEM_HEIGHT);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, mN * ITEM_HEIGHT + PADDING_GAP * 2);
	}

	@Override
	public Dimension getMinimumSize()
	{
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize()
	{
		return getPreferredSize();
	}
}
