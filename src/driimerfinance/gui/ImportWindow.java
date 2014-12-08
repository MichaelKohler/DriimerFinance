package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Mandant;
import driimerfinance.models.Transaction;
import driimerfinance.helpers.ComboboxHelper;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class ImportWindow {

	JFrame frame = new JFrame("Import");
	ImageIcon icon = new ImageIcon("images/DF.png");
	private JTextField nameField;

	/**
	 * Constructor
	 */
	public ImportWindow() {
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
