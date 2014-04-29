package br.unicamp.fee.dca.hyperlab.benchmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JOptionPane;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import br.unicamp.fee.dca.hyperlab.HyperHeuristic;

public class Results
{
	public class RunResult
	{
		public double cost;
		public double timeElapsed;

		public RunResult(double cost, double timeElapsed)
		{
			this.cost = cost;
			this.timeElapsed = timeElapsed;
		}
	}
	
	RunResult[][][] rawDataResults;
	String[] hyperHeuristicsNames;
	String[] problemNames;
	int[] problemInstanceCounts;
	int hyperHeuristicNumber;
	int problemNumber;
	
	public Results(HyperHeuristic[] hyperHeuristics, List<Problem<?>> problems)
	{
		hyperHeuristicNumber = hyperHeuristics.length;
		problemNumber = problems.size();

		initFromExecutionHyperHeuristicMetadata(hyperHeuristics);
		initFromExecutionProblemMetadata(problems);
		initRawDataResults();
	}

	private void initFromExecutionHyperHeuristicMetadata(HyperHeuristic[] hyperHeuristics)
	{
		Map<String, Integer> nameUsageCount = new HashMap<String, Integer>();
		
		hyperHeuristicsNames = new String[hyperHeuristicNumber];
		for (int hh = 0; hh < hyperHeuristicNumber; hh++)
		{
			String rawName = hyperHeuristics[hh].getName();
			if (!nameUsageCount.containsKey(rawName))
			{
				nameUsageCount.put(rawName, 1);
				hyperHeuristicsNames[hh] = rawName;
			}
			else	
			{
				int useCount = nameUsageCount.get(rawName);
				useCount++;
				nameUsageCount.put(rawName, useCount);
				hyperHeuristicsNames[hh] = rawName + " (" + useCount + ")";
			}
		}
	}
	
	private void initFromExecutionProblemMetadata(List<Problem<?>> problems)
	{
		problemNames = new String[problemNumber];
		problemInstanceCounts = new int[problemNumber];
		for (int p = 0; p < problemNumber; p++)
		{
			Problem<?> problem = problems.get(p);
			String problemName = problem.getName();
			if (problemName == null)
			{
				problemNames[p] = "Problem" + p;
			}
			else	
			{
				problemNames[p] = problemName;
			}
			
			problemInstanceCounts[p] = problem.problemInstances.length;
		}
	}

	private void initRawDataResults()
	{
		rawDataResults = new RunResult[hyperHeuristicNumber][][];
		for (int hh = 0; hh < hyperHeuristicNumber; hh++)
		{
			rawDataResults[hh] = new RunResult[problemNumber][];
			for (int p = 0; p < problemNumber; p++)
			{
				rawDataResults[hh][p] = new RunResult[problemInstanceCounts[p]];
			}
		}
	}
	
	public void register(int hh, int p, int i, double cost, double timeElapsed)
	{
		rawDataResults[hh][p][i] = new RunResult(cost, timeElapsed);
	}
	
	public void writeToCsv(String fileName)
	{
		try
		{
			CSVWriter writer = new CSVWriter(new FileWriter(fileName));
			
			writeHeaderToCsv(writer);
			writePayloadToCsv(writer);
			
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void writeHeaderToCsv(CSVWriter writer)
	{
		writer.writeNext(hyperHeuristicsNames);
		writer.writeNext(problemNames);
		
		String[] s = new String[problemNumber];
		for (int p = 0; p < problemNumber; p++)
		{
			s[p] = Integer.toString(problemInstanceCounts[p]);
		}
		writer.writeNext(s);
		
		writer.writeNext(new String[0]);
	}

	private void writePayloadToCsv(CSVWriter writer)
	{
		String[] entry = new String[]
		{
			"Hyper-heuristic",
			"Problem Suite",
			"Instance",
			"Solution Cost",
			"Running Time"
		};
		writer.writeNext(entry);

		for (int hh = 0; hh < hyperHeuristicNumber; hh++)
		{
			for (int p = 0; p < problemNumber; p++)
			{
				for (int i = 0; i < rawDataResults[hh][p].length; i++)
				{
					RunResult runResult = rawDataResults[hh][p][i];
					entry = new String[]
					{
						hyperHeuristicsNames[hh],
						problemNames[p],
						Integer.toString(i),
						Double.toString(runResult.cost),
						Double.toString(runResult.timeElapsed)
					};
					writer.writeNext(entry);
				}
			}
		}
	}

	public static Results fromCsv(File f)
	{
		try
		{
			CSVReader reader = new CSVReader(new FileReader(f));
			try
			{
				List<String[]> content = reader.readAll();
				reader.close();
				return new Results(content);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null,
						"Error reading file: " + e.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				try
				{
					reader.close();
				}
				catch (IOException e1)
				{
				}
				return null;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Results(List<String[]> content)
	{
		ListIterator<String[]> it = content.listIterator();
		initFromListMetadata(it);
		initFromListPayload(it);
	}

	private void initFromListMetadata(ListIterator<String[]> it)
	{
		String[] readHyperHeuristicNames = it.next();
		hyperHeuristicNumber = readHyperHeuristicNames.length;
		hyperHeuristicsNames = new String[hyperHeuristicNumber];
		for (int hh = 0; hh < hyperHeuristicNumber; hh++)
		{
			hyperHeuristicsNames[hh] = readHyperHeuristicNames[hh];
		}

		String[] readProblemNames = it.next();
		String[] readProblemInstanceCounts = it.next();
		problemNumber = readProblemNames.length;
		problemNames = new String[problemNumber];
		problemInstanceCounts = new int[problemNumber];
		for (int p = 0; p < problemNumber; p++)
		{
			problemNames[p] = readProblemNames[p];
			problemInstanceCounts[p] = Integer.parseInt(readProblemInstanceCounts[p]);
		}
		
		initRawDataResults();
		
		// Skip blank line.
		it.next();
	}
	
	private void initFromListPayload(ListIterator<String[]> it)
	{
		HashMap<String, Integer> hyperHeuristicNameToIndex = new HashMap<String, Integer>();
		for (int hh = 0; hh < hyperHeuristicNumber; hh++)
		{
			hyperHeuristicNameToIndex.put(hyperHeuristicsNames[hh], hh);
		}

		HashMap<String, Integer> problemNameToIndex = new HashMap<String, Integer>();
		for (int p = 0; p < problemNumber; p++)
		{
			problemNameToIndex.put(problemNames[p], p);
		}
		
		// Skip column titles.
		it.next();
		
		while (it.hasNext())
		{
			String[] line = it.next();
			int hh = hyperHeuristicNameToIndex.get(line[0]);
			int p = problemNameToIndex.get(line[1]);
			int i = Integer.parseInt(line[2]);
			double cost = Double.parseDouble(line[3]);
			double timeElapsed = Double.parseDouble(line[4]);
			register(hh, p, i, cost, timeElapsed);
		}
	}

	public int getHyperHeuristicNumber()
	{
		return hyperHeuristicNumber;
	}

	public int getProblemNumber()
	{
		return problemNumber;
	}
	
	public String getHyperHeuristicName(int hh)
	{
		return hyperHeuristicsNames[hh];
	}
	
	public String getProblemName(int p)
	{
		return problemNames[p];
	}

	public RunResult[] getRunResults(int hh, int p)
	{
		return rawDataResults[hh][p];
	}

	public String[] getHyperHeuristicNames()
	{
		return hyperHeuristicsNames;
	}

	public String[] getProblemNames()
	{
		return problemNames;
	}
}
