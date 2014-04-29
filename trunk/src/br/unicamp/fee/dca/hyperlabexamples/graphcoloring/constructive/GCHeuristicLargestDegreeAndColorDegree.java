package br.unicamp.fee.dca.hyperlabexamples.graphcoloring.constructive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.GCPartialSolution;
import br.unicamp.fee.dca.hyperlabexamples.graphcoloring.Node;

/*
 * Also known as IDO+LDO, mixes both Incidence Degree Ordering and Largest Degree Ordering
 */
public class GCHeuristicLargestDegreeAndColorDegree extends GCConstructiveHeuristicOrderBy 
{
	class ColorDegreeComparator implements Comparator<Node>
	{
		private int[] colors;
		
		public ColorDegreeComparator(int[] newColors)
		{
			colors = newColors;
		}
		
		public int compare(Node node1, Node node2) 
		{
			
			if (node1.adjacents.size() == node2.adjacents.size())
			{
			
				int n1 = node1.adjacents.size();
				int n2 = node2.adjacents.size();
				int cdegree1 = 0;
				int cdegree2 = 0;
				
				for (int i = 0; i < n1; i++)
				{
					if (colors[node1.adjacents.get(i)] >= 0)
					{
						cdegree1++;
					}
				}
				
				for (int i = 0; i < n2; i++)
				{
					if (colors[node2.adjacents.get(i)] >= 0)
					{
						cdegree2++;
					}
				}
				
				return cdegree2 - cdegree1;
			}
			return node2.adjacents.size() - node1.adjacents.size();
		}
	}

	
	public GCHeuristicLargestDegreeAndColorDegree()
	{
		super();
	}
	
	public GCPartialSolution run(GCPartialSolution current, int numberOfAssignments)
	{
		return super.run(current, numberOfAssignments);
	}

	public ArrayList<Node> orderNodesByCriteria(GCPartialSolution current) {
		int[] colors = current.getColors();
		ColorDegreeComparator cc = new ColorDegreeComparator(colors);
		@SuppressWarnings("unchecked")
		ArrayList<Node> orderedNodes = (ArrayList<Node>) current.getInstance().getGraph().getNodes().clone();
		Collections.sort(orderedNodes, cc);
		
		return orderedNodes;
	}
}
