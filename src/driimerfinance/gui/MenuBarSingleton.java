package driimerfinance.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.models.Mandant;

/**
 * Singleton which returns a new menu bar or the existing one. This is included in different windows.
 * 
 * (c) 2014 Driimer Finance
*/
public class MenuBarSingleton {
	private static JMenuBar menuBar;
    
	/**
     * Creates the GUI
     */
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

		JMenu mandants = new JMenu("Mandanten");
		mandants.setMnemonic(KeyEvent.VK_M);

		JMenuItem addMenuItem = new JMenuItem("Hinzuf\u00fcgen...");
		addMenuItem.setMnemonic(KeyEvent.VK_H);
		addMenuItem.setToolTipText("Mandant hinzuf\u00fcgen");
		addMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new AddMandantWindow();
			}
		});
		mandants.add(addMenuItem);
		JMenu mandantChangeMenu = new JMenu("Wechseln...");
		mandantChangeMenu.setMnemonic(KeyEvent.VK_W);
		
		DriimerDBHelper driimerdb = new DriimerDBHelper();
		
		List<Mandant> mandanten = driimerdb.getAllMantanten();
		for ( Mandant mandant : mandanten) {
			JMenuItem menuItem = new JMenuItem(mandant.getName());
			menuItem.setMnemonic(KeyEvent.VK_M);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					// TODO: change Mandant
				}
			});
			
			mandantChangeMenu.add(menuItem);
			mandants.add(mandantChangeMenu);
		}
		mandants.add(mandantChangeMenu);
		
		JMenu transactions = new JMenu("Buchungen");
		transactions.setMnemonic(KeyEvent.VK_B);

		JMenuItem transactionItem = new JMenuItem("Buchung hinzuf\u00fcgen...");
		transactionItem.setMnemonic(KeyEvent.VK_H);
		transactionItem.setToolTipText("Buchung hinzuf\u00fcgen");
		transactionItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				new AddTransactionWindow();
			}
		});
		transactions.add(transactionItem);
		
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
		bar.add(mandants);
		bar.add(transactions);
		bar.add(accountPlan);
		return bar;
    }
    
    /**
     * Returns the singleton's instance.
     * 
     * @return singleton with the menu bar
     */
    public static JMenuBar getMenuBarInstance() {
    	    if (menuBar != null) {
            return menuBar;
    	    }
    	    else {
    	    	    return createGUI();
    	    }
    }
}
