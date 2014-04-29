package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.fee.dca.hyperlabanalyzer.graph.QuartileGraph;

public class CompareProblemsPerspective extends JPanel
{
	private static final int PADDING = 10;
	
	public CompareProblemsPerspective(ResultsAnalyst resultsAnalyst)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel welcomeLabel = new JLabel("Comparison of problems");
		add(welcomeLabel);
		
		GenericBenchmark[] benchmarks = resultsAnalyst.getHyperHeuristicBenchmark();

		for (int i = 0; i < benchmarks.length; i++)
		{
			JPanel problemPanel = new JPanel();
			problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.X_AXIS));
			
			QuartileGraph costGraph = new QuartileGraph(benchmarks[i].getEntriesCost(),
					resultsAnalyst.getResults().getProblemNames(), "cost",
					"Cost for " + resultsAnalyst.getResults().getHyperHeuristicName(i));
			problemPanel.add(costGraph);
			
			problemPanel.add(Box.createRigidArea(new Dimension(PADDING, 0)));
			
			QuartileGraph timeGraph = new QuartileGraph(benchmarks[i].getEntriesTime(),
					resultsAnalyst.getResults().getProblemNames(), "(s)",
					"Running time for " + resultsAnalyst.getResults().getHyperHeuristicName(i));
			problemPanel.add(timeGraph);
			
			add(problemPanel);
			if (i < benchmarks.length - 1)
			{
				add(Box.createRigidArea(new Dimension(0, PADDING)));
			}
		}
	}
}
