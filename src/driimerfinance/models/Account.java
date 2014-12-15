package driimerfinance.models;

import driimerfinance.database.MandantDBHelper;

/**
 * Account Model. Every account should be an Account.
 * 
 * (c) 2014 Driimer Finance
*/

public class Account implements IModel {
	
	private int id;
	private int number;
	private String name = null;
	private int fk_AccountType = 0;
	private double balance = 0;
	private Boolean capitalAccount = false;
	
	/**
	 * Constructor
	 */
    public Account() {
    }
    
    /**
     * Constructor: initializes an object and assigns a name to it.
     * 
     * @param name to set
     */
    public Account(String name) {
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
     * @param id to set
     * @return void
     */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
     * Getter: Returns the object's number
     * 
     * @return number
     */
	public Integer getNumber() {
		return this.number;
	}

	/**
     * Setter: Sets the object's number to the one specified
     * 
     * @param number to set
     */
	public void setNumber(Integer number) {
		this.number = number;
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
     * @param name to set
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Getter: Returns the object's account type
     * 
     * @return account type
     */
	public int getFk_AccountType() {
		return this.fk_AccountType;
	}

	/**
     * Setter: Sets the object's account type to the one specified
     * 
     * @param account type to set
     */
	public void setFk_AccountType(int fk_AccountType) {
		this.fk_AccountType = fk_AccountType;
	}

	/**
     * Getter: Returns the object's balance
     * 
     * @return id
     */
	public double getBalance() {
		return this.balance;
	}

	/**
     * Setter: Sets the object's balance to the one specified
     * 
     * @param balance to set
     */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
     * Getter: Returns whether the account is a capital account or not
     * 
     * @return whether capital account or not
     */
	public Boolean getCapitalAccount() {
		return this.capitalAccount;
	}

	/**
     * Setter: Sets the object's capital account check to the valze specified
     * 
     * @param whether it's a capital account or not
     */
	public void setCapitalAccount(Boolean capitalAccount) {
		this.capitalAccount = capitalAccount;
	}
    
	/**
     * Stores a new object in the database.
     */
    public void createInDB() {
        MandantDBHelper db = new MandantDBHelper();
        db.addAccount(this);
        db.closeConnection();
    }
    
    /**
     * Updates the object in the database if already existing.
     */
    public void updateInDB() {
        MandantDBHelper db = new MandantDBHelper();
        db.updateAccount(this);
        db.closeConnection();
    }
    
    /**
     * Deletes the object from the database.
     */
    public void deleteInDB() {
    	MandantDBHelper db = new MandantDBHelper();
    	db.deleteAccount(this);
    	db.closeConnection();
    }
}