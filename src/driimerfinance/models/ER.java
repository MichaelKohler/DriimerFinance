package driimerfinance.models;

import java.util.ArrayList;
import java.util.List;

import driimerfinance.database.MandantDBHelper;

/**
 * Erfolgsrechnung
 * 
 * (c) 2014 Driimer Finance
*/
public class ER {
	
	/**
	 * Constructor
	 */
    public ER() {
        
    }
    
    /**
     * Returns all the spending positions.
     * 
     * @return list of all spending positions
     */
    public List<Account> getSpendingPositions() {
    	    MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        List<Account> filteredAccounts = new ArrayList<Account>();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 4) { // Typ: Aufwand
        	    	    filteredAccounts.add(account);
        	    }
        }
        return filteredAccounts;
    }
    
    /**
     * Returns all the earning positions.
     * 
     * @return list of all earning positions
     */
    public List<Account> getEarningPositions() {
        MandantDBHelper helper = new MandantDBHelper();
        List<Account> allAccounts = helper.getAllAccounts();
        List<Account> filteredAccounts = new ArrayList<Account>();
        for (Account account : allAccounts) {
        	    int typ = account.getFk_AccountType();
        	    if (typ == 3) { // Typ: Ertrag
        	    	    filteredAccounts.add(account);
        	    }
        }
        return filteredAccounts;
    }
}
