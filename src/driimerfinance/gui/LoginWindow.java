package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import driimerfinance.helpers.GUIHelper;

/**
 * Window used for the login at startup
 * 
 * (c) 2014 Driimer Finance
*/
public class LoginWindow {
	
	JFrame frame = new JFrame("Login Window");
	
	/**
	 * Constructor
	 */
    public LoginWindow() {
        createGUI();
    }
    
    /**
     * Creates the GUI
     */
    private void createGUI() {
		GUIHelper.centerAndSizeFrame(this.frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		placeComponents(panel);
		frame.setVisible(true);
    }
    
	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel textLabel = new JLabel("Bitte geben Sie Ihr Passwort ein");
		textLabel.setBounds(10, 10, 200, 25);
		panel.add(textLabel);
		
		JLabel passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		
		ActionListener loginButtonListener = new LoginButtonListener();
		loginButton.addActionListener(loginButtonListener);
		
	}
	
	public class LoginButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "login button has been pressed");
		}
	}
}
