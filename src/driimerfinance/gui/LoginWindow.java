package driimerfinance.gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.helpers.GUIHelper;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

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
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainpanel = new JPanel() {
			public void paintComponent(Graphics g) {  
			Image img = Toolkit.getDefaultToolkit().getImage(new File("images/driimer.jpg").getAbsolutePath());
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
			}
		};
		
		JPanel panel = new JPanel();
		
		frame.getContentPane().add(mainpanel);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		placeComponents(panel);
		frame.setVisible(true);
    }
    
	private void placeComponents(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel textLabel = new JLabel("Bitte geben Sie Ihr Passwort ein:");
		textLabel.setFont(new Font("Verdana", 0, 30));
		panel.add(textLabel);
		
		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setFont(new Font("Verdana", 0, 30));
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Verdana", 0, 30));
		panel.add(loginButton);
		this.frame.getRootPane().setDefaultButton(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordText.getPassword());
				if (password.equals(db.getUserPasswordById(1))) {
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

