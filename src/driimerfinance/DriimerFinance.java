package driimerfinance;

import java.util.List;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.database.MandantDBHelper;
import driimerfinance.gui.MainWindow;
import driimerfinance.models.Mandant;
import driimerfinance.models.Transaction;
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
    		new MainWindow();
    		
    		// TEST CODE BELOW
        DriimerDBHelper driimerdb = new DriimerDBHelper();
                
        System.out.println("Listing all Users: ");
        List<User> users = driimerdb.getAllUsers();
        for(User user : users) {
        	System.out.println("ID: " + user.getId() + " Name: " + user.getName() + " Vorname: " + user.getVorname() + " username: " + user.getUsername() + " Password: " + user.getPassword());
        }
        
        
        System.out.println("Listing all Clients: ");
        List<Mandant> mandanten = driimerdb.getAllMantanten();
        for(Mandant mandant : mandanten) {
        	System.out.println("ID: " + mandant.getId() + " Name: " + mandant.getName() + " DBSchema: " + mandant.getDBSchema());
        }
        
        driimerdb.closeConnection();
        
        MandantDBHelper dbhelper = new MandantDBHelper("localhost", "mandant", "root", "mysql");
        List<Transaction> transactions = dbhelper.getAllTransactions();
        for (Transaction transaction : transactions) {
        	System.out.println("ID: " + transaction.getId() + " Datum: " + transaction.getDate() + " fk_SollKonto: " + transaction.getFk_SollKonto() + " fk_HabenKonto: " + transaction.getFk_HabenKonto() + " Bezeichnung: " + transaction.getBezeichnung() + " Betrag: " + transaction.getBetrag() + " Beleg-Nr: " + transaction.getBelegNr());
        }
    }
    
}
