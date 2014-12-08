package driimerfinance.services;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.models.Mandant;

/**
 * Special case: this will read data from a backup and import it to the
 * database. This can be used for restoring data.
 * 
 * (c) 2014 Driimer Finance
 */
public class RawDataImporter extends Exporter implements Runnable {
	
	private String source = null;
	private Mandant mandant = null;
		/**
	 * Constructor
	 */
	public RawDataImporter(String source, Mandant mandant) {
		this.source = source;
		this.mandant = mandant;
	}

	@Override
	public void run() {
		//Create Mandant in DB's
		DriimerDBHelper db = new DriimerDBHelper();
		db.addMandant(mandant);
		//import data from source to clients DB
		db.importMandantDatabase(mandant.getDBSchema(), source);
	}
}
