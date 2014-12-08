package driimerfinance.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.database.MandantDBHelper;
import driimerfinance.models.Mandant;

/**
 * Special case: this will save a SQL file with all the possible data from the
 * database. This can be used as a backup.
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
		DriimerDBHelper db = new DriimerDBHelper();
		db.addMandant(mandant);
	}
}
