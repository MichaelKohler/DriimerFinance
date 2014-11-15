package driimerfinance.services;

/**
 * Special case of exports: Class to create a PDF from the exported data
 * 
 * (c) 2014 Driimer Finance
*/
public class PDFExporter extends Exporter {
	
	/**
	 * Constructor
	 */
    public PDFExporter() {
        
    }
    
    /**
     * Gets the data to include in the PDF report from the database.
     * 
     * @return void
     */
    @Override
    public void getDataToExport() {
        
    }
    
    /**
     * Special case file creating since this is a PDF to be created. This creats the PDF and saves it on the specified path on the filesystem.
     * 
     * @param path the PDF will be saved
     * @return void
     */
    @Override
    public void exportToFile(String filename) {
        
    }
}
