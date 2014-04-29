package br.unicamp.fee.dca.hyperlabexamples.graphcoloring;

import graphviz.GraphViz;

import java.io.File;
import java.util.ArrayList;

import br.unicamp.fee.dca.hyperlab.PartialSolution;

public class GCPartialSolution extends PartialSolution
{
	protected GCInstance gcInstance;
	protected int[] colors;
	protected int n;
	protected PossibleColors possibleColors;
	private static String[] vertexColors = new String[]
		{
		"brown1", "greenyellow", "deepskyblue1",  "yellow", "deeppink1", "blueviolet", "coral", 
		"blue4", "springgreen", "lightyellow4", "pink4", "tomato", 
		"navajowhite1", "lightpink", "deeppink4", "lightcyan2", "dimgrey",
		"lightgreen", "darkgoldenrod3", "coral4", "darkslategrey", "darkturquoise", "palegreen", 
		"lavender", "aliceblue", "darkslategray2", "deepskyblue3", "azure4", "snow2", "olivedrab1",
		"steelblue4", "cornsilk2", "seagreen4", "magenta3", "hotpink1", "slategray3", "mediumslateblue", 
		"thistle3", "lightpink1", "mediumorchid1", "salmon", "lightsalmon2", "lightyellow1", 
		"khaki", "brown", "yellow3", "tomato1", "lightslateblue", "indianred3", "red1", 
		"purple", "khaki2", "mediumspringgreen", "forestgreen", "orchid2", "peru", "paleturquoise1", 
		"bisque2", "rosybrown3", "mistyrose4", "indianred4", "paleturquoise3", "magenta", "peachpuff3", 
		"floralwhite", "orangered4", "yellow2", "plum1", "firebrick4", "sienna2", "thistle1", 
		"darkorange3", "indigo", "darkkhaki", "dodgerblue1", "lightsteelblue1", "thistle4", "green3",
		"violetred2", "palegreen4", "springgreen4", "burlywood4", "lemonchiffon2", "lightgrey", 
		"khaki4", "lawngreen", "papayawhip", "seashell2", "antiquewhite", "lightgoldenrod", 
		"darkgoldenrod2", "hotpink4", "cornsilk", "lightyellow3", "maroon", "deepskyblue", 
		"purple1", "steelblue", "goldenrod1", "royalblue1", "mediumseagreen", "plum4", "dodgerblue", 
		"bisque4", "aquamarine1", "green", "cadetblue3", "wheat", "tan", "tomato3", "indianred2", 
		"indianred1", "darkgoldenrod1", "slategray", "lightsalmon1", "gold", "burlywood", "thistle", 
		"grey", "orange4", "darkorchid1", "steelblue2", "lightblue4", "gold1",  "green4",
		"darkslategray", "black", "red", "khaki1", "lightsalmon3", "darkgray", "mediumblue", "crimson", 
		"lightcyan", "antiquewhite3", "blue", "sienna4", "tan4", "lightyellow", "purple2", "bisque", 
		"darkgrey", "navajowhite", "lightgray", "darkseagreen1", "mediumvioletred", "blue3", "darkorange4", 
		"khaki3", "darkblue", "darkolivegreen1", "plum2", "steelblue3", "violetred3", "salmon4", 
		"royalblue", "firebrick1", "royalblue3", "cadetblue", "orangered2", "olivedrab3", "orangered", 
		"sandybrown", "gold4", "snow1", "mintcream", "navy", "slateblue4", "powderblue", 
		"lightcyan3", "beige", "aquamarine", "honeydew", "pink1", "dodgerblue3", "palevioletred1",
		"cornsilk1", "peachpuff", "maroon4", "darkslateblue", "cornsilk4", "lavenderblush1", 
		"darkorange", "peachpuff1", "red4", "tan2", "antiquewhite2", "violetred1",  "green2", 
		"mediumpurple2", "lightpink2", "burlywood2", "darkslategray1", "palegreen3", "mistyrose1", 
		"azure", "palevioletred", "lightseagreen", "chartreuse1", "darkslategray4", "chocolate", 
		"dodgerblue4", "deepskyblue2", "sienna", "snow3", "mistyrose2", "chartreuse", "lightblue1", 
		"hotpink3", "paleturquoise4", "violetred", "chartreuse4", "blue1", "skyblue1", "ivory3", 
		"cyan2", "lightskyblue2", "firebrick", "seagreen", "lightskyblue", "honeydew4", "darkviolet", 
		"gold3", "tomato4", "darkcyan", "darkgreen", "saddlebrown", "ivory", "burlywood1",
		"darkseagreen3", "lemonchiffon1", "purple3", "seagreen1", "salmon3", "olivedrab", "bisque1", 
		"burlywood3", "antiquewhite1", "coral3", "honeydew3", "plum3", "orange", "orange2", 
		"whitesmoke", "antiquewhite4", "moccasin", "mediumpurple3", "aquamarine4", "chocolate3",
		"darkmagenta", "limegreen", "ivory1", "mediumturquoise", "lightsteelblue", "pink", 
		"chocolate1", "peachpuff2", "goldenrod4", "lightsalmon4", "orchid4", "paleturquoise2", 
		"orange3", "lightskyblue3", "navajowhite3", "turquoise1", "chocolate4", "royalblue2", 
		"darkseagreen", "gray", "mediumorchid2", "seagreen2", "mediumpurple4", "violet", "maroon3", 
		"royalblue4", "white", "mediumorchid3", "darkolivegreen", "salmon1", "cyan", "turquoise4", 
		"skyblue3", "honeydew2", "peachpuff4", "coral1", "purple4", "darkolivegreen4", "navajowhite2", 
		"rosybrown", "linen", "seagreen3", "orchid1", "deeppink", "lightsalmon", "lightsteelblue4", 
		"seashell4", "lightsteelblue2", "fuchsia", "mistyrose", "mistyrose3", "wheat4", "deeppink2", 
		"navyblue", "lightblue3", "deeppink3", "ivory4", "cornsilk3", "maroon2", "bisque3", 
		"springgreen1", "plum", "yellow1", "slategrey", "palevioletred2", "brown2", "lavenderblush2", 
		"salmon2", "sienna3", "navajowhite4", "olive", "orangered3", "blue2", "skyblue2", 
		"springgreen3", "tomato2", "skyblue", "gold2", "gainsboro",
		"hotpink2", "slategray1", "lightgoldenrod3", "azure1", "lemonchiffon", "seashell3", "red2", 
		"azure3", "darkolivegreen2", "chocolate2", "seashell", "maroon1", "mediumpurple1", 
		"cadetblue1", "darkgoldenrod4", "aqua", "rosybrown4", "lime", "cyan1", "darkorchid2", 
		"lightpink4", "lightslategrey", "orchid", "yellowgreen", "palevioletred3", "lightcoral", 
		"firebrick2", "chartreuse2", "goldenrod3", "lavenderblush", "lightgoldenrod4", "darkorchid3", 
		"paleturquoise", "blanchedalmond", "oldlace", "aquamarine3", "skyblue4", 
		"darkorchid4", "azure2", "lemonchiffon3", "magenta4", "cadetblue2", "turquoise3", "sienna1", 
		"darkseagreen4", "chartreuse3", "darkorange2", "lightgoldenrod1", "olivedrab4", "lightblue", 
		"teal", "rosybrown1", "slateblue1", "lightsteelblue3", "cornflowerblue", "brown3", "cadetblue4", 
		"lightskyblue1", "pink3", "palevioletred4", "darkolivegreen3", "firebrick3", 
		"rosybrown2", "magenta1", "turquoise", "coral2", "slateblue2", "lightpink3", "lightyellow2", 
		"aquamarine2", "slategray4", "wheat3", "lightslategray", "orangered1", "thistle2", "none", 
		"mediumpurple", "lemonchiffon4", "slategray2", "goldenrod2", "lightskyblue4", "wheat2", 
		"lightblue2", "slateblue", "brown4", "turquoise2", "pink2", "wheat1", 
		"springgreen2", "seashell1", "darkslategray3", "hotpink", "honeydew1", 
		"lightgoldenrodyellow", "magenta2", "violetred4", "green1", 
		};
	
	public PossibleColors getPossibleColors() {
		return possibleColors;
	}
	
	public GCPartialSolution(GCInstance gcInstance)
	{
		this.gcInstance = gcInstance;
		this.possibleColors = new PossibleColors(gcInstance.getGraph());
		this.n = gcInstance.getGraph().getSize();
		colors = new int[n];
		for (int i = 0; i < n; i++)
		{
			colors[i] = -1;
		}
	}
	
	public void generateRandomSolution()
	{
		for (int i = 0; i < n; i++)
		{
			colors[i] = i;
			possibleColors.removePossibleColorFromNeighbors(this.getInstance().getGraph(), i, this.getInstance().getGraph().getNode(i));
		}
	}
	
	public int getInstanceSize()
	{
		return n;
	}
	
	public GCPartialSolution(GCInstance gcInstance, int[] colors, PossibleColors possibleColors)
	{
		this.gcInstance = gcInstance;
		this.n = gcInstance.getGraph().getSize();
		this.colors = colors;
		this.possibleColors = possibleColors;
	}

	public GCInstance getInstance()
	{
		return this.gcInstance;
	}
	
	public int[] getColors()
	{
		return this.colors;
	}
	
	public void setColors(int[] colors)
	{
		this.colors = colors;
	}
	
	public double getCost()
	{
		ArrayList<Integer> check = new ArrayList<Integer>();
		int cost = 0;
		for (int i = 0; i < n; i++)
		{
			if (colors[i] != (-1) && !check.contains(colors[i]))
			{
				cost++;
				check.add(colors[i]);
			}
		}
		return cost;
	}
	
	public void printColors()
	{
		for (int i = 0; i < n; i++)
		{
			System.out.println("Node " + i + ": color " + colors[i]);
		}
	}
	public void printGraphToGif(String pathToDotProgram, String pathOut)
	{
		  Graph graph = gcInstance.getGraph(); 
	      GraphViz gv = new GraphViz(pathToDotProgram);
	      gv.addln(gv.start_graph());
	      gv.addln("layout=dot;");
	      gv.addln("overlap=false;");
	      for (Node node : graph.getNodes())
	      {
	    	  for (Integer neig : node.adjacents)
	    	  {
	    		  if(node.id < neig)
	    		  {
	    			  gv.addln(node.id + " -> " + neig + " [dir=none];");
	    		  }
	    	  }
	    	  gv.addln(node.id + " [style=filled, fillcolor = " +getColorString(colors[node.id]) + "]");
	      }
	      gv.addln(gv.end_graph());
	     // System.out.println(gv.getDotSource());
	      
	      String type = "gif";
//	      String type = "dot";
//	      String type = "fig";    // open with xfig
//	      String type = "pdf";
//	      String type = "ps";
//	      String type = "svg";    // open with inkscape
//	      String type = "png";
//	      String type = "plain";
//	      File out = new File("/tmp/out." + type);   // Linux
	      File out = new File(pathOut + "/graphviz-java-api-out." + type);    // Windows
	      byte[] data = gv.getGraph( gv.getDotSource(), type );
	      //System.out.println("out " + out);
	      //System.out.println("data " + data.length + "#");
	      gv.writeGraphToFile( data, out );
	   }
	
		private String getColorString(int i)
		{
			if (i < vertexColors.length)
			{
				return vertexColors[i];
			}
			System.out.println("You are trying to use more colors than we support.");
			return vertexColors[vertexColors.length -1];
		}
		
		public boolean isComplete()
		{
			for (int i = 0; i < this.n; i++)
			{
				if (colors[i] == -1)
				{
					return false;
				}
			}
			return true;
		}
	
		public GCPartialSolution copy()
		{
			GCPartialSolution newSol = new GCPartialSolution(this.getInstance());
			
			for (int i = 0; i < this.getInstanceSize(); i++)
			{
				newSol.colors[i] = this.colors[i];
				newSol.possibleColors.removePossibleColorFromNeighbors(newSol.getInstance().getGraph(), 
																		newSol.colors[i], 
																		newSol.getInstance().getGraph().getNode(i));
			}
			
			
			return newSol;
		}
		
/*		
		public static void main(String[] args){
			ArrayList<String> cores = new ArrayList<String>();
			for (String cor : vertexColors)
			{
				cores.add(cor);
			}
			Collections.shuffle(cores);
			StringBuilder sb = new StringBuilder();
			String corrr = new String();

			int i = 0;
			for (String cor : cores)
			{
				if (i < 10)
				{
					i++;
					sb.append(cor + " ");
				}
				else
				{
					corrr = sb.toString();
					i = 0;
					System.out.println(corrr);
					sb = new StringBuilder();
				}
			}

			System.out.println(corrr);
		}
	*/
}
