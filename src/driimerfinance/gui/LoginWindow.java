package driimerfinance.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.helpers.GUIHelper;

/**
 * Window used for the login at startup
 * 
 * (c) 2014 Driimer Finance
*/
public class LoginWindow {
	
	JFrame frame = new JFrame("Login Window");
	ImageIcon icon = new ImageIcon("images/DF.png");
	DriimerDBHelper db = new DriimerDBHelper();
	
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
		this.frame.setIconImage(icon.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {  
			Image img = Toolkit.getDefaultToolkit().getImage(new File("images/driimer.jpg").getAbsolutePath());
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
			}
		};
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
		this.frame.getRootPane().setDefaultButton(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordText.getPassword());
				if (password.equals(db.getUserPasswordById(1))) {
					JOptionPane.showMessageDialog(frame, "Login Erfolgreich", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
					MainWindowSingleton.getMainWindowInstance();
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(frame, "Das Kennwort ist falsch", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}	
		});	
	}
	
}

