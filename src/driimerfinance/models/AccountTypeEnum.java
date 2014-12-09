package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
 */

public enum AccountTypeEnum {
	ACTIVE(1),
	PASSIVE(2),
	EARNING(3),
	SPENDING(4);
	
	private int id = 0;
	
	private AccountTypeEnum(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
