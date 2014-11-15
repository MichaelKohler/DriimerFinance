package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/

public class Account {
	
	private Integer id = null;
	private String name = null;
	private int fk_AccountType = 0;
	private int balance = 0;
	private Boolean capitalAccount = false;
	
    public Account() {
        // TODO: create ID automatically from DB
    }
    
    public Account(String name) {
        this.setName(name);
    }
    
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFk_AccountType() {
		return this.fk_AccountType;
	}

	public void setFk_AccountType(int fk_AccountType) {
		this.fk_AccountType = fk_AccountType;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Boolean getCapitalAccount() {
		return this.capitalAccount;
	}

	public void setCapitalAccount(Boolean capitalAccount) {
		this.capitalAccount = capitalAccount;
	}
    
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
