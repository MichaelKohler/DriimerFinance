package driimerfinance.gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
	
	JFrame frame = new JFrame("Driimer Finance - Login");
	ImageIcon icon = null;
	DriimerDBHelper db = new DriimerDBHelper();
	JPasswordField passwordText;
	
	/**
	 * Constructor
	 */
    public LoginWindow() {
    	    File imageFile = new File("images/DF.png");
    	    if (imageFile.exists()) {
    	        icon = new ImageIcon(imageFile.getAbsolutePath());
    	    } else {
    	    	    URL url = getClass().getResource("/DF.png");
    	    	    icon = new ImageIcon(url);
    	    }
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
			// Paint the background image
			public void paintComponent(Graphics g) {
				File driimerFile = new File("images/driimer.jpg");
				String driimerFilePath = driimerFile.getAbsolutePath();
				if (!driimerFile.exists()) {
					URL url = getClass().getResource("/driimer.jpg");
					File logoFile = new File("bin/driimer.jpg");
					try {
						InputStream in = url.openStream();
						Files.copy(in, logoFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}
					driimerFilePath = logoFile.getAbsolutePath();
				}
			    Image img = Toolkit.getDefaultToolkit().getImage(driimerFilePath);
			    g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
			}
		};
		
		JPanel panel = new JPanel();
		
		frame.getContentPane().add(mainpanel);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		placeComponents(panel);
		frame.setVisible(true);
    }
    
    /**
     * Prints the different components of the login form.
     * 
     * @param panel to paint on
     */
	private void placeComponents(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel textLabel = new JLabel("Bitte geben Sie Ihr Passwort ein:");
		textLabel.setFont(new Font("Verdana", 0, 30));
		panel.add(textLabel);
		
		passwordText = new JPasswordField(20);
		passwordText.setFont(new Font("Verdana", 0, 30));
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Verdana", 0, 30));
		panel.add(loginButton);
		this.frame.getRootPane().setDefaultButton(loginButton);
		loginButton.addActionListener(new LoginAction());	
	}
	
	/**
	 * ActionListener to login a user
	 */
	public class LoginAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String password = new String(passwordText.getPassword());
			if (password.equals(db.getUserPasswordById(1))) {
				MainWindowSingleton.getMainWindowInstance();
				frame.dispose();
			}
			else {
				JOptionPane.showMessageDialog(frame, "Das Kennwort ist falsch", "Fehler", JOptionPane.ERROR_MESSAGE);
				passwordText.setText("");
			}
		}
	}
}