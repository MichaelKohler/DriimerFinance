package driimerfinance.models;

import java.util.List;

import javax.swing.JOptionPane;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.gui.BalanceViewer;
import driimerfinance.gui.ERViewer;
import driimerfinance.gui.JournalWindow;

/**
 * Symbolic class to do the yearly annual accounts step.
 * 
 * (c) 2014 Driimer Finance
*/
public class AnnualAccounts {
	/**
	 * Constructor
	 */
    public AnnualAccounts() {
    }
    
    /**
     * Processes the earnings accordingly.
     */
    public void processEarnings() {
    		MandantDBHelper helper = new MandantDBHelper();
    		Account capAccount = helper.getCapitalAccount();
    		
    		if (capAccount == null) {
    			JOptionPane.showMessageDialog(null, "Es ist kein Kapitalkonto erfasst!", "Fehler", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		// calculate win and act on it
    		double win = calculateTotalEarning() - calculateTotalSpending();
    		if (win > 0) {
    			capAccount.setBalance(capAccount.getBalance() + win);
    		} else if (win == 0) {
    			return;
    		} else {
    			capAccount.setBalance(capAccount.getBalance() - win);
    		}
    		capAccount.updateInDB();
    }
    
    /**
     * Calculates the total spending
     */
    public double calculateTotalSpending() {
    		double totalSpending = 0;
    		ER er = new ER();
        List<Account> spending = er.getSpendingPositions();
        for (Account acc : spending) {
        		totalSpending += acc.getBalance();
        }
        return totalSpending;
    }
    
    /**
     * Calculates the total earning
     */
    public double calculateTotalEarning() {
    		double totalEarning = 0;
	    	ER er = new ER();
	    	List<Account> earning = er.getEarningPositions();
	    	for (Account acc : earning) {
        		totalEarning += acc.getBalance();
        }
	    	return totalEarning;
    }
    
    /**
     * Creates all the needed statements as PDF
     */
    public void createStatements() {
    		ERViewer erViewer = new ERViewer(false);
    		erViewer.exportPDF();
    		
    		BalanceViewer balanceViewer = new BalanceViewer(false);
    		balanceViewer.exportPDF();
    		
    		JournalWindow journalWindow = new JournalWindow(false);
    		journalWindow.exportPDF();
    }
    
    /**
     * Set balance for all the spending positions to 0.
     */
    public void clearSpendingPositions() {
    	    MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 4) { // Typ: Aufwand
        	    	   account.setBalance(0);
        	    	   account.updateInDB();
        	    }
        }
    }
    
    /**
     * Set balance for all the earning positions to 0.
     */
    public void clearEarningPositions() {
        MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 3) { // Typ: Ertrag
        	    	    account.setBalance(0);
        	    	    account.updateInDB();
        	    }
        }
    }
}
