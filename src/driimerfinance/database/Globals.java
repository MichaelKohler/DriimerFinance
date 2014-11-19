package driimerfinance.database;

/**
 * Global information about the current database connection
 * 
 * (c) 2014 Driimer Finance
*/
public class Globals {
	public static String host = "";
	public static String  databasename = "";
	public static String user = "";
	public static String password = "";
	
	/**
	 * changes the mandant in the background
	 * 
	 * @param mandantname to change to
	 */
	public static void changeMandant(String mandantName) {
		databasename = mandantName;
	}
}
