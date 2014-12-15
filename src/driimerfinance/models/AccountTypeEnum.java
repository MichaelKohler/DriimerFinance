package driimerfinance.models;

/**
 * List of all possible account types.
 * 
 * (c) 2014 Driimer Finance
 */

public enum AccountTypeEnum {
	ACTIVE(1),
	PASSIVE(2),
	EARNING(3),
	SPENDING(4);
	
	private int id = 0;
	
	/**
	 * Constructor
	 * 
	 * @param id used from the enum
	 */
	private AccountTypeEnum(int id) {
		this.id = id;
	}
	
	/**
	 * get the id
	 * 
	 * @return id of the used account type
	 */
	public int getId() {
		return this.id;
	}
}
