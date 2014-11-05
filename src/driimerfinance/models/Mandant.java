package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class Mandant {
	private Integer ID;
	private String Name;
	private String DBSchema;
	
	public Mandant() {
        
    }
	
    public Mandant(String name) {
        this.setName(name);
    }
    	
	public Integer getId() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getDBSchema() {
		return DBSchema;
	}

	public void setDBSchema(String dBSchema) {
		DBSchema = dBSchema;
	}

	public String getName() {
		return Name;
	}


    

    public void setName(String name) {
        this.Name = name;
    }
    
    public static void setCurrentWorkingMandant(String name) {
        
    }
    
    public void createInDB() {
        
    }
    
    public void updateInDB() {
        
    }
    
    public void deleteInDB() {
        
    }
}
