package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.fee.dca.hyperlabanalyzer.graph.QuartileGraph;

public class CompareHyperHeuristicsPerspective extends JPanel
{
	private static final int PADDING = 10;
	
	public CompareHyperHeuristicsPerspective(ResultsAnalyst resultsAnalyst)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel welcomeLabel = new JLabel("Comparison of hyper-heuristics");
		add(welcomeLabel);
		
		GenericBenchmark[] benchmarks = resultsAnalyst.getProblemBenchmarks();

		for (int i = 0; i < benchmarks.length; i++)
		{
			JPanel problemPanel = new JPanel();
			problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.X_AXIS));
			
			QuartileGraph costGraph = new QuartileGraph(benchmarks[i].getEntriesCost(),
					resultsAnalyst.getResults().getHyperHeuristicNames(), "cost",
					"Cost for " + resultsAnalyst.getResults().getProblemName(i));
			problemPanel.add(costGraph);
			
			problemPanel.add(Box.createRigidArea(new Dimension(PADDING, 0)));
			
			QuartileGraph timeGraph = new QuartileGraph(benchmarks[i].getEntriesTime(),
					resultsAnalyst.getResults().getHyperHeuristicNames(), "(s)",
					"Running time for " + resultsAnalyst.getResults().getProblemName(i));
			problemPanel.add(timeGraph);
			
			add(problemPanel);
			if (i < benchmarks.length - 1)
			{
				add(Box.createRigidArea(new Dimension(0, PADDING)));
			}
		}
	}
}
