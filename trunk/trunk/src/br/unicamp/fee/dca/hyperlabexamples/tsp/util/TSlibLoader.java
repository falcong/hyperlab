package br.unicamp.fee.dca.hyperlabexamples.tsp.util;

import java.io.FileInputStream;
import java.util.Scanner;

import br.unicamp.fee.dca.hyperlabexamples.tsp.TSPInstance;

public class TSlibLoader {
    
	public TSPInstance read(String tsplibFileName)
	{
		String name;
		String type = null;
		int dimension = 0;
		String edgeWeightType = null;
		String edgeWeightFormat = null;
		
		
		Scanner scanner = null;
		
		TSPInstance newInstance = new TSPInstance();
		
		try 
		{
			scanner = new Scanner(new FileInputStream(tsplibFileName));

    		String line = scanner.nextLine();
	    	while (scanner.hasNextLine())
	    	{
	    		if (line.startsWith("NAME"))
	    		{
	    			String[] content = line.split(" ");
	    			name = content[content.length - 1];
	    			newInstance.setName(name);
					line = scanner.nextLine();
	    		}
	    		else if (line.startsWith("TYPE"))
	    		{
	    			String[] content = line.split(" ");
	    			type = content[content.length - 1];
					line = scanner.nextLine();
	    		}
	    		else if (line.startsWith("DIMENSION"))
	    		{
	    			String[] content = line.split(" ");
	    			dimension = Integer.parseInt(content[content.length - 1]);
	    			newInstance.setLength(dimension);
					line = scanner.nextLine();
	    		}
	    		else if (line.startsWith("EDGE_WEIGHT_TYPE"))
	    		{
	    			String[] content = line.split(" ");
	    			edgeWeightType = content[content.length - 1];
					line = scanner.nextLine();
	    		}
	    		else if (line.startsWith("EDGE_WEIGHT_FORMAT"))
	    		{
	    			String[] content = line.split(" ");
	    			edgeWeightFormat = content[content.length - 1];
					line = scanner.nextLine();
	    		}
	    		else if (line.startsWith("NODE_COORD_SECTION") || line.startsWith("DISPLAY_DATA_SECTION")) 	    		{
	    			double[] x = new double[dimension];
	    			double[] y = new double[dimension];
	    			
	    			line = scanner.nextLine();
	    			while (scanner.hasNextLine() && line.matches(" *\\d+ +[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)? +[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"))
	    			{
	    				String[] content = line.split(" ");
	    				int index = 0;
	    				double xread = 0;
	    				double yread= 0;
	    				int i = 0;
    					int j = 0;
	    				while (i < content.length && j < 3)
	    				{
    						if (!content[i].equals("") && j == 0)
    						{
    							index = Integer.parseInt(content[i]);
    							j++;
    						}
    						else if(!content[i].equals("") && j == 1)
    						{
    							xread = Double.parseDouble(content[i]);
    							j++;
    						}
    						else if(!content[i].equals("") && j == 2)
    						{
    							yread = Double.parseDouble(content[i]);
    							j++;
    						}
    						i++;
	    				}
	    				x[index-1] = xread;
	    				y[index-1] = yread;	
	    				line = scanner.nextLine();
	    			}
	    			
	    			newInstance.setX(x);
	    			newInstance.setY(y);
	    			
	    			if ((type != null && type.equals("TSP")) || (edgeWeightType != null && edgeWeightType.equals("EUC_2D")))
	    			{
	    				newInstance.calculateDistanceTable();
	    			}
	    		}
	    		else if (line.startsWith("EDGE_WEIGHT_SECTION"))
	    		{
	    			if ((type != null && type.equals("TSP")) 
	    					&& (edgeWeightType != null && edgeWeightType.equals("EXPLICIT") 
	    							&& edgeWeightFormat != null && edgeWeightFormat.equals("UPPER_ROW")))
	    			{
	    				double[][] distances = new double[dimension][dimension];
	    				for (int i = 0; i < dimension; i++)
	    				{
	    					distances[i][i] = 0;
	    				}
	    				line = scanner.nextLine();
	    				
	    				int i = 0;
	    				int j = 1;
	    				
	    				while (scanner.hasNextLine() && !line.matches("^[^0-9]+$"))
		    			{
	    					
	    					Scanner secScanner = new Scanner(line);
	    					while (secScanner.hasNext())
	    					{
	    						distances[i][j] = Double.parseDouble(secScanner.next());
	    						distances[j][i] = distances[i][j];
	    						j++;
	    						if (j >= dimension)
	    						{
	    							i++;
	    							j = i+1;
	    						}
	    					}
	    					
	    					secScanner.close();
	    					line = scanner.nextLine();
		    			}
	    				newInstance.setDistances(distances);
	    			}	
	    			else if ((edgeWeightType != null && edgeWeightType.equals("EXPLICIT") 
	    							&& edgeWeightFormat != null && edgeWeightFormat.equals("FULL_MATRIX")))
	    			{
	    				double[][] distances = new double[dimension][dimension];
	    				
	    				line = scanner.nextLine();
	    				
	    				int i = 0;
	    				int j = 0;
	    				
	    				while (scanner.hasNextLine() && !line.matches("^[^0-9]+$"))
		    			{
	    					
	    					Scanner secScanner = new Scanner(line);
	    					while (secScanner.hasNext())
	    					{
	    						distances[i][j] = Double.parseDouble(secScanner.next());
	    						distances[j][i] = distances[i][j];
	    						j++;
	    						if (j >= dimension)
	    						{
	    							i++;
	    							j = 0;
	    						}
	    					}
	    					
	    					secScanner.close();
	    					line = scanner.nextLine();
		    			}
	    				newInstance.setDistances(distances);
	    			}	
	    			else if ((edgeWeightType != null && edgeWeightType.equals("EXPLICIT") 
	    							&& edgeWeightFormat != null && edgeWeightFormat.equals("LOWER_DIAG_ROW")))
	    			{
	    				double[][] distances = new double[dimension][dimension];
	    				
	    				line = scanner.nextLine();
	    				
	    				int i = 0;
	    				int j = 0;
	    				
	    				while (scanner.hasNextLine() && !line.matches("^[^0-9]+$"))
		    			{
	    					
	    					Scanner secScanner = new Scanner(line);
	    					while (secScanner.hasNext())
	    					{
	    						distances[i][j] = Double.parseDouble(secScanner.next());
	    						distances[j][i] = distances[i][j];
	    						j++;
	    						if (j > i)
	    						{
	    							i++;
	    							j = 0;
	    						}
	    					}
	    					
	    					secScanner.close();
	    					line = scanner.nextLine();
		    			}
	    				newInstance.setDistances(distances);
	    			}	
	    			else if ((edgeWeightType != null && edgeWeightType.equals("EXPLICIT") 
	    							&& edgeWeightFormat != null && edgeWeightFormat.equals("UPPER_DIAG_ROW")))
	    			{
	    				double[][] distances = new double[dimension][dimension];
	    				
	    				line = scanner.nextLine();
	    				
	    				int i = 0;
	    				int j = 0;
	    				
	    				while (scanner.hasNextLine() && !line.matches("^[^0-9]+$"))
		    			{
	    					
	    					Scanner secScanner = new Scanner(line);
	    					while (secScanner.hasNext())
	    					{
	    						distances[i][j] = Double.parseDouble(secScanner.next());
	    						distances[j][i] = distances[i][j];
	    						j++;
	    						if (j >= dimension)
	    						{
	    							i++;
	    							j = i;
	    						}
	    					}
	    					
	    					secScanner.close();
	    					line = scanner.nextLine();
		    			}
	    				newInstance.setDistances(distances);
	    			}	
	    		}
	    		else
	    		{
					line = scanner.nextLine();
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
