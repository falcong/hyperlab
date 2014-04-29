package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class StatisticsTablesPerspective extends JPanel
{
	private static final int PADDING = 10;
	
	public StatisticsTablesPerspective(ResultsAnalyst resultsAnalyst, boolean pretty)
	{
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		int n = TableModel.Mode.values().length;
		for (int m = 0; m < n; m++)
		{
			TableModel tableModel = new TableModel(resultsAnalyst, pretty);
			tableModel.setMode(TableModel.Mode.values()[m]);
			JTable table = new JTable(tableModel);
			add(table);

			add(Box.createRigidArea(new Dimension(0, PADDING)));
		}
	}
	
	static class TableModel extends AbstractTableModel
	{
		enum Mode
		{
			AVERAGE("Average"),
			MIN("Min"),
			MAX("Max"),
			FIRST_QUARTILE("First Quartile"),
			MEDIAN("Median"),
			THIRD_QUARTILE("Third Quartile"),
			VARIANCE("Variance"),
			STD_DEV("Standard Deviation");
			
			public final String name;
			
			private Mode(String name)
			{
				this.name = name;
			}
		}
		
		private final ResultsAnalyst resultsAnalyst;
		private final GenericBenchmark[] benchmarks;
		private final int cols;
		private final int rows;
		private Mode mode;
		private boolean pretty;

		public TableModel(ResultsAnalyst resultsAnalyst, boolean pretty)
		{
			this.resultsAnalyst = resultsAnalyst;
			this.benchmarks = resultsAnalyst.getHyperHeuristicBenchmark();
			this.cols = benchmarks.length + 1;
			this.rows = benchmarks[0].getNumberOfEntries() + 1;
			this.pretty = pretty;
			setMode(Mode.AVERAGE);
		}
		
		public void setMode(Mode mode)
		{
			if (mode != this.mode)
			{
				this.mode = mode;
				fireTableDataChanged();
			}
		}
		
		@Override
		public int getColumnCount()
		{
			return cols;
		}

		@Override
		public int getRowCount()
		{
			return rows;
		}

		@Override
		public Object getValueAt(int y, int x)
		{
			int p = y - 1;
			int hh = x - 1;
			if (hh >= 0)
			{
				if (p >= 0)
				{
					ValueSet vs = benchmarks[hh].getEntriesCost()[p];
					Object metric = readMetricFromValueSet(mode, vs);
					if (pretty)
					{
						BigDecimal bd = new BigDecimal((Double)metric);
						bd = bd.round(new MathContext(4));
						metric = bd.toString();
					}
					return metric;
				}
				else
				{
					return resultsAnalyst.getResults().getHyperHeuristicName(hh);
				}
			}
			else
			{
				if (p >= 0)
				{
					return resultsAnalyst.getResults().getProblemName(p);
				}
				else
				{
					return mode.name;
				}
			}
		}

		private Object readMetricFromValueSet(Mode mode2, ValueSet vs) {
			switch (mode)
			{
				case AVERAGE:
					return vs.getAverage();
				case MIN:
					return vs.getMin();
				case MAX:
					return vs.getMax();
				case FIRST_QUARTILE:
					return vs.getValueAtPercentage(0.25);
				case MEDIAN:
					return vs.getValueAtPercentage(0.5);
				case THIRD_QUARTILE:
					return vs.getValueAtPercentage(0.75);
				case VARIANCE:
					return vs.getVariance();
				case STD_DEV:
					return vs.getStandardDeviation();
				default:
					System.out.println("Mode not supported: " + mode);
					return null;
			}
		}
	}
}
