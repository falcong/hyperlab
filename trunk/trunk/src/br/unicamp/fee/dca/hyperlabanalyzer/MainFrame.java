package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.unicamp.fee.dca.hyperlab.benchmark.Results;

public class MainFrame extends JFrame
{
	public enum AppState
	{
		INVALID,
		EMPTY,
		COMPARE_HYPER_HEURISTICS,
		COMPARE_PROBLEMS,
		PROBLEMS_LINE_GRAPH,
		STATISTICS_TABLE_PRETTY,
		STATISTICS_TABLE_RAW,
	}
	
	private ResultsAnalyst mResultsAnalyst;
	private AppState mState;
	private MenuBar mMenu;
	
	public MainFrame()
	{
		mResultsAnalyst = null;
		mState = AppState.INVALID;
		mMenu = new MenuBar(this);
		
		setState(AppState.EMPTY);
		
		setTitle("Hyperbole Benchmark Analyzer");
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//openFile(new File("C:\\Users\\Eduardo\\Documents\\benchmark_results2.csv"));
		//openFile(new File("C:\\Users\\Kamila\\Documents\\benchmark_results.csv"));
    }
    
	public void showOpenFileDialog()
	{
		JFileChooser fc = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
	    fc.setFileFilter(filter);
	    
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			openFile(f);
		}
	}

	public AppState setPerspective(AppState newState)
	{
		if (mResultsAnalyst != null && mState != newState)
		{
			setState(newState);
		}
		return mState;
	}
	
	private void openFile(File f)
	{
		try
		{
			System.out.println("Picked file " + f.getCanonicalPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Results results = Results.fromCsv(f);
		loadResults(results);
	}

	private void loadResults(Results results)
	{
		mResultsAnalyst = new ResultsAnalyst(results);

		setState(AppState.COMPARE_HYPER_HEURISTICS);
	}

	private void setState(AppState newState)
	{
		mState = newState;
		
		Container c = getContentPane();
		c.removeAll();
		
		Container newPerspective =
				PerspectiveFactory.createPerspective(mState, mResultsAnalyst);
		JScrollPane scrollPane = new JScrollPane(newPerspective);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		c.add(scrollPane);
		c.revalidate();
		c.repaint();
		
		mMenu.notifyStateChanged(mState);
	}
}
