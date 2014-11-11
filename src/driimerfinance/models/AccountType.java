package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/

public class AccountType {
	
	private Integer Id = null;
	private String Name = null;
	
    public AccountType() {
        
    }
    
    public AccountType(String name) {
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

    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
