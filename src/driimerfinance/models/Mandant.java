package driimerfinance.models;

/**
 * Defines a mandant (client) to work for.
 * 
 * (c) 2014 Driimer Finance
*/
public class Mandant implements IModel {
	private Integer id;
	private String name;
	private String dbSchema;
	
	/**
	 * Constructor
	 */
	public Mandant() {
        // TODO: create ID acc. to DB automatically
    }
	
    /**
     * Constructor which initializes the object with a given name
     * 
     * @param name to give
     */
    public Mandant(String name) {
        this.setName(name);
    }
    	
    /**
     * Getter: Returns the object's id
     * 
     * @return id
     */
	public Integer getId() {
		return this.id;
	}

	/**
     * Setter: Sets the object's id to the one specified
     * 
     * @param id to set
     * @return void
     */
	public void setID(Integer iD) {
		this.id = iD;
	}

	/**
     * Getter: Returns the object's database schema
     * 
     * @return dbschema
     */
	public String getDBSchema() {
		return this.dbSchema;
	}

	/**
     * Setter: Sets the object's dbschema to the one specified
     * 
     * @param database schema  to set
     * @return void
     */
	public void setDBSchema(String dBSchema) {
		this.dbSchema = dBSchema;
	}

	/**
     * Getter: Returns the object's name
     * 
     * @return name
     */
	public String getName() {
		return this.name;
	}

	/**
     * Setter: Sets the object's name to the one specified
     * 
     * @param name to set
     * @return void
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Setter: Sets the current mandant to work on
     * 
     * @param mandant to change to
     * @return void
     */
    public static void setCurrentWorkingMandant(String name) {
        
    }
    
    /**
     * Stores a new object in the database.
     * 
     * @return void
     */
    public void createInDB() {
        
    }
    
    /**
     * Updates the object in the database if already existing.
     * 
     * @return void
     */
    public void updateInDB() {
        
    }
    
    /**
     * Deletes the object from the database.
     * 
     * @return void
     */
    public void deleteInDB() {
        
    }
}
