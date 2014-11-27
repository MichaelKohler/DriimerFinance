package driimerfinance.models;

import java.util.List;

import driimerfinance.database.MandantDBHelper;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
 */

public class AccountType implements IModel {

	private Integer id = null;
	private String name = null;

	/**
	 * Constructor
	 */
	public AccountType() {

	}

	/**
	 * Constructor which initialies an object with a given name
	 * 
	 * @param name
	 *            to give
	 */
	public AccountType(String name) {
		this.setName(name);
	}

	/**
	 * Getter: Returns the object's id
	 * 
	 * @return id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Setter: Sets the object's id to the one specified
	 * 
	 * @param id
	 *            to set
	 * @return void
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * Setter: Sets the object's name to the one specified
	 * 
	 * @param name
	 *            to set
	 * @return void
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Stores a new object in the database.
	 * 
	 * @return void
	 */
	public void createInDB() {
		MandantDBHelper db = new MandantDBHelper();
		db.addAccountType(this);
		db.closeConnection();
	}

	/**
	 * Updates the object in the database if already existing.
	 * 
	 * @return void
	 */
	public void updateInDB() {
		MandantDBHelper db = new MandantDBHelper();
		db.updateAccountType(this);
		db.closeConnection();
	}

	/**
	 * Deletes the object from the database.
	 * 
	 * @return void
	 */
	public void deleteInDB() {
		MandantDBHelper db = new MandantDBHelper();
		db.deleteAccountType(this);
		db.closeConnection();
	}
	
	/**
	 * Returns the name of the Foreign Key (Type)
	 * 
	 * @return type as readable string
	 */
	public String getTypeAsString() {
		MandantDBHelper helper = new MandantDBHelper();
		List<AccountType> types = helper.getAllAccountTypes();
		for (int i = 0; i < types.size(); i++) {
			if (this.id == types.get(i).getId()) {
				return types.get(i).getName();
			}
		}
		return "";
	}
}
