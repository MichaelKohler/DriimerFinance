package driimerfinance.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Properties;
import java.util.UUID;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.gui.MenuBarSingleton;

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
     */
	public void setID(Integer iD) {
		this.id = iD;
	}

	/**
     * Getter: Returns the object's database schema
     * 
     * @return dbschema used
     */
	public String getDBSchema() {
		return this.dbSchema;
	}

	/**
     * Setter: Sets the object's dbschema to the one specified String
     * 
     * @param database schema  to set
     */
	public void setDBSchema(String dBSchema) {
		String temp = Normalizer.normalize(dBSchema, Normalizer.Form.NFD);
		temp = temp.replaceAll("[^\\p{ASCII}]", ""); // replace all non valid characters for the "Normalform"
		temp = temp.replace(" ", "");
		temp = temp.replace("-", "");
		this.dbSchema = temp;
	}
	
	/**
     * Setter: Creates a new random String for dbSchema
     * 
     * @param database schema  to set
     */
	public void createDBSchema() {
		//Generiert eine einmalige UUID. Entfernt "-" da in datenbankname nicht erlaubt
		String unique = UUID.randomUUID().toString().replace("-", "");
		this.dbSchema = unique;
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
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Setter: Sets the current mandant to work on
     */
    public void setCurrentWorkingMandant() {
    	    Properties prop = new Properties();
		try {
			File propertiesFile = new File("bin/driimerfinance/database/database.properties");
			if (!propertiesFile.exists()) {
				propertiesFile = new File("../../driimerfinance/database/database.properties");
			}
			prop.load(new FileInputStream(propertiesFile.getAbsolutePath()));
			prop.setProperty("databasename", this.getDBSchema()); // sets the name of the schema as value in the properties file
			prop.store(new FileOutputStream(propertiesFile.getAbsolutePath()), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MenuBarSingleton.setEnabled(true); // set all the menu items to enabled since we have a working mandant now
    }
    
    /**
     * Stores a new object in the database.
     */
    public void createInDB() {
    	    DriimerDBHelper driimerdb = new DriimerDBHelper();
    	    driimerdb.addMandant(this);
    	    driimerdb.closeConnection();
    }
    
    /**
     * Updates the object in the database if already existing.
     */
    public void updateInDB() {
    	    DriimerDBHelper driimerdb = new DriimerDBHelper();
    	    driimerdb.updateMandant(this);
    	    driimerdb.closeConnection();
    }
    
    /**
     * Deletes the object from the database.
     */
    public void deleteInDB() {
    	    DriimerDBHelper driimerdb = new DriimerDBHelper();
    	    driimerdb.deleteMandant(this);
    	    driimerdb.closeConnection();
    }
}
