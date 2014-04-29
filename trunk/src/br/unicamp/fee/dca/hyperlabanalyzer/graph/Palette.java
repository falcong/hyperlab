package br.unicamp.fee.dca.hyperlabanalyzer.graph;

import java.awt.Color;

public class Palette
{
	private static final Color[] COLORS = new Color[]
	{
		Color.BLUE,
		Color.RED,
		Color.GREEN,
		Color.ORANGE,
		Color.MAGENTA,
		Color.PINK,
		Color.DARK_GRAY,
	};

	public Color getColor(int i) {
		return COLORS[i % COLORS.length];
	}
}
