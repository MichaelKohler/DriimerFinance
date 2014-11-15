package driimerfinance.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class MenuBarProvider {
	private static JMenuBar menuBar;
    
    private static JMenuBar createGUI() {
    	    JMenuBar bar = new JMenuBar();
    	
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
				new AddMandantWindow();
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
				new AddTransactionWindow();
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
				new AccountPlanWindow();
			}
		});
		accountPlan.add(editAccountPlan);

		bar.add(file);
		bar.add(mandanten);
		bar.add(buchungen);
		bar.add(accountPlan);
		return bar;
    }
    
    public static JMenuBar getMenuBarInstance() {
    	    if (menuBar != null) {
            return menuBar;
    	    }
    	    else {
    	    	    return createGUI();
    	    }
    }
}
