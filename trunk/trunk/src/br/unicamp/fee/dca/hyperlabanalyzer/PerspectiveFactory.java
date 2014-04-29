package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.Container;

import br.unicamp.fee.dca.hyperlabanalyzer.MainFrame.AppState;

public class PerspectiveFactory
{

	public static Container createPerspective(AppState state, ResultsAnalyst resultsAnalyst)
	{
		switch (state)
		{
			case EMPTY:
				return new EmptyPerspective();

			case COMPARE_HYPER_HEURISTICS:
				return new CompareHyperHeuristicsPerspective(resultsAnalyst);

			case COMPARE_PROBLEMS:
				return new CompareProblemsPerspective(resultsAnalyst);

			case PROBLEMS_LINE_GRAPH:
				return new ProblemsLineGraphPerspective(resultsAnalyst);

			case STATISTICS_TABLE_PRETTY:
				return new StatisticsTablesPerspective(resultsAnalyst, true);

			case STATISTICS_TABLE_RAW:
				return new StatisticsTablesPerspective(resultsAnalyst, false);
				
			default:
				return null;
		}
	}
}
