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
 * Erfolgsrechnung overview window
 * 
 * (c) 2014 Driimer Finance
*/
public class ERViewer {
	JFrame frame = new JFrame("DriimerFinance");
	
	/**
	 * Constructor
	 */
    public ERViewer() {
        createGUI();
    }
    
    /**
     * Creates the GUI.
     */
    private void createGUI() {
    	    GUIHelper.centerFrame(this.frame);
    	    this.frame.setSize(500, 400);
		this.frame.setVisible(true);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		String[] headers = { "Aufwand", "Ertrag" };
		Object[][] data = {  }; // TODO: call getData and transform it
		
		JTable accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);
		this.frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
    }
    
    /**
     * Crawls the database to get the data used for the ER.
     * 
     * @return data for the ER
     */
    private List<Account> getData() {
        MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        List<Account> filteredAccounts = new ArrayList<Account>();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 3 || typ == 4) {
        	    	    filteredAccounts.add(account);
        	    }
        }
        return filteredAccounts;
    }
}
