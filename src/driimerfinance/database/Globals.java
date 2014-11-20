package driimerfinance.database;

/**
 * Global information about the current database connection
 * 
 * (c) 2014 Driimer Finance
*/
public class Globals {
	public static String host = "localhost";
	public static String  databasename = "mandant";
	public static String user = "root";
	public static String password = "mysql";
	
	/**
	 * changes the mandant in the background
	 * 
	 * @param mandantname to change to
	 */
	public static void changeMandant(String mandantName) {
		databasename = mandantName;
	}
}
