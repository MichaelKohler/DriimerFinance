package driimerfinance.services;

/**
 * General implementation of export capabilities.
 * 
 * (c) 2014 Driimer Finance
*/
public abstract class Exporter {
	
	/**
	 * Constructor
	 */
    public Exporter() {
        
    }
    
    /**
     * Crawls the database to get all data for the export.
     * 
     * @return void
     */
    public void getDataToExport() {
        
    }
    
    /**
     * Exports the crawled data to a file on the file system.
     * 
     * @param path to the desired file
     * @return void
     */
    public void exportToFile(String filename) {
        
    }
}
