package driimerfinance.models;

import java.util.ArrayList;

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
    public ArrayList<Account> getActivePositions() {
        return new ArrayList<Account>();
    }
    
    /**
     * Returns all the positions from the balance that are considered passive.
     * 
     * @return list of all passive positions
     */
    public ArrayList<Account> getPassivePositions() {
        return new ArrayList<Account>();
    }
}
