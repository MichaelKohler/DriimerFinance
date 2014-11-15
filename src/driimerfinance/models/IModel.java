package driimerfinance.models;

/**
 * Interface for all database connected models.
 */
public interface IModel {
	/**
	 * Create the entry in the database.
	 */
    public void createInDB();
    
    /**
     * Update the entry in the database.
     */
    public void updateInDB();
    
    /**
     * Delete the entry from the database.
     */
    public void deleteInDB();
}
