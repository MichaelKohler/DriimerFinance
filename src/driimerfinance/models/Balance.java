package driimerfinance.models;

import java.util.ArrayList;
import java.util.List;

import driimerfinance.database.MandantDBHelper;

/**
 * 
 * 
 * (c) 2014 Driimer Finance
*/
public class Balance {
	
	/**
	 * Constructor
	 */
    public Balance() {
    }
    
    /**
     * Returns are the positions from the balance that are considered active.
     * 
     * @return list of all active positions
     */
    public List<Account> getActivePositions() {
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
     * Returns all the positions from the balance that are considered passive.
     * 
     * @return list of all passive positions
     */
    public List<Account> getPassivePositions() {
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
