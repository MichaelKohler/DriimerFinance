package driimerfinance;

import java.util.List;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.gui.MainWindow;
import driimerfinance.gui.LoginWindow;
import driimerfinance.gui.MainWindowSingleton;
import driimerfinance.models.Mandant;
import driimerfinance.models.User;


/**
 * Main entry point class
 * 
 * (c) 2014 Driimer Finance
*/
public class DriimerFinance {
	
	//random comment

    /**
     * The main method starts everything. This includes initializing settings
     * and starting the Main graphical screen.
     * 
     * @param args the command line arguments
     * @throws Exception this is only used for the test code -- to be removed
     */
    public static void main(String[] args) throws Exception {
    		MainWindowSingleton.getMainWindowInstance();
 //   		new LoginWindow();

    		// TEST CODE BELOW
        DriimerDBHelper driimerdb = new DriimerDBHelper();
                
        System.out.println("Listing all Users: ");
        List<User> users = driimerdb.getAllUsers();
        for(User user : users) {
        	System.out.println("ID: " + user.getId() + " Name: " + user.getName() + " Vorname: " + user.getVorname() + " username: " + user.getUsername() + " Password: " + user.getPassword());
        }
        //Mandant testmandant = new Mandant();
        //testmandant.setName("stampfli");
        //testmandant.setDBSchema("stampfli");
        //testmandant.createInDB();
        //driimerdb.addMandant(testmandant);
        
        
        System.out.println("Listing all Clients: ");
        List<Mandant> mandanten = driimerdb.getAllMantanten();
        for(Mandant mandant : mandanten) {
        	System.out.println("ID: " + mandant.getId() + " Name: " + mandant.getName() + " DBSchema: " + mandant.getDBSchema());
        }
        driimerdb.closeConnection();
     }
    
}
