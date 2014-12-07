package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
 * Used at the beginning to install the software.
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
					URL url = getClass().getResource("../../driimerfinance/database/database.properties");
					prop.load(new FileInputStream(url.toURI().getPath()));
					prop.setProperty("licensekey", licenseField.getText());
					prop.store(new FileOutputStream(url.toURI().getPath()), null);
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				}
				frame.dispose();
				MainWindowSingleton.getMainWindowInstance();
			}
		}
	}
}