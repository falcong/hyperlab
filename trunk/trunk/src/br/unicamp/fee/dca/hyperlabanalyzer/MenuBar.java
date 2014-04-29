package br.unicamp.fee.dca.hyperlabanalyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import br.unicamp.fee.dca.hyperlabanalyzer.MainFrame.AppState;

public class MenuBar extends JMenuBar
{
	private MainFrame mMainFrame;
	private JMenu mFileMenu;
	private JMenu mPerspectiveMenu;
	private Map<MainFrame.AppState, JMenuItem> mPerspectiveButtons;
	
	public MenuBar(MainFrame mainFrame)
	{
		mMainFrame = mainFrame;
		mMainFrame.setJMenuBar(this);
		
		initFileMenu();
		initPerspectiveMenu();
	}

	private void initFileMenu() {
		mFileMenu = new JMenu("File");
		mFileMenu.setMnemonic(KeyEvent.VK_F);
		this.add(mFileMenu);
		
		JMenuItem menuItem = new JMenuItem("Open Results File", KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mMainFrame.showOpenFileDialog();
			}
		});
		mFileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		mFileMenu.add(menuItem);
	}

	private void initPerspectiveMenu() {
		mPerspectiveMenu = new JMenu("Perspective");
		mPerspectiveMenu.setMnemonic(KeyEvent.VK_P);
		this.add(mPerspectiveMenu);
		
		ButtonGroup group = new ButtonGroup();
		
		mPerspectiveButtons = new HashMap<AppState, JMenuItem>();

		createRadioButtonForPerspective(group, "Welcome screen",
				KeyEvent.VK_W, AppState.EMPTY);
		createRadioButtonForPerspective(group, "Compare Hyper-Heuristics",
				KeyEvent.VK_H, AppState.COMPARE_HYPER_HEURISTICS);
		createRadioButtonForPerspective(group, "Compare Problems",
				KeyEvent.VK_P, AppState.COMPARE_PROBLEMS);
		createRadioButtonForPerspective(group, "Compare Problems (line graph)",
				KeyEvent.VK_L, AppState.PROBLEMS_LINE_GRAPH);
		createRadioButtonForPerspective(group, "Statistics Table",
				KeyEvent.VK_S, AppState.STATISTICS_TABLE_PRETTY);
		createRadioButtonForPerspective(group, "Statistics Table (raw)",
				KeyEvent.VK_R, AppState.STATISTICS_TABLE_RAW);

		notifyStateChanged(AppState.EMPTY);
	}

	private void createRadioButtonForPerspective(ButtonGroup group,
			String label, int keyBind, final AppState state) {
		JMenuItem rbMenuItem = new JRadioButtonMenuItem(label);
		rbMenuItem.setMnemonic(keyBind);
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				AppState newState = mMainFrame.setPerspective(state);
				notifyStateChanged(newState);
			}
		});
		mPerspectiveMenu.add(rbMenuItem);
		mPerspectiveButtons.put(state, rbMenuItem);
	}

	public void notifyStateChanged(AppState newState)
	{
		mPerspectiveButtons.get(newState).setSelected(true);
	}
}
