package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.AccountType;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class EditAccountWindow {
	AccountPlanWindow parent = null;
	JFrame frame = new JFrame("DriimerFinance - Konto bearbeiten");
	ImageIcon icon = new ImageIcon("images/DF.png");
	
	private int accountId;
	private int number;
	private String name = null;
	private int fk_AccountType = 0;
	private boolean capitalAccount = false;
	ArrayList<String> types = new ArrayList<String>();
	private DefaultTableModel model = null;
	private int row = 0;

	JComboBox typeField = new JComboBox();
	JTextField numberField = null;
	JTextField nameField = null;
	private JCheckBox isCapAccount;

	/**
	 * Constructor
	 */
	public EditAccountWindow(AccountPlanWindow accWin, int accountId, int number, String name, int fk_AccountType, boolean capitalAccount, DefaultTableModel model, int row) {
		this.parent = accWin;
		this.accountId = accountId;
		this.number = number;
		this.name = name;
		this.fk_AccountType = fk_AccountType;
		this.capitalAccount = capitalAccount;
		this.model = model;
		this.row = row;
		getData();
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
     * Gets the types from the DB
     */
    private void getData() {
        MandantDBHelper helper = new MandantDBHelper();
        List<AccountType> allTypes = helper.getAllAccountTypes();
        for (AccountType type : allTypes) {
            types.add(type.getName());
        }
    }
	/**
	 * Adds the form
	 */
	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(6, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel numberLabel = new JLabel("Nummer");
		this.numberField = new JTextField(Integer.toString(number));
		this.numberField.setPreferredSize(new Dimension(150, 20));
		JLabel nameLabel = new JLabel("Name");
		this.nameField = new JTextField(name);
		this.nameField.setPreferredSize(new Dimension(150, 20));
		JLabel typeLabel = new JLabel("Typ");
		this.typeField = new JComboBox(types.toArray());
		this.typeField.setSelectedItem(types.toArray()[fk_AccountType-1]);
		this.typeField.setPreferredSize(new Dimension(150, 20));
		JLabel capLabel = new JLabel("Kapitalkonto?");
		this.isCapAccount = new JCheckBox();
		this.isCapAccount.setSelected(capitalAccount);

		formPanel.add(numberLabel);
		formPanel.add(this.numberField);
		formPanel.add(nameLabel);
		formPanel.add(this.nameField);
		formPanel.add(typeLabel);
		formPanel.add(this.typeField);
		formPanel.add(capLabel);
		formPanel.add(this.isCapAccount);

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
	
	/**
	 * ActionListener save an edited transaction
	 */
	public class SaveEditedTransactionAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// ask the user for confirmation
			Object[] options = {"Ja", "Nein"};
			int eingabe = JOptionPane.showOptionDialog(
							null,
							"Sind Sie sicher, Konto wird ge\u00e4ndert?",
							"Best\u00e4tigung",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[1]);
			if (eingabe == 0) { // if the user said yes, (abort otherwise)
				boolean hasError = false;
				String errorMessage = "";
				MandantDBHelper helper = new MandantDBHelper();
				Account accToEdit = helper.getAccountById(accountId);
				try {
					accToEdit.setNumber(Integer.parseInt(numberField.getText()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Die Konto-Nr. muss eine Nummer sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (nameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Der Name muss ausgef\u00fcllt sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				accToEdit.setName(nameField.getText());
				int typeCode = typeField.getSelectedIndex()+1;
				accToEdit.setFk_AccountType(typeCode);
				accToEdit.setBalance(helper.getAccountById(accountId).getBalance());
				accToEdit.setCapitalAccount(isCapAccount.isSelected());
				if (!hasError) {
					helper.updateAccount(accToEdit);
					//Update JTable in GUI
					accToEdit.getFk_AccountType();
					Object[] newRow = { accToEdit.getId().toString(),
							accToEdit.getNumber(), accToEdit.getName(),
							helper.getAccountTypeById(accToEdit.getFk_AccountType()).getName(), accToEdit.getCapitalAccount()};
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