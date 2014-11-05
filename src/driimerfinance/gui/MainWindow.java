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
		JMenu mandantenChangeMenu = new JMenu("Wechseln...");
		mandantenChangeMenu.setMnemonic(KeyEvent.VK_W);
		JMenuItem mandant1MenuItem = new JMenuItem("Mandant 1");
		mandant1MenuItem.setMnemonic(KeyEvent.VK_M);
		mandant1MenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO: change Mandant
			}
		});
		JMenuItem mandant2MenuItem = new JMenuItem("Mandant 2");
		mandant2MenuItem.setMnemonic(KeyEvent.VK_M);
		mandant2MenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO: change Mandant
			}
		});
		mandantenChangeMenu.add(mandant1MenuItem);
		mandantenChangeMenu.add(mandant2MenuItem);
		mandanten.add(mandantenChangeMenu);
		
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
		
		JMenu accountPlan = new JMenu("Kontenplan");
		file.setMnemonic(KeyEvent.VK_K);

		JMenuItem editAccountPlan = new JMenuItem("Kontenplan editieren...");
		editAccountPlan.setMnemonic(KeyEvent.VK_H);
		editAccountPlan.setToolTipText("Kontenplan editieren");
		editAccountPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				AccountPlanWindow accPlanWindow = new AccountPlanWindow();
			}
		});
		accountPlan.add(editAccountPlan);

		menubar.add(file);
		menubar.add(mandanten);
		menubar.add(buchungen);
		menubar.add(accountPlan);
		_frame.setJMenuBar(menubar);
	}
}
