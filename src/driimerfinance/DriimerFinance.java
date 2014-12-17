package driimerfinance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import driimerfinance.gui.EnterLicenseKeyWindow;
import driimerfinance.gui.LoginWindow;
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
	 */
	public static void main(String[] args) {
		Properties properties = new Properties();
		String licenseKey = "";
		try {
			File file = new File("bin/driimerfinance/database/database.properties");
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			properties.load(new FileInputStream(file.getAbsoluteFile()));
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
