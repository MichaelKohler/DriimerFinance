package driimerfinance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.gui.EnterLicenseKeyWindow;
import driimerfinance.gui.MainWindowSingleton;
import driimerfinance.gui.SetupWindow;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.models.Mandant;
import driimerfinance.models.User;

/**
 * Main entry point class
 * 
 * (c) 2014 Driimer Finance
 */
public class DriimerFinance {

	// random comment

	/**
	 * The main method starts everything. This includes initializing settings
	 * and starting the Main graphical screen.
	 * 
	 * @param args
	 *            the command line arguments
	 * @throws Exception
	 *             this is only used for the test code -- to be removed
	 */
	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		String licenseKey = "";
		try {
			properties.load(new FileInputStream(new File(
					"bin/driimerfinance/database/database.properties")
					.getAbsoluteFile()));
			licenseKey = properties.getProperty("licensekey");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (licenseKey != null && licenseKey.equals("") == false) {
			// existing key, checking validity
			boolean validity = FinanceHelper.checkLicense(licenseKey);
			if (validity) {
				// new LoginWindow(); // TODO: enable for production
				MainWindowSingleton.getMainWindowInstance(); // TODO: remove for
																// production
			} else {
				JOptionPane.showMessageDialog(null,
						"Der Lizenzschlüssel ist nicht gültig!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				new EnterLicenseKeyWindow();
			}
		} else {
			new SetupWindow();
		}
	}

}
