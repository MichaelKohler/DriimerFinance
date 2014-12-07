package driimerfinance.models;

import java.text.SimpleDateFormat;

import driimerfinance.database.MandantDBHelper;

/**
 * Used for Transactions within one mandant.
 * 
 * (c) 2014 Driimer Finance
*/
public class Transaction implements IModel {
	
	private Integer id = null;
	private java.sql.Date date = null;
	private int fk_fromAccount = 0;
	private int fk_toAccount = 0;
	private String description = null;
	private double amount = 0;
	private Integer receiptNumber = null;
	
	/**
	 * Constructor
	 */
    public Transaction() {
        // TODO: create ID automatically from DB
    }
    
    /**
     * Initializes a transaction with the given arguments.
     * 
     * @param positionFrom account source
     * @param positionTo account destination
     * @param amount to book
     */
    public Transaction(Account positionFrom, Account positionTo, int amount) {

    }
    
    /**
     * Setter: Sets the object's amount to the one specified
     * 
     * @param amount to set
     * @return void
     */
    public void setAmount(double amount) {
        this.amount = amount;
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
     * Getter: Returns the object's date
     * 
     * @return date
     */
	public java.sql.Date getDate() {
		return this.date;
	}
	
	public String getStringDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");  
		String dateString = sdf.format(date);
		return dateString;
	}

	/**
     * Setter: Sets the object's date to the one specified
     * 
     * @param date to set
     * @return void
     */
	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	/**
     * Getter: Returns the object's fromAccount
     * 
     * @return fromAccount
     */
	public int getFk_SollKonto() {
		return this.fk_fromAccount;
	}

	/**
     * Setter: Sets the object's from account to the one specified
     * 
     * @param fromAccount to set
     * @return void
     */
	public void setFk_SollKonto(int sollKonto) {
		this.fk_fromAccount = sollKonto;
	}

	/**
     * Getter: Returns the object's toAccount
     * 
     * @return toAccount
     */
	public int getFk_HabenKonto() {
		return this.fk_toAccount;
	}

	/**
     * Setter: Sets the object's toAccount to the one specified
     * 
     * @param toAccount to set
     * @return void
     */
	public void setFk_HabenKonto(int habenKonto) {
		this.fk_toAccount = habenKonto;
	}

	/**
     * Getter: Returns the object's description
     * 
     * @return description
     */
	public String getBezeichnung() {
		return this.description;
	}

	/**
     * Setter: Sets the object's description to the one specified
     * 
     * @param description to set
     * @return void
     */
	public void setBezeichnung(String description) {
		this.description = description;
	}

	/**
     * Getter: Returns the object's amount
     * 
     * @return amount
     */
	public double getBetrag() {
		return this.amount;
	}

	/**
     * Setter: Sets the object's amount to the one specified
     * 
     * @param amount to set
     * @return void
     */
	public void setBetrag(double betrag) {
		this.amount = betrag;
	}

	/**
     * Getter: Returns the object's receipt number
     * 
     * @return receipt number
     */
	public Integer getBelegNr() {
		return this.receiptNumber;
	}

	/**
     * Setter: Sets the object's receipt number to the one specified
     * 
     * @param receipt number to set
     * @return void
     */
	public void setBelegNr(Integer receipt) {
		this.receiptNumber = receipt;
	}
	
	/**
     * Stores a new object in the database.
     * 
     * @return void
     */
    public void createInDB() {
    	MandantDBHelper db = new MandantDBHelper();
    	this.setId(db.addTransaction(this));
    	db.closeConnection();
    }
    
    /**
     * Updates the object in the database if already existing.
     * 
     * @return void
     */
    public void updateInDB() {
    	MandantDBHelper db = new MandantDBHelper();
    	db.updateTransaction(this);
    	db.closeConnection();
    }
    
    /**
     * Deletes the object from the database.
     * 
     * @return void
     */
    public void deleteInDB() {
    	MandantDBHelper db = new MandantDBHelper();
    	db.deleteTransaction(this);
    	db.closeConnection();
        
    }
}
