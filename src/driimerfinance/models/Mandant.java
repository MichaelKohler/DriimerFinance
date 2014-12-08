package driimerfinance.models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Properties;

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
		String temp = Normalizer.normalize(dBSchema, Normalizer.Form.NFD);
		temp = temp.replaceAll("[^\\p{ASCII}]", "");
		temp = temp.replace(" ", "");
		temp = temp.replace("-", "");
		this.dbSchema = temp;
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
    public void setCurrentWorkingMandant() {
    	Properties prop = new Properties();
		//InputStream in;
		try {
			URL url = getClass().getResource("../../driimerfinance/database/database.properties");
			System.out.println("URL: " + url);
			prop.load(new FileInputStream(url.toURI().getPath()));
			prop.setProperty("databasename", this.getDBSchema());
			prop.store(new FileOutputStream(url.toURI().getPath()), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MenuBarSingleton.setEnabled(true);
		
		
    }
    
    /**
     * Stores a new object in the database.
     * 
     * @return void
     */
    public void createInDB() {
    	DriimerDBHelper driimerdb = new DriimerDBHelper();
    	driimerdb.addMandant(this);
    	driimerdb.closeConnection();
    }
    
    /**
     * Updates the object in the database if already existing.
     * 
     * @return void
     */
    public void updateInDB() {
    	DriimerDBHelper driimerdb = new DriimerDBHelper();
    	driimerdb.updateMandant(this);
    	driimerdb.closeConnection();
    }
    
    /**
     * Deletes the object from the database.
     * 
     * @return void
     */
    public void deleteInDB() {
    	DriimerDBHelper driimerdb = new DriimerDBHelper();
    	driimerdb.deleteMandant(this);
    	driimerdb.closeConnection();
    }
}
