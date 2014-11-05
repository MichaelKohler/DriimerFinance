package driimerfinance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
 */
public class DBConnection {
	private Connection connection = null;


	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection createConnection(String host, String databasename,
			String user, String password) {
		try {
			// setup the connection with the DB.
			System.out.println("Connecting to database");
			connection = DriverManager.getConnection("jdbc:mysql://" + host
					+ "/" + databasename + "?user=" + user + "&password="
					+ password);
			System.out.println("Success");
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
		}
		return connection;
	}

	public Connection getConnection(String host, String databasename,
			String user, String password) {
		return this.createConnection(host, databasename, user, password);
	}

	// you need to close all three to make sure
	public void close() {
		DBUtil.close(connection);
	}

	public void init() {

	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
