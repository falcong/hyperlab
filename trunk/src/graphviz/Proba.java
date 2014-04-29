package graphviz;
import java.io.File;

public class Proba
{
   public static void main(String[] args)
   {
      Proba p = new Proba();
      p.start();
//      p.start2();
   }

   /**
    * Construct a DOT graph in memory, convert it
    * to image and store the image in the file system.
    */
   private void start()
   {
      GraphViz gv = new GraphViz("C:/Program Files (x86)/Graphviz2.34/bin/dot.exe");
      gv.addln(gv.start_graph());
      gv.addln("A -> B;");
      gv.addln("A -> C;");
      gv.addln(gv.end_graph());
      System.out.println(gv.getDotSource());
      
      String type = "gif";
//      String type = "dot";
//      String type = "fig";    // open with xfig
//      String type = "pdf";
//      String type = "ps";
//      String type = "svg";    // open with inkscape
//      String type = "png";
//      String type = "plain";
//      File out = new File("/tmp/out." + type);   // Linux
      File out = new File("C:/Users/Eduardo/Desktop/graphviz-java-api/sample/graphviz-java-api-out." + type);    // Windows
      byte[] data = gv.getGraph( gv.getDotSource(), type );
      System.out.println("out " + out);
      System.out.println("data " + data.length + "#");
      gv.writeGraphToFile( data, out );
   }
   
   /**
    * Read the DOT source from a file,
    * convert to image and store the image in the file system.
    */
   @SuppressWarnings("unused")
private void start2()
   {
//      String dir = "/home/jabba/eclipse2/laszlo.sajat/graphviz-java-api";     // Linux
//      String input = dir + "/sample/simple.dot";
	   String input = "C:/Users/Eduardo/Desktop/graphviz-java-api/sample/simple.dot";    // Windows
	   
	   GraphViz gv = new GraphViz("C:/Program Files (x86)/Graphviz2.34/bin/dot.exe");
	   gv.readSource(input);
	   System.out.println(gv.getDotSource());
   		
      String type = "gif";
//    String type = "dot";
//    String type = "fig";    // open with xfig
//    String type = "pdf";
//    String type = "ps";
//    String type = "svg";    // open with inkscape
//    String type = "png";
//      String type = "plain";
	   File out = new File("/tmp/simple." + type);   // Linux
//	   File out = new File("c:/eclipse.ws/graphviz-java-api/tmp/simple." + type);   // Windows
	   gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
   }
}
