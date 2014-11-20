package driimerfinance.helpers;

import java.text.NumberFormat;

/**
 * Useful methods for financial tasks.
 * 
 * (c) 2014 Driimer Finance
*/
public class FinanceHelper {
	
	/**
     * Creates a formatted string from the number input.
     * 
     * @param double number to convert
     * @return a formatted number to be put on display for the user
     */
    public static String formatAmount(double number) {
	    	NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
	    	String formattedString = numberFormatter.format(number);
	    	return formattedString;
    }
}
