package driimerfinance.models;


import java.util.List;

import driimerfinance.database.MandantDBHelper;

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
     * Finishes everything for this year and opens a new year for accounting
     */
    public void doAnnualAccounts() {
        
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
        	    }
        }
    }
    
    
    
}
