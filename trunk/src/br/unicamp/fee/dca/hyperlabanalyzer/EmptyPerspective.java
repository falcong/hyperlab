package br.unicamp.fee.dca.hyperlabanalyzer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmptyPerspective extends JPanel
{
	public EmptyPerspective()
	{
		super();

		JLabel welcomeLabel = new JLabel("Welcome to Hyperbole Analyzer!");
		add(welcomeLabel);
	}
}
