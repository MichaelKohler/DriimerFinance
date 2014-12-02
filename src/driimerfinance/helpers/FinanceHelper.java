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
    
    /**
     * unformats a formatted number to be a double again
     * 
     * @param string number to convert
     * @return an unformatted number to be used for db interactions
     */
    public static double unformatAmount(String formattedNumber) {
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
        String currencyName = numberFormatter.getCurrency().getCurrencyCode();
        System.out.println(currencyName);
        formattedNumber = formattedNumber.replace(currencyName, "");
        formattedNumber = formattedNumber.replace("'", "");
        String unformattedStringNumber = formattedNumber.replace(",", "");
        System.out.println(unformattedStringNumber);
        double number = Double.parseDouble(unformattedStringNumber);
        return number;
    }
}
