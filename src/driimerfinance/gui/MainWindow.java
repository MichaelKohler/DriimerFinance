package driimerfinance.gui;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import driimerfinance.helpers.GUIHelper;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
 */
public class MainWindow {

	JFrame _frame = new JFrame("DriimerFinance");

	public MainWindow() {
		createGUI();
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
	}

	private void createGUI() {
		GUIHelper.centerAndSizeFrame(_frame);
		addMenubar();
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
	}

	private void addMenubar() {
		JMenuBar menubar = MenuBarProvider.getMenuBarInstance();
		_frame.setJMenuBar(menubar);
	}
}
