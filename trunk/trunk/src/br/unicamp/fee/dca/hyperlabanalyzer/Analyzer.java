package br.unicamp.fee.dca.hyperlabanalyzer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Analyzer
{
    public static void main(String[] args)
    {
    	try
    	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
    	catch (Exception e)
    	{
    		System.err.println("Warning: Could not set native look and feel.");
		}

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
            	MainFrame ex = new MainFrame();
                ex.setVisible(true);
            }
        });
    }
}
