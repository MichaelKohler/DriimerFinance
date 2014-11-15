package driimerfinance.gui;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import driimerfinance.helpers.GUIHelper;

/**
 * This is the main screen of the application.
 * 
 * (c) 2014 Driimer Finance
 */
public class MainWindow {

	JFrame frame = new JFrame("DriimerFinance");

	/**
	 * Constructor
	 */
	public MainWindow() {
		createGUI();
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
	}

	/**
     * Creates the GUI
     */
	private void createGUI() {
		GUIHelper.centerAndSizeFrame(this.frame);
		addMenubar();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}

	/**
	 * Adds the menu bar to the window.
	 */
	private void addMenubar() {
		JMenuBar menubar = MenuBarSingleton.getMenuBarInstance();
		this.frame.setJMenuBar(menubar);
	}
}
