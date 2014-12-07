package driimerfinance.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import javax.swing.JFileChooser;

/**
 * Special case: this will save a SQL file with all the possible data from the database. This can be used as a backup.
 * 
 * (c) 2014 Driimer Finance
*/
public class RawDataExporter extends Exporter {
	private String host = "";
	private String username = "";
	private String password = "";
	private String dbname = "";
	
	/**
	 * Constructor
	 */
    public RawDataExporter() {
    	    setDBPreferences();
    }
    
    /**
     * Gets the db preferences and set's them to the variables for later use.
     */
    private void setDBPreferences() {
	    	Properties properties = new Properties();
	    	try {
	    	  properties.load(new FileInputStream(new File("bin/driimerfinance/database/database.properties").getAbsoluteFile()));
	    	  host = properties.getProperty("host");
	  	  username = properties.getProperty("user");
	  	  dbname = properties.getProperty("databasename");
	  	  password = properties.getProperty("password");
	    	} catch (IOException e) {
	    	  e.printStackTrace();
	    	}
    }
    
    /**
     * Exports the crawled data to a file on the file system.
     * 
     * @param path the user has chosen
     * @return void
     */
    public void exportToFile(String path) {
    	    String filename = path + File.separator + dbname + ".sql";
    	    String pathToDump = new File("lib/mysqldump").getAbsolutePath();
    	    String command = pathToDump + " -u " + username + " -p" + password + " " + dbname;
		try {
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(proc.getInputStream()));
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			String tmp = "";
			while ((tmp = stdInput.readLine()) != null) {
			    writer.println(tmp + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
