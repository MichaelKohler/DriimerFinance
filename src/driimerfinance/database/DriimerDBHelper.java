package driimerfinance.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import driimerfinance.models.Mandant;
import driimerfinance.models.User;

/**
 * Useful helper methods for DB access and DB related tasks.
 */
public class DriimerDBHelper {
	private Connection dbconnection;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static String host = "localhost";
	private static String databasename = "driimerfinance";
	private static String user = "root";
	private static String password = "mysql";
	private DBConnection db = null;

	/**
	 * Constructor to initialize the Helper. It initializes the DB connection with the given properties.
	 */
	public DriimerDBHelper() {
		db = new DBConnection();
		dbconnection = db.createConnection(host, databasename, user, password);
	}

	/**
     * Helper method to get all users from the database.
     * 
     * @return user list of all users in the database
     */
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from user");
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("idUser"));
				user.setName(resultSet.getString("Name"));
				user.setVorname(resultSet.getString("Vorname"));
				user.setPassword(resultSet.getString("Password"));
				user.setUsername(resultSet.getString("username"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return users;
	}

	/**
     * Finds a user by id specified.
     * 
     * @param id to search
     * @return user which was found or an empty user
     */
	public User getUserById(int userId) {
		User user = new User();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from user where idUser=?");
			preparedStatement.setInt(1, userId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				user.setId(resultSet.getInt("idUser"));
				user.setName(resultSet.getString("Name"));
				user.setVorname(resultSet.getString("Vorname"));
				user.setPassword(resultSet.getString("Password"));
				user.setUsername(resultSet.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user;
	}

	/**
     * Adds a user to the database.
     * 
     * @param user to be added
     * @return void
     */
	public void addUser(User user) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into user(Name, Vorname, Password, username) values (?,?,?,?)");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getVorname());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
     * Deletes a user on the database.
     * 
     * @param user to be deleted
     * @return void
     */
	public void deleteUser(User user) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from users where idUser=?");
			preparedStatement.setInt(1, user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
     * Updates a specific user on the database.
     * 
     * @param user to be updated
     * @return void
     */
	public void updateUser(User user) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update user set Name=?, Vorname=?, Password=?, username=? where idUser=?");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getVorname());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
     * Returns all available mandants from the database.
     * 
     * @return list of all mandants
     */
	public List<Mandant> getAllMantanten() {
		List<Mandant> mandanten = new ArrayList<Mandant>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from mandanten");
			while (resultSet.next()) {
				Mandant mandant = new Mandant();
				mandant.setID(resultSet.getInt("idMandanten"));
				mandant.setName(resultSet.getString("Name"));
				mandant.setDBSchema(resultSet.getString("DBSchema"));
				mandanten.add(mandant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mandanten;
	}

	/**
     * Searches a mandant on the database with a given id.
     * 
     * @param id to search
     * @return found mandant if existing or new mandant
     */
	public Mandant getMandantById(int mandantId) {
		Mandant mandant = new Mandant();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from mandanten where idMandanten=?");
			preparedStatement.setInt(1, mandantId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				mandant.setID(resultSet.getInt("idMandanten"));
				mandant.setName(resultSet.getString("Name"));
				mandant.setDBSchema(resultSet.getString("DBSchema"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mandant;
	}

	/**
     * Adds a mandant to the database.
     * 
     * @param mandant to be added
     * @return void
     */
	public void addMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into mandanten(Name, DBSchema) values (?,?)");
			preparedStatement.setString(1, mandant.getName());
			preparedStatement.setString(2, mandant.getDBSchema());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
     * Deletes a mandant from the database.
     * 
     * @param mandant to be deleted
     * @return void
     */
	public void deleteMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from mandanten where idmandanten=?");
			preparedStatement.setInt(1, mandant.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void deleteMandantById(int mandantId) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from mandanten where idmandanten=?");
			preparedStatement.setInt(1, mandantId);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
     * Updates a specific mandant on the database.
     * 
     * @param mandant to be updated
     * @return void
     */
	public void updateMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update mandanten set Name=?, DBSchema=? where idMandanten=?");
			preparedStatement.setString(1, mandant.getName());
			preparedStatement.setString(2, mandant.getDBSchema());
			preparedStatement.setInt(3, mandant.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
     * Closes all open database connections.
     * 
     * @return void
     */
	public void closeConnection() {
		close();
		db.close();
	}

	/**
     * Closes all database related connections.
     *
     * @return void
     */
	private void close() {
		if (statement != null) {
			DBUtil.close(statement);
		}
		if (resultSet != null) {
			DBUtil.close(resultSet);
		}
	}

}
