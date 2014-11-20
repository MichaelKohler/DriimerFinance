package driimerfinance.database;

/**
 * Global information about the current database connection
 * 
 * THIS IS A SINGLETON!
 * 
 * (c) 2014 Driimer Finance
*/
public class Globals {
	public String host = "localhost";
	public String  databasename = "Mandant";
	public String user = "root";
	public String password = "mysql";
	
	private static Globals instance = null;
	
	/**
	 * changes the mandant in the background
	 * 
	 * @param mandantname to change to
	 */
	public void changeMandant(String mandantName) {
		databasename = mandantName;
	}
	
	/**
	 * returns the Globals instance or creates one if not already defined
	 * 
	 * @param mandantname to change to
	 */
	public static Globals getInstance() {
		if (instance == null) {
			instance = new Globals();
		}
		return instance;
	}
}
