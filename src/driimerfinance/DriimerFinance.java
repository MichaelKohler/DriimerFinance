package driimerfinance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import driimerfinance.gui.EnterLicenseKeyWindow;
import driimerfinance.gui.LoginWindow;
import driimerfinance.gui.MainWindowSingleton;
import driimerfinance.gui.SetupWindow;
import driimerfinance.helpers.FinanceHelper;

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
				new LoginWindow();
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
