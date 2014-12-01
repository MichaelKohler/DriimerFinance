package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.print.attribute.standard.DateTimeAtProcessing;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Transaction;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class AddTransactionWindow {

	JFrame frame = new JFrame("DriimerFinance - Buchung hinzuf\u00fcgen");
	ImageIcon icon = new ImageIcon("images/DF.png");
	ArrayList<String> fromAccounts = new ArrayList<String>();
	ArrayList<String> toAccounts = new ArrayList<String>();
	
	JTextField dateField = null;
	JComboBox sollField = null;
	JComboBox habenField = null;
	JTextField transactionField =null;
	JTextField amountField = null;
	JTextField receiptField = null;
	JDatePickerImpl datePicker = null;

	/**
	 * Constructor
	 */
	public AddTransactionWindow() {
	    setData();
		createGUI();
	}
	
	/**
     * Gets the necessary data from the DB
     */
	private void setData() {
		MandantDBHelper helper = new MandantDBHelper();
		List<Account> accs = helper.getAllAccounts();
		for (Account acc : accs) {
			this.fromAccounts.add(acc.getName());
			this.toAccounts.add(acc.getName());
		}
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
		GridLayout layout = new GridLayout(6, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		
		//DatePicker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		JLabel dateLabel = new JLabel("Datum");
		this.dateField = new JTextField();
		this.dateField.setPreferredSize(new Dimension(150, 20));
		JLabel sollLabel = new JLabel("Soll Konto");
		this.sollField = new JComboBox(this.fromAccounts.toArray());
		this.sollField.setPreferredSize(new Dimension(150, 20));
		JLabel habenLabel = new JLabel("Haben Konto");
		this.habenField = new JComboBox(this.toAccounts.toArray());
		this.habenField.setPreferredSize(new Dimension(150, 20));
		JLabel buchungLabel = new JLabel("Buchungssatz");
		this.transactionField = new JTextField();
		this.transactionField.setPreferredSize(new Dimension(150, 20));
		JLabel betragLabel = new JLabel("Betrag");
		this.amountField = new JTextField();
		this.amountField.setPreferredSize(new Dimension(150, 20));
		JLabel belegLabel = new JLabel("Beleg Nr.");
		this.receiptField = new JTextField();
		this.receiptField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(dateLabel);
		//formPanel.add(this.dateField);
		formPanel.add(datePicker);
		formPanel.add(sollLabel);
		formPanel.add(this.sollField);
		formPanel.add(habenLabel);
		formPanel.add(this.habenField);
		formPanel.add(buchungLabel);
		formPanel.add(this.transactionField);
		formPanel.add(betragLabel);
		formPanel.add(this.amountField);
		formPanel.add(belegLabel);
		formPanel.add(this.receiptField);

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
				boolean hasError = false;
				String errorMessage = "";
				Transaction newTrans = new Transaction();
				String regex = "[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}";
				//System.out.println((java.sql.Date) datePicker.getModel().getValue());
//				System.out.println(new java.sql.Date((java.util.Date) datePicker.getModel().getValue()));
				java.util.Date selectedDate = (Date) datePicker.getModel().getValue();
				
				if (selectedDate != null) {
					newTrans.setDate(new java.sql.Date(selectedDate.getTime()));
				} else {
					errorMessage = "Datum muss ausgef\u00fcllt sein!";
					hasError = true;
				}
				MandantDBHelper helper = new MandantDBHelper();
				Account sollAccount = helper.getAccountByName(fromAccounts.get(sollField.getSelectedIndex()));
				newTrans.setFk_SollKonto(sollAccount.getId());
				Account habenAccount = helper.getAccountByName(toAccounts.get(habenField.getSelectedIndex()));
				newTrans.setFk_HabenKonto(habenAccount.getId());
				newTrans.setBezeichnung(transactionField.getText());
				double amount = 0.00;
				try {
					amount = Integer.parseInt(amountField.getText());
					newTrans.setAmount(amount);
					newTrans.setBelegNr(Integer.parseInt(receiptField.getText()));
				} catch (NumberFormatException ex) {
					errorMessage += "\nBetrag und Beleg-Nr. m\u00fcssen eine Zahl sein!";
					hasError = true;
				}
				
				if (!hasError) {
					newTrans.createInDB();
					calculateAccountAmounts(sollAccount, habenAccount, amount);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, errorMessage, "Fehler", JOptionPane.ERROR_MESSAGE);
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
	
	private void calculateAccountAmounts(Account soll, Account haben, double amount) {
		double newSollBalance = 0.00;
		double newHabenBalance = 0.00;

		// Active Account or Expenses Account -> Soll +
		if (soll.getFk_AccountType() == 1 || soll.getFk_AccountType() == 3) {
			newSollBalance = soll.getBalance() + amount;
		}
		
		// Active Account or Expenses Account -> Haben -
		if (haben.getFk_AccountType() == 1 || haben.getFk_AccountType() == 3) {
			newHabenBalance = haben.getBalance() - amount;
		}
		
		// Passive Account or Earnings Account -> Soll -
		if (soll.getFk_AccountType() == 2 || soll.getFk_AccountType() == 4) {
			newSollBalance = soll.getBalance() - amount;
		}
		
		// Passive Account or Earnings Account -> Haben +
		if (haben.getFk_AccountType() == 2 || haben.getFk_AccountType() == 4) {
			newHabenBalance = haben.getBalance() + amount;
		}
		
		soll.setBalance(newSollBalance);
		soll.updateInDB();
		haben.setBalance(newHabenBalance);
		haben.updateInDB();
	}

}
