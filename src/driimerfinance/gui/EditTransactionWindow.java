package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.ComboboxHelper;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Transaction;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class EditTransactionWindow {
	JournalWindow parent = null;
	JFrame frame = new JFrame("DriimerFinance - Buchung bearbeiten");
	ImageIcon icon = new ImageIcon("images/DF.png");
	
	int transactionId;
	String stDate;
	int fk_fromAccount;
	int fk_toAccount;
	String description;
	String amount; 
	String receiptNumber;
	JComboBox sollField = new JComboBox();
	JComboBox habenField = new JComboBox();
	JTextField transactionField =null;
	JTextField amountField = null;
	JTextField receiptField = null;
	JDatePickerImpl datePicker = null;
	private DefaultTableModel model = null;
	private int row = 0;

	/**
	 * Constructor
	 */
	public EditTransactionWindow(JournalWindow jouWin, int transactionId, String date, int fk_fromAccount, int fk_toAccount, String description, double amount, int receiptNumber, DefaultTableModel model, int row) {
		this.parent = jouWin;
		this.transactionId = transactionId;
		this.stDate = date;
		this.fk_fromAccount = fk_fromAccount;
		this.fk_toAccount = fk_toAccount;
		this.description = description;
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setGroupingUsed(false); // we don't want to use separators here
		this.amount = formatter.format(amount);
		this.receiptNumber = Integer.toString(receiptNumber);
		this.model = model;
		this.row = row;
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
		GridLayout layout = new GridLayout(6, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date date;
		try {
			date = formatter.parse(this.stDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			datePicker.getModel().setDay(calendar.get(Calendar.DAY_OF_MONTH));
			datePicker.getModel().setMonth(calendar.get(Calendar.MONTH));
			datePicker.getModel().setYear(calendar.get(Calendar.YEAR));
			datePicker.getModel().setSelected(true);
		} catch (ParseException e) {
		}
		
		JLabel dateLabel = new JLabel("Datum");
		JLabel sollLabel = new JLabel("Soll Konto");
		
		MandantDBHelper helper = new MandantDBHelper();
		List<Account> accs = helper.getAllAccounts();
		for (Account acc : accs) {
			Object[] itemData = new Object[] {acc.getId(), acc.getName()};
			this.sollField.addItem(itemData);
			this.habenField.addItem(itemData);
			if (acc.getId()==this.fk_fromAccount){
				this.sollField.setSelectedItem(itemData);
			}
			if (acc.getId()==this.fk_toAccount){
				this.habenField.setSelectedItem(itemData);
			}
		}
		this.sollField.setRenderer(new ComboboxHelper());
		this.sollField.setPreferredSize(new Dimension(150, 20));
		JLabel habenLabel = new JLabel("Haben Konto");
		this.habenField.setRenderer(new ComboboxHelper());
		this.habenField.setPreferredSize(new Dimension(150, 20));
		JLabel buchungLabel = new JLabel("Buchungssatz");
		this.transactionField = new JTextField(description);
		this.transactionField.setPreferredSize(new Dimension(150, 20));
		JLabel betragLabel = new JLabel("Betrag");
		this.amountField = new JTextField(amount);
		this.amountField.setPreferredSize(new Dimension(150, 20));
		JLabel belegLabel = new JLabel("Beleg Nr.");
		this.receiptField = new JTextField(receiptNumber);
		this.receiptField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(dateLabel);
		formPanel.add(this.datePicker);
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
		okButton.addActionListener(new SaveEditedTransactionAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}
	
	public class SaveEditedTransactionAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options = {"Ja", "Nein"};
			int eingabe = JOptionPane.showOptionDialog(
							null,
							"Sind Sie sicher, Buchung wird ge\u00e4ndert?",
							"Best\u00e4tigung",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[1]);
			if (eingabe == 0) {
				boolean hasError = false;
				String errorMessage = "";
				MandantDBHelper helper = new MandantDBHelper();
				Transaction transToEdit = helper.getTransactionById(transactionId);
				Date date = new Date();
				String regex = "[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}";
				java.util.Date selectedDate = (Date) datePicker.getModel().getValue();
				if (selectedDate != null) {
					transToEdit.setDate(new java.sql.Date(selectedDate.getTime()));
				} else {
					errorMessage = "Datum muss ausgef\u00fcllt sein!";
					hasError = true;
				}
				Object[] itemData = (Object[])sollField.getSelectedItem();
				int sollAccountId = Integer.parseInt(itemData[0].toString());
				itemData = (Object[])habenField.getSelectedItem();
				int habenAccountId = Integer.parseInt(itemData[0].toString());
				transToEdit.setFk_SollKonto(sollAccountId);
				transToEdit.setFk_HabenKonto(habenAccountId);
				transToEdit.setBezeichnung(transactionField.getText());
				try {
					transToEdit.setAmount(Double.parseDouble(amountField.getText()));
					transToEdit.setBelegNr(Integer.parseInt(receiptField.getText()));
				} catch (NumberFormatException ex) {
					errorMessage += "\nBetrag und Beleg-Nr. m\u00fcssen eine Zahl sein!";
					hasError = true;
				}
				if (!hasError) {
					transToEdit.updateInDB();
					
					//Update Jtable in GUI
					Account sollAccount = helper.getAccountById(transToEdit.getFk_SollKonto());
					Account habenAccount = helper.getAccountById(transToEdit.getFk_HabenKonto());
					Object[] newRow = { transToEdit.getId().toString(),
							transToEdit.getStringDate(), sollAccount.getName(),
							habenAccount.getName(), transToEdit.getBezeichnung(),
							FinanceHelper.formatAmount(transToEdit.getBetrag()),
							transToEdit.getBelegNr() };
					for(int i=0; i< newRow.length; i++) {
						model.setValueAt(newRow[i].toString(), row, i);
					}

					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, errorMessage, "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}