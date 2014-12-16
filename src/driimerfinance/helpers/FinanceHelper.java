package driimerfinance.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import driimerfinance.models.Account;
import driimerfinance.models.AccountTypeEnum;

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
        Number number = null;
		try {
			number = numberFormatter.parse(formattedNumber);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        double unformattedNumber = number.doubleValue();
        return unformattedNumber;
    }
    
    /**
	 * Checks if the license is valid using the License Key Server
	 * 
	 * @param license key to check
	 * @return validity of the license (true or false)
	 */
	public static boolean checkLicense(String licenseKey) {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://driimerfinance.michaelkohler.info/checkLicense");
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("key", licenseKey)); // pass the key to the server
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return false;
		}

		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost); // get the response from the server
		} catch (IOException e) {
			return false;
		}
		HttpEntity entity = response.getEntity();

		if (entity != null) {
		    InputStream instream = null;
			try {
				instream = entity.getContent();
				@SuppressWarnings("resource")
				java.util.Scanner s = new java.util.Scanner(instream).useDelimiter("\\A"); // parse the response
			    String responseString =  s.hasNext() ? s.next() : "";
			    instream.close();
			    return responseString.contains("\"valid\":true"); // if the license is valid, return true, else false
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Calculates the new amounts of the given accounts depending on the amount specified. This also
	 * takes into account that there are different business cases.
	 * 
	 * @param soll account to use for calculation
	 * @param haben account to use for calculation
	 * @param amount to be used as base
	 */
	public static void calculateAccountAmounts(Account soll, Account haben, double amount) {
		double newSollBalance = 0.00;
		double newHabenBalance = 0.00;
		int sollType = soll.getFk_AccountType();
		int habenType = haben.getFk_AccountType();
		
		// Aktiv  Aktiv    -> Höher  Tiefer
		// Aktiv  Ertrag   -> Höher  Tiefer
		// Aufwand Aktiv  -> Höher  Tiefer
		// Aufwand Ertrag -> Höher  Tiefer
		if ((sollType == AccountTypeEnum.ACTIVE.getId() || sollType == AccountTypeEnum.SPENDING.getId()) &&
				(habenType == AccountTypeEnum.ACTIVE.getId() || habenType == AccountTypeEnum.EARNING.getId())) {
			newSollBalance = soll.getBalance() + amount;
			newHabenBalance = haben.getBalance() - amount;
		}
		
		// Aktiv  Passiv    -> Höher  Höher
		// Aktiv  Aufwand   -> Höher  Höher
		// Aufwand Passiv  -> Höher  Höher
		// Aufwand Aufwand -> Höher  Höher
		if ((sollType == AccountTypeEnum.ACTIVE.getId() || sollType == AccountTypeEnum.SPENDING.getId()) &&
				(habenType == AccountTypeEnum.PASSIVE.getId() || habenType == AccountTypeEnum.SPENDING.getId())) {
			newSollBalance = soll.getBalance() + amount;
			newHabenBalance = haben.getBalance() + amount;
		}
		
		// Passiv  Aktiv    -> Tiefer  Tiefer
		// Passiv  Ertrag   -> Tiefer  Tiefer
		// Ertrag Aktiv  -> Tiefer  Tiefer
		// Ertrag Ertrag -> Tiefer  Tiefer
		if ((sollType == AccountTypeEnum.PASSIVE.getId() || sollType == AccountTypeEnum.EARNING.getId()) &&
				(habenType == AccountTypeEnum.ACTIVE.getId() || habenType == AccountTypeEnum.EARNING.getId())) {
			newSollBalance = soll.getBalance() - amount;
			newHabenBalance = haben.getBalance() - amount;
		}
		
		// Passiv  Passiv    -> Tiefer  Höher
		// Passiv  Aufwand   -> Tiefer  Höher
		// Ertrag Passiv  -> Tiefer  Höher
		// Ertrag Aufwand -> Tiefer  Höher
		if ((sollType == AccountTypeEnum.PASSIVE.getId() || sollType == AccountTypeEnum.EARNING.getId()) &&
				(habenType == AccountTypeEnum.PASSIVE.getId() || habenType == AccountTypeEnum.SPENDING.getId())) {
			newSollBalance = soll.getBalance() - amount;
			newHabenBalance = haben.getBalance() + amount;
		}
		
		soll.setBalance(newSollBalance);
		soll.updateInDB();
		haben.setBalance(newHabenBalance);
		haben.updateInDB();
	}
}
