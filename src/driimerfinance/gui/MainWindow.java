package driimerfinance.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	}

	private void createGUI() {
		GUIHelper.centerAndSizeFrame(_frame);
		addMenubar();
		_frame.setVisible(true);
	}

	private void addMenubar() {
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Applikation schliessen");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		file.add(eMenuItem);

		JMenu mandanten = new JMenu("Mandanten");
		mandanten.setMnemonic(KeyEvent.VK_F);

		JMenuItem addMenuItem = new JMenuItem("Hinzufügen...");
		addMenuItem.setMnemonic(KeyEvent.VK_H);
		addMenuItem.setToolTipText("Mandant hinzufügen");
		addMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				AddMandantWindow addMandantWindow = new AddMandantWindow();
			}
		});
		mandanten.add(addMenuItem);
		JMenuItem changeMenuItem = new JMenuItem("Wechseln...");
		changeMenuItem.setMnemonic(KeyEvent.VK_W);
		changeMenuItem.setToolTipText("Mandant wechseln");
		changeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO: change Mandant
			}
		});
		mandanten.add(changeMenuItem);
		
		JMenu buchungen = new JMenu("Buchungen");
		file.setMnemonic(KeyEvent.VK_B);

		JMenuItem buchungItem = new JMenuItem("Buchung hinzufügen...");
		buchungItem.setMnemonic(KeyEvent.VK_H);
		buchungItem.setToolTipText("Buchung hinzufügen");
		buchungItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				AddTransactionWindow addTransactionWindow = new AddTransactionWindow();
			}
		});
		buchungen.add(buchungItem);

		menubar.add(file);
		menubar.add(mandanten);
		menubar.add(buchungen);
		_frame.setJMenuBar(menubar);
	}
}
