package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/

public class AccountType {
	
	private Integer id = null;
	private String name = null;
	
    public AccountType() {
        
    }
    
    public AccountType(String name) {
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

    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
