package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unicamp.fee.dca.hyperlabanalyzer.graph.LineGraph;
import br.unicamp.fee.dca.hyperlabanalyzer.graph.Palette;
import br.unicamp.fee.dca.hyperlabanalyzer.graph.SeriesLabels;

public class ProblemsLineGraphPerspective extends JPanel
{
	private static final int PADDING = 10;
	
	public ProblemsLineGraphPerspective(ResultsAnalyst resultsAnalyst)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel welcomeLabel = new JLabel("Comparison of problems");
		add(welcomeLabel);
		
		GenericBenchmark[] benchmarks = resultsAnalyst.getHyperHeuristicBenchmark();
		Palette palette = new Palette();

		JPanel problemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.X_AXIS));
		
		Double[][] values = getValueMatrix(benchmarks, GenericBenchmark.Metric.COST);
		LineGraph costGraph = new LineGraph(values, resultsAnalyst.getResults().getProblemNames(),
				palette, "cost", "Cost for all Hyper-Heuristics");
		problemPanel.add(costGraph);
		
		problemPanel.add(Box.createRigidArea(new Dimension(PADDING, 0)));

		values = getValueMatrix(benchmarks, GenericBenchmark.Metric.TIME);
		LineGraph timeGraph = new LineGraph(values, resultsAnalyst.getResults().getProblemNames(),
				palette, "(s)", "Running time for all Hyper-Heuristics");
		problemPanel.add(timeGraph);
		
		add(problemPanel);

		add(Box.createRigidArea(new Dimension(0, PADDING)));
		
		JPanel labelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel seriesLabels = new SeriesLabels(palette,
				resultsAnalyst.getResults().getHyperHeuristicNames());
		labelsPanel.add(seriesLabels);
		add(labelsPanel);
	}

	private Double[][] getValueMatrix(GenericBenchmark[] benchmarks,
			GenericBenchmark.Metric metric) {
		int numberOfBenchmarks = benchmarks.length;
		int numberOfValuesPerBenchmark = benchmarks[0].getNumberOfEntries();
		Double[][] values = new Double[numberOfBenchmarks][];
		for (int i = 0; i < numberOfBenchmarks; i++)
		{
			GenericBenchmark gb = benchmarks[i];
			ValueSet[] valueSets = gb.getMetric(metric);
			values[i] = new Double[numberOfValuesPerBenchmark];
			for (int j = 0; j < numberOfValuesPerBenchmark; j++)
			{
				values[i][j] = valueSets[j].getValueAtPercentage(0.5);
			}
		}
		return values;
	}
}
