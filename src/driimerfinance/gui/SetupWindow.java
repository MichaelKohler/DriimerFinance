package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.User;

/**
 * Used at the beginning to install the software.
 * 
 * (c) 2014 Driimer Finance
 */
public class SetupWindow {

	ImageIcon icon = new ImageIcon("images/DF.png");
	JFrame frame = new JFrame("DriimerFinance - Konfiguration");
	JPasswordField pwField1 = new JPasswordField();
	JPasswordField pwField2 = new JPasswordField();
	JTextField licenseField = new JTextField();
	JTextField mysqlHost = new JTextField();
	JTextField mysqlPort = new JTextField();
	JTextField mysqlUser = new JTextField();
	JPasswordField mysqlPassword = new JPasswordField();

	/**
	 * Constructor
	 */
	public SetupWindow() {
		createGUI();
	}

	/**
	 * Creates the GUI
	 */
	private void createGUI() {
		addDescription();
		addForm();
		addButtons();
		this.frame.setSize(400, 400);
		GUIHelper.centerFrame(this.frame);
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
	}

	/**
	 * Adds the description
	 */
	private void addDescription() {
		JPanel descriptionPanel = new JPanel();
		JTextArea label = new JTextArea(
				"Danke, dass Sie sich für DriimerFinance entschieden haben. Bitte beachten Sie, dass wir keine Möglichkeit haben, das Passwort zurückzusetzen, falls Sie dieses vergessen sollten. Zudem wird für die Lizenzüberprüfung eine Internetverbindung vorausgesetzt.");
		label.setPreferredSize(new Dimension(350, 100));
		label.setEditable(false);
		label.setColumns(30);
		label.setLineWrap(true);
		label.setWrapStyleWord(true);
		label.setBackground(new Color(238, 238, 238));
		descriptionPanel.add(label);
		descriptionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.frame.getContentPane().add(descriptionPanel, BorderLayout.NORTH);
	}

	/**
	 * Adds the form
	 */
	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(7, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel pw1Label = new JLabel("Passwort");
		formPanel.add(pw1Label);
		formPanel.add(pwField1);
		JLabel pw2Label = new JLabel("Passwort wiederholen");
		formPanel.add(pw2Label);
		formPanel.add(pwField2);
		JLabel licenseLabel = new JLabel("Lizenzschlüssel");
		formPanel.add(licenseLabel);
		formPanel.add(licenseField);
		JLabel mysqlHostLabel = new JLabel("MySQL Host");
		formPanel.add(mysqlHostLabel);
		mysqlHost.setText("localhost");
		formPanel.add(mysqlHost);
		JLabel mysqlPortLabel = new JLabel("MySQL Port");
		formPanel.add(mysqlPortLabel);
		mysqlPort.setText("3306");
		formPanel.add(mysqlPort);
		JLabel mysqlUserLabel = new JLabel("MySQL Benutzer");
		formPanel.add(mysqlUserLabel);
		formPanel.add(mysqlUser);
		JLabel mysqlPasswordLabel = new JLabel("MySQL Passwort");
		formPanel.add(mysqlPasswordLabel);
		formPanel.add(mysqlPassword);
		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	/**
	 * Adds the buttons on the bottom of the frame.
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new SaveConfigAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}

	/**
	 * Checks if the entered password match
	 */
	private boolean checkPasswords() {
		return new String(pwField1.getPassword()).equals(new String(pwField2
				.getPassword()));
	}

	/**
	 * ActionListener to save the new config
	 */
	public class SaveConfigAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean pwOK = checkPasswords();
			if (!pwOK) {
				JOptionPane.showMessageDialog(frame,
						"Passwörter stimmen nicht überein!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}

			boolean licenseOK = FinanceHelper.checkLicense(licenseField
					.getText());
			if (!licenseOK) {
				JOptionPane.showMessageDialog(frame,
						"Der Lizenzschlüssel ist nicht gültig!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}

			saveMySQLConfig();

			if (pwOK && licenseOK) {
				// User erstellen und Frame schliessen
				User user = new User("admin",
						new String(pwField1.getPassword()));
				user.createInDB();
				Properties prop = new Properties();
				try {
					URL url = getClass()
							.getResource(
									"../../driimerfinance/database/database.properties");
					prop.load(new FileInputStream(url.toURI().getPath()));
					prop.setProperty("licensekey", licenseField.getText());
					prop.store(new FileOutputStream(url.toURI().getPath()),
							null);
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

	/**
	 * Saves the config to the properties file which is used later
	 */
	public void saveMySQLConfig() {
		Properties prop = new Properties();
		URL url = getClass().getResource(
				"../../driimerfinance/database/database.properties");
		try {
			prop.load(new FileInputStream(url.toURI().getPath()));
			prop.setProperty("host", mysqlHost.getText());
			prop.setProperty("user", mysqlUser.getText());
			prop.setProperty("port", mysqlPort.getText());
			prop.setProperty("password",
					new String(mysqlPassword.getPassword()));
			prop.store(new FileOutputStream(url.toURI().getPath()), null);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
