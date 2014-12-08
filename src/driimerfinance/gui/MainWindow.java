package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

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
	ImageIcon icon = new ImageIcon("images/DF.png");
	
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
		this.frame.setIconImage(icon.getImage());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);    
	}

	/**
	 * Adds the menu bar to the window.
	 */
	private void addMenubar() {
		JMenuBar menubar = MenuBarSingleton.getMenuBarInstance();
		this.frame.setJMenuBar(menubar);
		MenuBarSingleton.setEnabled(false);
	}
	
	/**
	 * Adds the content
	 */
	private void addForm() {
		JPanel formPanel = new JPanel();
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JScrollPane pane = new JScrollPane(formPanel);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		DriimerDBHelper driimerdb = new DriimerDBHelper();
		List<Mandant> mandanten = driimerdb.getAllMantanten();
		int numberOfColumns = mandanten.size();
		GridLayout layout = new GridLayout(numberOfColumns, 4);
		formPanel.setLayout(layout);
		for ( Mandant mandant : mandanten ) {
			JButton manantButton = makeButton(mandant);
			formPanel.add(manantButton);
		}
		this.frame.getContentPane().add(pane, BorderLayout.CENTER);
	}
	
	private JButton makeButton(final Mandant mandant) {
		JButton button = new JButton(mandant.getName());
		button.addActionListener(new ChangeMandantAction(mandant));
		return button;
	}
	
	public void reload(){
		System.out.println("relaod mainwindow");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		MainWindowSingleton.main = null;
		System.out.println("frame nulled");
		MainWindowSingleton.getMainWindowInstance();
	}
	
	public class ChangeMandantAction implements ActionListener {
		Mandant mandant = null;
		
		public ChangeMandantAction(Mandant mandant) {
			this.mandant = mandant;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			mandant.setCurrentWorkingMandant();
				JMenuBar menubar = MenuBarSingleton.getMenuBarInstance();
			JMenu mandantmenu = menubar.getMenu(menubar.getMenuCount()-1);
			mandantmenu.setText("Mandant: " + mandant.getName());
		}
	}
}
