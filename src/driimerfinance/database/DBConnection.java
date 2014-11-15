package driimerfinance.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection connects to the Database and enables easy access to it.
 * 
 * (c) 2014 Driimer Finance
 */
public class DBConnection {
	private Connection connection = null;

	/**
     * Constructor initializing the JDBC driver 
     */
	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
     * Creates a connection to the specified host and connects to the database.
     * 
     * @param host Address of the host where the DB is running
     * @param databasename Name of the database to connect to
     * @param user username of the db user
     * @param password password of the db user
     * @return connection returns the connection once established
     */
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

	/**
     * Returns the current connection. This can be used if you don't want to reconnect again.
     * Warning: only usable if connection exists!
     * 
     * @param host Address of the host where the DB is running
     * @param databasename Name of the database to connect to
     * @param user username of the db user
     * @param password password of the db user
     * @return the established connection if one is established, otherwise null
     */
	public Connection getConnection(String host, String databasename,
			String user, String password) {
		return this.connection;
	}

	/**
     * Closes the database connection.
     * 
     * @return void
     */
	public void close() {
		DBUtil.close(connection);
	}

	/**
     * Sets the main connection to the database to a new connection.
     * 
     * @param connection 
     * @return the connection once established
     */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
