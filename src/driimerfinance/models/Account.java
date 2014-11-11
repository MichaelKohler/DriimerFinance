package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/

public class Account {
	
	private Integer Id = null;
	private String Name = null;
	private int Fk_AccountType = 0;
	private int Balance = 0;
	private Boolean CapitalAccount = false;
	
    public Account() {
        
    }
    
    public Account(String name) {
        this.setName(name);
    }
    
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getFk_AccountType() {
		return Fk_AccountType;
	}

	public void setFk_AccountType(int fk_AccountType) {
		Fk_AccountType = fk_AccountType;
	}

	public int getBalance() {
		return Balance;
	}

	public void setBalance(int balance) {
		Balance = balance;
	}

	public Boolean getCapitalAccount() {
		return CapitalAccount;
	}

	public void setCapitalAccount(Boolean capitalAccount) {
		CapitalAccount = capitalAccount;
	}
    
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
