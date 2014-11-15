package driimerfinance.gui;

import java.util.ArrayList;

/**
 * Shows the Balance in a two column view.
 * 
 * (c) 2014 Driimer Finance
*/
public class BalanceViewer extends TwoColumnsViewer {
	/**
	 * Constructor
	 */
    public BalanceViewer() {
        super();
    }
    
    /**
     * Crawls the database to get the data used for the balance.
     * 
     * @return data for the balance
     */
    public ArrayList<String> getData() {
        return new ArrayList<String>();
    }
}
