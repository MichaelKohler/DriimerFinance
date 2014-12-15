package driimerfinance.models;

import driimerfinance.database.DriimerDBHelper;

/**
 * User model for the login to the system.
 * 
 * (c) 2014 Driimer Finance
 */
public class User implements IModel {
	private Integer id = 0;
	private String name = "";
	private String firstName = "";
	private String username = "";
	private String password = "";

	/**
	 * Constructor
	 */
	public User() {
	}

	/**
	 * Initializes a User with a name and password.
	 * 
	 * @param name to give
	 * @param password to set
	 */
	public User(String name, String password) {
		this.setName(name);
		this.setPassword(password);
	}

	/**
	 * Getter: Returns the object's first name
	 * 
	 * @return first name
	 */
	public String getVorname() {
		return this.firstName;
	}

	/**
	 * Setter: Sets the object's first name to the one specified
	 * 
	 * @param first name to set
	 */
	public void setVorname(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter: Returns the object's id
	 * 
	 * @return id of the user
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Setter: Sets the object's id to the one specified
	 * 
	 * @param id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Getter: Returns the object's username
	 * 
	 * @return username of this user
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter: Sets the object's username to the one specified
	 * 
	 * @param username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter: Returns the object's name
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter: Returns the object's password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Setter: Sets the object's name to the one specified
	 * 
	 * @param name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter: Sets the object's password to the one specified
	 * 
	 * @param password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Stores a new object in the database.
	 */
	public void createInDB() {
		DriimerDBHelper driimerdb = new DriimerDBHelper();
		driimerdb.addUser(this);
		driimerdb.closeConnection();
	}

	/**
	 * Updates the object in the database if already existing.
	 */
	public void updateInDB() {
		DriimerDBHelper driimerdb = new DriimerDBHelper();
		driimerdb.updateUser(this);
		driimerdb.closeConnection();
	}

	/**
	 * Deletes the object from the database.
	 */
	public void deleteInDB() {
		DriimerDBHelper driimerdb = new DriimerDBHelper();
		driimerdb.deleteUser(this);
		driimerdb.closeConnection();
	}
}