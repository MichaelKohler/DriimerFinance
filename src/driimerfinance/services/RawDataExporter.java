package driimerfinance.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Special case: this will save a SQL file with all the possible data from the
 * database. This can be used as a backup.
 * 
 * (c) 2014 Driimer Finance
 */
public class RawDataExporter extends Exporter implements Runnable {
	private String host = "";
	private String username = "";
	private String password = "";
	private String dbname = "";
	private String path = "";

	/**
	 * Constructor
	 */
	public RawDataExporter(String path) {
		setDBPreferences();
		this.path = path;
	}

	/**
	 * Gets the db preferences and set's them to the variables for later use.
	 */
	private void setDBPreferences() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(
					"bin/driimerfinance/database/database.properties")
					.getAbsoluteFile()));
			host = properties.getProperty("host");
			username = properties.getProperty("user");
			dbname = properties.getProperty("databasename");
			password = properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String system = System.getProperty("os.name");
		String pathToDump = "";
		if (system.contains("Mac OS X")) {
			pathToDump = new File("lib/mysqldump").getAbsolutePath();
		}
		else if (system.contains("Windows")) {
			pathToDump = new File("lib/mysqldump.exe").getAbsolutePath();
		}
		String command = pathToDump + " -u " + username + " -p" + password + " " + dbname;
		System.out.println(command);
		try {
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String filename = path + File.separator + dbname + ".sql";
			System.out.println(filename);
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
