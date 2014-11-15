package driimerfinance.models;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/

public class AccountType implements IModel {
	
	private Integer id = null;
	private String name = null;
	
	/**
	 * Constructor
	 */
    public AccountType() {
        
    }
    
    /**
     * Constructor which initialies an object with a given name
     * 
     * @param name to give
     */
    public AccountType(String name) {
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
	public void setId(Integer id) {
		this.id = id;
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
