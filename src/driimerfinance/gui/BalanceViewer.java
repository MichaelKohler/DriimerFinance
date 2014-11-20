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
    	    this.frame.setSize(500, 400);
    	    GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		String[] headers = { "Aktiven", "Passiven" };
		Object[][] data = prepareData();
		
		JTable accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(490, 390));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);
		this.frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
    }
    
    /**
     * Prepares the data for display.
     * 
     * @return data for display
     */
    private Object[][] prepareData() {
        List<Account> active = getActiveData();
        List<Account> passive = getPassiveData();
        
        int maxLength = active.size() >= passive.size() ? active.size() : passive.size();
        int columns = 2;
        Object[][] rows = new Object[maxLength][columns];
        for (int i = 0; i < maxLength; i++) {
        	    String nameActive = "";
        	    if (i < active.size()) {
        	    	    nameActive = active.get(i).getName();
        	    }
        	    String namePassive = "";
        	    if (i < passive.size()) {
    	    	        namePassive = passive.get(i).getName();
    	        }
        	    Object[] names = { nameActive, namePassive };
        	    rows[i] = names;
        }
        return rows;
    }
    
    /**
     * Crawls the database to get the data used for the active balance.
     * 
     * @return data for the balance
     */
    private List<Account> getActiveData() {
        MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        List<Account> filteredAccounts = new ArrayList<Account>();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 1) {
        	    	    filteredAccounts.add(account);
        	    }
        }
        return filteredAccounts;
    }
    
    /**
     * Crawls the database to get the data used for the passive balance.
     * 
     * @return data for the balance
     */
    private List<Account> getPassiveData() {
        MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        List<Account> filteredAccounts = new ArrayList<Account>();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 2) {
        	    	    filteredAccounts.add(account);
        	    }
        }
        return filteredAccounts;
    }
}
