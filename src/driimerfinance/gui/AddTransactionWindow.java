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

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.AccountType;
import driimerfinance.models.AccountTypeEnum;
import driimerfinance.models.Transaction;
import driimerfinance.helpers.ComboboxHelper;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class AddTransactionWindow {

	JFrame frame = new JFrame("DriimerFinance - Buchung hinzuf\u00fcgen");
	ImageIcon icon = new ImageIcon("images/DF.png");
	
	JTextField dateField = null;
	JComboBox sollField = new JComboBox();
	JComboBox habenField = new JComboBox();
	JTextField transactionField =null;
	JTextField amountField = null;
	JTextField receiptField = null;
	JDatePickerImpl datePicker = null;
	JTable table = new JTable();

	/**
	 * Constructor
	 */
	public AddTransactionWindow(JTable table) {
		setTable(table);
		createGUI();
	}
	/**
	 * Constructor
	 */
	public AddTransactionWindow() {
		setTable(table);
		createGUI();
	}
	
	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
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
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		datePicker.getModel().setDay(calendar.get(Calendar.DAY_OF_MONTH));
		datePicker.getModel().setMonth(calendar.get(Calendar.MONTH));
		datePicker.getModel().setYear(calendar.get(Calendar.YEAR));
		datePicker.getModel().setSelected(true);
		
		JLabel dateLabel = new JLabel("Datum");
		this.dateField = new JTextField();
		this.dateField.setPreferredSize(new Dimension(150, 20));
		JLabel sollLabel = new JLabel("Soll Konto");
		
		MandantDBHelper helper = new MandantDBHelper();
		List<Account> accs = helper.getAllAccounts();
		for (Account acc : accs) {
			Object[] itemData = new Object[] {acc.getId(), acc.getName()};
			this.sollField.addItem(itemData);
			this.habenField.addItem(itemData);
		}

		this.sollField.setRenderer(new ComboboxHelper());
		this.habenField.setRenderer(new ComboboxHelper());
		this.sollField.setPreferredSize(new Dimension(150, 20));
		
		JLabel habenLabel = new JLabel("Haben Konto");
		
		this.habenField.setPreferredSize(new Dimension(150, 20));
		
		JLabel buchungLabel = new JLabel("Text");
		this.transactionField = new JTextField();
		this.transactionField.setPreferredSize(new Dimension(150, 20));
		JLabel betragLabel = new JLabel("Betrag");
		this.amountField = new JTextField();
		this.amountField.setPreferredSize(new Dimension(150, 20));
		JLabel belegLabel = new JLabel("Beleg Nr.");
		this.receiptField = new JTextField();
		this.receiptField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(dateLabel);
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
		okButton.addActionListener(new SaveTransactionAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}
	
	private void calculateAccountAmounts(Account soll, Account haben, double amount) {
		double newSollBalance = 0.00;
		double newHabenBalance = 0.00;

		// Active Account or Expenses Account -> Soll +
		if (soll.getFk_AccountType() == AccountTypeEnum.ACTIVE.getId() || soll.getFk_AccountType() == AccountTypeEnum.SPENDING.getId()) {
			newSollBalance = soll.getBalance() + amount;
		}
		
		// Active Account or Expenses Account -> Haben -
		if (haben.getFk_AccountType() == AccountTypeEnum.ACTIVE.getId() || haben.getFk_AccountType() == AccountTypeEnum.SPENDING.getId()) {
			newHabenBalance = haben.getBalance() - amount;
		}
		
		// Passive Account or Earnings Account -> Soll -
		if (soll.getFk_AccountType() == AccountTypeEnum.PASSIVE.getId() || soll.getFk_AccountType() == AccountTypeEnum.EARNING.getId()) {
			newSollBalance = soll.getBalance() - amount;
		}
		
		// Passive Account or Earnings Account -> Haben +
		if (haben.getFk_AccountType() == AccountTypeEnum.PASSIVE.getId() || haben.getFk_AccountType() == AccountTypeEnum.EARNING.getId()) {
			newHabenBalance = haben.getBalance() + amount;
		}
		
		soll.setBalance(newSollBalance);
		soll.updateInDB();
		haben.setBalance(newHabenBalance);
		haben.updateInDB();
	}

	public class SaveTransactionAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean hasError = false;
			String errorMessage = "";
			Transaction newTrans = new Transaction();
			String regex = "[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}";
			java.util.Date selectedDate = (Date) datePicker.getModel().getValue();
			
			if (selectedDate != null) {
				newTrans.setDate(new java.sql.Date(selectedDate.getTime()));
			} else {
				errorMessage = "Datum muss ausgef\u00fcllt sein!";
				hasError = true;
			}
			MandantDBHelper helper = new MandantDBHelper();
			Object[] itemData = (Object[])sollField.getSelectedItem();
			int sollAccountId = Integer.parseInt(itemData[0].toString());
			itemData = (Object[])habenField.getSelectedItem();
			int habenAccountId = Integer.parseInt(itemData[0].toString());
			newTrans.setFk_SollKonto(sollAccountId);
			newTrans.setFk_HabenKonto(habenAccountId);
			newTrans.setBezeichnung(transactionField.getText());
			double amount = 0.00;
			try {
				amount = Double.parseDouble(amountField.getText());
				newTrans.setAmount(amount);
				newTrans.setBelegNr(Integer.parseInt(receiptField.getText()));
			} catch (NumberFormatException ex) {
				errorMessage += "\nBetrag und Beleg-Nr. m\u00fcssen eine Zahl sein!";
				hasError = true;
			}
			
			if (!hasError) {
				newTrans.createInDB();
				
				//Update Swing table
				DefaultTableModel model = (DefaultTableModel) (table.getModel());
				Account sollAccount = helper.getAccountById(newTrans.getFk_SollKonto());
				Account habenAccount = helper.getAccountById(newTrans.getFk_HabenKonto());
				Object[] newRow = { newTrans.getId().toString(),
						newTrans.getStringDate(), sollAccount.getName(),
						habenAccount.getName(), newTrans.getBezeichnung(),
						FinanceHelper.formatAmount(newTrans.getBetrag()),
						newTrans.getBelegNr() };
				model.addRow(newRow);
				
				calculateAccountAmounts(sollAccount, habenAccount, amount);
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(frame, errorMessage, "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
