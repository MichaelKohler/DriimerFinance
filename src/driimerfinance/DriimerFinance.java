package driimerfinance;

import java.util.List;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.models.Mandant;
import driimerfinance.models.User;

/**
 * Main entry point class
 * 
 * (c) 2014 Driimer Finance
*/
public class DriimerFinance {

    /**
     * The main method starts everything. This includes initializing settings
     * and starting the Main graphical screen.
     * 
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Test");
        
        DriimerDBHelper driimerdb = new DriimerDBHelper();
                
        System.out.println("Listing all Users: ");
        List<User> users = driimerdb.getAllUsers();
        for(User user : users) {
        	System.out.println("ID: " + user.getId() + " Name: " + user.getName() + " Vorname: " + user.getVorname() + " username: " + user.getUsername() + " Password: " + user.getPassword());
        }
        
        
        System.out.println("Listing all Clients: ");
        List<Mandant> mandanten = driimerdb.getAllMantanten();
        for(Mandant mandant : mandanten) {
        	System.out.println("ID: " + mandant.getID() + " Name: " + mandant.getName() + " DBSchema: " + mandant.getDBSchema());
        }
        
        driimerdb.closeConnection();
    }
    
}
