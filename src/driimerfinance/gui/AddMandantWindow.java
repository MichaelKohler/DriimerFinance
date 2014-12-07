package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;

/**
 * Add a new mandant form
 * 
 * (c) 2014 Driimer Finance
 */
public class AddMandantWindow {

	JFrame frame = new JFrame("DriimerFinance - Mandant hinzuf\u00fcgen");
	ImageIcon icon = new ImageIcon("images/DF.png");
	JTextField nameField = null;


	/**
	 * Constructor
	 */
	public AddMandantWindow() {
		createGUI();
	}

	/**
     * Creates the GUI
     */
	private void createGUI() {
		addForm();
		addButtons();
		this.frame.setSize(500, 125);
		GUIHelper.centerFrame(this.frame);
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
	}

	/**
	 * Adds the content.
	 */
	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel nameLabel = new JLabel("Name");
		this.nameField = new JTextField();
		this.nameField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(nameLabel);
		formPanel.add(this.nameField);

		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	/**
	 * Adds the buttons on the bottom of the frame.
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new SaveMandantAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}
	
	public class SaveMandantAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (nameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Der Name muss ausgef\u00fcllt sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			DriimerDBHelper helper = new DriimerDBHelper();
			String mandantName = nameField.getText();
			Mandant existingMandant = helper.getMandantByName(mandantName);
			if (existingMandant == null) {
				Mandant newMandant = new Mandant();
				newMandant.setName(mandantName);
				newMandant.setDBSchema(nameField.getText());
				newMandant.createInDB();
				MainWindowSingleton.getMainWindowInstance().reload();
				frame.dispose();
			}
			else {
				JOptionPane.showMessageDialog(frame, "Der Name darf nicht bereits existieren!", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
