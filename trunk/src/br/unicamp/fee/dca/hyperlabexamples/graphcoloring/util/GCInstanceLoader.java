package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCInstance;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Graph;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;

public class GCInstanceLoader {
    
	
	@SuppressWarnings("unchecked")
	public GCInstance read(String gcInstanceFileName)
	{
		String name;
		int nodes = 0;
		
		
		Scanner scanner = null;
		
		GCInstance newInstance = new GCInstance();
		
		try 
		{
			scanner = new Scanner(new FileInputStream(gcInstanceFileName));
	    	while (scanner.hasNextLine())
	    	{
	    		String line = scanner.nextLine();
	    		if (line.startsWith("FILE:"))
	    		{
	    			String[] content = line.split(" ");
	    			name = content[content.length - 1];
	    			newInstance.setName(name);
	    		}
	    		else if (line.startsWith("p edge"))
	    		{
	    			String[] content = line.split(" ");
	    			nodes = Integer.parseInt(content[content.length - 2]);
	    		
	    			Graph newgraph = newInstance.getGraph();
	    			
	    			
	    			ArrayList<Integer> colors = new ArrayList<Integer>();
	    			for (int i = 0; i < nodes; i++)
	    			{
	    				colors.add(i);
	    			}
	    			
	    			for (int i = 0; i < nodes; i++)
	    			{
	    				Node newNode = new Node();
	    				newNode.id = i;
	    				newNode.possibleColors = (ArrayList<Integer>) colors.clone();
	    				newNode.weight = 1;
	    				newgraph.addNode(newNode);
	    			}
	    			
	    			line = scanner.nextLine();
	    			while (line.matches(" *e+ +\\d+ +\\d+"))
	    			{
	    				content = line.split(" ");
	    				int[] numbers = new int[2];
	    				int i = 0;
    					int j = 0;
	    				while (i < content.length && j < 3)
	    				{
    						if (!content[i].equals("") && !content[i].equals("e") )
    						{
    							numbers[j] = Integer.parseInt(content[i]);
    							j++;
    						}
    						i++;
	    				}
	    				newgraph.getNode(numbers[0]-1).adjacents.add(numbers[1]-1);
	    				if (scanner.hasNextLine())
	    				{
	    					line = scanner.nextLine();
	    				}
	    				else
	    				{
	    					break;
	    				}
	    			}
	    		}
	    	}
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally{
	    	if (scanner != null)
	    	{
	    		scanner.close();
	    	}
	    }
	    return newInstance;
	  }
}
