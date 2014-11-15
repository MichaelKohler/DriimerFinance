package driimerfinance.models;

import java.util.ArrayList;

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
    public ArrayList<Transaction> getSpendingPositions() {
        return new ArrayList<Transaction>();
    }
    
    /**
     * Returns all the earning positions.
     * 
     * @return list of all earning positions
     */
    public ArrayList<Transaction> getEarningPositions() {
        return new ArrayList<Transaction>();
    }
}
