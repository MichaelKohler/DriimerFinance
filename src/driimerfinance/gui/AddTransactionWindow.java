package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Transaction;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class AddTransactionWindow {

	JFrame frame = new JFrame("DriimerFinance - Buchung hinzufügen");
	String[] fromAccounts = { "Foo", "Bla", "Bar", "Test" };
	String[] toAccounts = { "Baz", "Blubb" };
	
	JTextField dateField = null;
	JComboBox sollField = null;
	JComboBox habenField = null;
	JTextField transactionField =null;
	JTextField amountField = null;
	JTextField receiptField = null;

	/**
	 * Constructor
	 */
	public AddTransactionWindow() {
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

		JLabel dateLabel = new JLabel("Datum");
		this.dateField = new JTextField();
		this.dateField.setPreferredSize(new Dimension(150, 20));
		JLabel sollLabel = new JLabel("Soll Konto");
		this.sollField = new JComboBox(this.fromAccounts);
		this.sollField.setPreferredSize(new Dimension(150, 20));
		JLabel habenLabel = new JLabel("Haben Konto");
		this.habenField = new JComboBox(this.toAccounts);
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
		formPanel.add(this.dateField);
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
				Date date = new Date();
				try {
					date = DateFormat.getDateInstance().parse(dateField.getText());
				} catch (ParseException ex) {
					errorMessage = "Das Datum muss ein korrektes Format haben!\n";
					hasError = true;
				}
				newTrans.setDate(new java.sql.Date(date.getTime()));
				//Account fromKonto = new Account(); // TODO: get by id
				//newTrans.setFk_SollKonto(fromKonto.getId());
				//Account toKonto = new Account(); // TODO: get by id
				//newTrans.setFk_HabenKonto(toKonto.getId());
				newTrans.setBezeichnung(transactionField.getText());
				try {
					newTrans.setAmount(Integer.parseInt(amountField.getText()));
					newTrans.setBelegNr(Integer.parseInt(receiptField.getText()));
				} catch (NumberFormatException ex) {
					errorMessage = "Betrag und Beleg-Nr. müssen eine Zahl sein!";
					hasError = true;
				}
				
				if (!hasError) {
					newTrans.createInDB();
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
	}

}
