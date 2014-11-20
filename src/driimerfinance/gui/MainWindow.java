package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.javafx.tk.Toolkit;

import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;
import driimerfinance.database.DriimerDBHelper;

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
	}

	/**
     * Creates the GUI
     */
	private void createGUI() {
		GUIHelper.centerAndSizeFrame(this.frame);
		addMenubar();
		addForm();
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
	
	/**
	 * Adds the content
	 */
	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(3,4);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		DriimerDBHelper driimerdb = new DriimerDBHelper();
		List<Mandant> mandanten = driimerdb.getAllMantanten();
		for ( Mandant mandant : mandanten ) {
			JButton manantButton = new JButton(mandant.getName());
			formPanel.add(manantButton);
			
		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
		}
	}
}
