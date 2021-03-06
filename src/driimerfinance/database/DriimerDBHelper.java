package driimerfinance.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import driimerfinance.models.Mandant;
import driimerfinance.models.User;

/**
 * Useful helper methods for DB access and DB related tasks.
 * 
 * (C) 2014 Driimer Finance
 */
public class DriimerDBHelper {
	private Connection dbconnection;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static String host = "";
	private static String databasename = "driimerfinance";
	private static String user = "";
	private static String password = "";
	private DBConnection db = null;

	/**
	 * Constructor
	 * 
	 * @param connection to use
	 */
	public DriimerDBHelper(Connection connection) {
		this.dbconnection = connection;
	}
	
	/**
	 * Constructor to initialize the Helper. It initializes the DB connection
	 * with the given properties.
	 */
	public DriimerDBHelper() {
		initConfig();
		db = new DBConnection();
		dbconnection = db.createConnection(host, databasename, user, password);
	}
	
	/**
	 * Initializes the config for mysql (host, user and password).
	 */
	public void initConfig() {
		Properties prop = new Properties();
		try {
			File propertiesFile = new File("bin/driimerfinance/database/database.properties");
			if (!propertiesFile.exists()) {
				propertiesFile = new File("../../driimerfinance/database/database.properties");
			}
			prop.load(new FileInputStream(propertiesFile.getAbsolutePath()));
			host = prop.getProperty("host");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
	 * Finds a user by name specified.
	 * 
	 * @param name to search
	 * @return user which was found or an empty user
	 */
	public User getUserByName(String name) {
		User user = new User();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from user where username=?");
			preparedStatement.setString(1, name);

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
	 * Finds a users Password by id specified.
	 * 
	 * @param id to search
	 * @return String of Password found or an empty String
	 */
	public String getUserPasswordById(int userId) {
		String password = null;
		try {
			preparedStatement = dbconnection
					.prepareStatement("select Password from user where idUser=?");
			preparedStatement.setInt(1, userId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				 password = resultSet.getString("Password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return password;
	}

	/**
	 * Adds a user to the database.
	 * 
	 * @param user to be added
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
	 * Searches a mandant on the database with a given name.
	 * 
	 * @param mandantName to search
	 * @return found mandant if existing or null otherwise
	 */
	public Mandant getMandantByName(String mandantName) {
		Mandant mandant = null;
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from mandanten where Name=?");
			preparedStatement.setString(1, mandantName);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				mandant = new Mandant();
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
	 * @param mandant
	 *            to be added
	 * @return void
	 */
	public void addMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into mandanten(Name, DBSchema) values (?,?)");
			preparedStatement.setString(1, mandant.getName());
			preparedStatement.setString(2, mandant.getDBSchema());
			preparedStatement.execute();

			createMandantDatabase(mandant.getDBSchema());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * calls deleteMandantById()
	 * 
	 * @param mandant
	 *            to be deleted
	 * @return void
	 */
	public void deleteMandant(Mandant mandant) {
		deleteMandantById(mandant.getId());
	}

	/**
	 * Deletes a mandant from the database
	 * 
	 * @param Id of the mandant to be deleted
     */
	public void deleteMandantById(int mandantId) {
		try {
			Mandant mandant = getMandantById(mandantId);
			deleteMandantDatabase(mandant.getDBSchema());
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
	 */
	public void closeConnection() {
		close();
		db.close();
	}

	/**
	 * Closes all database related connections.
	 */
	private void close() {
		if (statement != null) {
			DBUtil.close(statement);
		}
		if (resultSet != null) {
			DBUtil.close(resultSet);
		}
	}

	/**
	 * Creates a new Database for the mandant
	 * 
	 * @param name of the mandant to be created
	 */

	private void createMandantDatabase(String schemaName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = null;

			connection = DriverManager
					.getConnection("jdbc:mysql://localhost/mysql"
							+ "?user=root&password=mysql");

			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + schemaName);

			Connection updateConnection = null;
			updateConnection = DriverManager
					.getConnection("jdbc:mysql://localhost/" + schemaName
							+ "?user=root&password=mysql");
			ScriptRunner runner = new ScriptRunner(updateConnection, false,
					true);
			InputStream in = getClass().getResourceAsStream(
					"/driimerfinance/database/DBSchema.txt");
			runner.runScript(new InputStreamReader(in, "utf-8"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Deletes the mandant's Database
	 * 
	 * @param name of the mandant to be deleted
	 */
	public void deleteMandantDatabase(String schemaName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = null;
			// connect to the mandant db
			connection = DriverManager.getConnection("jdbc:mysql://localhost/"
					+ schemaName + "?user=root&password=mysql");
			Statement statement = connection.createStatement();
			// drop the database to delete it
			statement.executeUpdate("DROP DATABASE " + schemaName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Imports a previously exported instance of a mandant's datbase
	 * 
	 * @param schema name to be imported
	 * @param source file path of the sql file
	 */
	public void importMandantDatabase(String schemaName, String source) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection updateConnection = null;
			updateConnection = DriverManager
					.getConnection("jdbc:mysql://localhost/" + schemaName
							+ "?user=root&password=mysql");
			// Call the script runner to actually important it
			ScriptRunner runner = new ScriptRunner(updateConnection, false,
					true);
			InputStream in = new FileInputStream(source);
			runner.runScript(new InputStreamReader(in, "utf-8"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

