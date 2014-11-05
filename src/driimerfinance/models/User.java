package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class User {
	private Integer id = null;
	private String Name = null;
	private String Vorname = null;
	private String Username = null;
	private String Password = null;
	
	
	public String getVorname() {
		return Vorname;
	}

	public void setVorname(String vorname) {
		Vorname = vorname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getName() {
		return Name;
	}

	public String getPassword() {
		return Password;
	}


	
	
    public User() {
        
    }
    
    public User(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }
    
    public void setName(String name) {
        this.Name = name;
    }
    
    public void setPassword(String password) {
        this.Password = password;
    }
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
