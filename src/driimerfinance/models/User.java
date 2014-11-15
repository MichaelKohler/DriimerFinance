package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class User {
	private Integer id = null;
	private String name = null;
	private String firstName = null;
	private String username = null;
	private String password = null;
	
    public User() {
    }
    
    public User(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }
	
	public String getVorname() {
		return this.firstName;
	}

	public void setVorname(String firstName) {
		this.firstName = firstName;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
