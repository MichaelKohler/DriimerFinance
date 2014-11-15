package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class Mandant {
	private Integer id;
	private String name;
	private String dbSchema;
	
	public Mandant() {
        // TODO: create ID acc. to DB automatically
    }
	
    public Mandant(String name) {
        this.setName(name);
    }
    	
	public Integer getId() {
		return this.id;
	}

	public void setID(Integer iD) {
		this.id = iD;
	}

	public String getDBSchema() {
		return this.dbSchema;
	}

	public void setDBSchema(String dBSchema) {
		this.dbSchema = dBSchema;
	}

	public String getName() {
		return this.name;
	}

    public void setName(String name) {
        this.name = name;
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
