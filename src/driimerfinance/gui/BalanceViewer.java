package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;

/**
 * Shows the Balance in a two column view.
 * 
 * (c) 2014 Driimer Finance
*/
public class BalanceViewer {
	JFrame frame = new JFrame("DriimerFinance - Balance");
	
	/**
	 * Constructor
	 */
    public BalanceViewer() {
        createGUI();
    }
    
    /**
     * Creates the GUI.
     */
    private void createGUI() {
    	    GUIHelper.centerAndSizeFrame(this.frame);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		String[] headers = { "Aktiven", "Passiven" };
		Object[][] data = {  }; // TODO: call getData and transform it
		
		JTable accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);
		this.frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
    }
    
    /**
     * Crawls the database to get the data used for the balance.
     * 
     * @return data for the balance
     */
    private List<Account> getData() {
        MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        List<Account> filteredAccounts = new ArrayList<Account>();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 1 || typ == 2) {
        	    	    filteredAccounts.add(account);
        	    }
        }
        return filteredAccounts;
    }
}
