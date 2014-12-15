package driimerfinance.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Useful DB helper methods
 */
public class DBUtil {

	/**
     * Closes the connection to the database.
     * 
     * @param connection specifies the connection to be closed
     * @return void
     */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}

		try {
			if (connection.isClosed()) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * Closes a statement on the database.
     * 
     * @param statement which needs to be closed
     * @return void
     */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				/* Ignore */
			}
		}

		try {
			if (statement.isClosed()) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * Closes a ResultSet
     * 
     * @param resultSet to be closed
     * @return void
     */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}

		try {
			if (resultSet.isClosed()) {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
