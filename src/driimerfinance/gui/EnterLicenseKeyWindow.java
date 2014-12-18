package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;

/**
 * Re-enter the license key once it's expired.
 * 
 * (c) 2014 Driimer Finance
*/
public class EnterLicenseKeyWindow {
	
	ImageIcon icon = new ImageIcon("images/DF.png");
	JFrame frame = new JFrame("DriimerFinance - Lizenz");
	JTextField licenseField = new JTextField();
	
	/**
	 * Constructor
	 */
    public EnterLicenseKeyWindow() {
        createGUI();
    }
    
    /**
     * Creates the GUI
     */
    private void createGUI() {
		addForm();
		addButtons();
		this.frame.setSize(400, 100);
		GUIHelper.centerFrame(this.frame);
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
	}
    
    /**
	 * Adds the form
	 */
    private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel licenseLabel = new JLabel("Lizenzschlüssel");
		formPanel.add(licenseLabel);
		formPanel.add(licenseField);
		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
    }
    
    /**
     * Adds the buttons on the bottom of the frame.
     */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new SaveLicenseAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}
	
	/**
	 * ActionListener to save the new license
	 */
	public class SaveLicenseAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean licenseOK = FinanceHelper.checkLicense(licenseField.getText());
			if (!licenseOK) {
				JOptionPane.showMessageDialog(frame, "Der Lizenzschlüssel ist nicht gültig!", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
			else {
				Properties prop = new Properties();
				try {
					// save the new key to the properties file
					File propertiesFile = new File("bin/driimerfinance/database/database.properties");
					if (!propertiesFile.exists()) {
						propertiesFile = new File("../../driimerfinance/database/database.properties");
					}
					prop.load(new FileInputStream(propertiesFile.getAbsolutePath()));
					prop.setProperty("licensekey", licenseField.getText());
					prop.store(new FileOutputStream(propertiesFile.getAbsolutePath()), null);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				frame.dispose();
				MainWindowSingleton.getMainWindowInstance(); // start the GUI
			}
		}
	}
}