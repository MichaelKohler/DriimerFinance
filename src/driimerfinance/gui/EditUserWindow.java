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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.User;

/**
 * Used at the beginning to install the software.
 * 
 * (c) 2014 Driimer Finance
*/
public class EditUserWindow {
	
	ImageIcon icon = new ImageIcon("images/DF.png");
	JFrame frame = new JFrame("DriimerFinance - Benutzer bearbeiten");
	JPasswordField pwField1 = new JPasswordField();
	JPasswordField pwField2 = new JPasswordField();
	
	/**
	 * Constructor
	 */
    public EditUserWindow() {
        createGUI();
    }
    
    /**
     * Creates the GUI
     */
    private void createGUI() {
		addForm();
		addButtons();
		this.frame.setSize(400, 300);
		GUIHelper.centerFrame(this.frame);
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
	}
    
    /**
	 * Adds the form
	 */
    private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(2, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel pwLabel1 = new JLabel("Passwort");
		formPanel.add(pwLabel1);
		formPanel.add(pwField1);
		JLabel pwLabel2 = new JLabel("Passwort wiederholen");
		formPanel.add(pwLabel2);
		formPanel.add(pwField2);
		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
    }
    
    /**
     * Adds the buttons on the bottom of the frame.
     */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pw1 = new String(pwField1.getPassword());
				String pw2 = new String(pwField2.getPassword());
				if (pw1.equals(pw2) == false) {
					JOptionPane.showMessageDialog(frame, "Passwörter stimmen nicht überein!", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
				else {
					DriimerDBHelper helper = new DriimerDBHelper();
					User user = helper.getUserByName("admin");
					user.setPassword(pw1);
					user.updateInDB();
					frame.dispose();
				}
			}
		});
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}
}