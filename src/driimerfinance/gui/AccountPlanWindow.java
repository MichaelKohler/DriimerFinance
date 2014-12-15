package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.AccountType;

/**
 * Account plan overview
 * 
 * (c) 2014 Driimer Finance
 */
public class AccountPlanWindow {

	JFrame frame = new JFrame("DriimerFinance - Kontenplan");
	ImageIcon icon = new ImageIcon("images/DF.png");
	JTable accountTable = new JTable();
	MandantDBHelper db = new MandantDBHelper();
	AccountPlanWindow parent = this;

	/**
	 * Constructor
	 */
	public AccountPlanWindow() {
		createGUI();
	}

	/**
	 * Creates the GUI
	 */
	private void createGUI() {
		addForm();
		addButtons();
		frame.setSize(650, 500);
		GUIHelper.centerFrame(frame);
		this.frame.setIconImage(icon.getImage());
		frame.setVisible(true);
	}

	/**
	 * Adds the content of the window.
	 */
	private void addForm() {
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		String[] headers = { "ID", "Nummer", "Name", "Typ", "Kapitalkonto" };
		Object[][] data = getData();

		accountTable = new JTable(new DefaultTableModel(data, headers) {
			private static final long serialVersionUID = 1L;

			// make the table non-editable
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		
		accountTable.removeColumn(accountTable.getColumn(headers[0])); // remove the first column (id) from the view
		// center the first column of the view
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		accountTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    
		accountTable.setPreferredScrollableViewportSize(new Dimension(640, 380));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);

		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}

	/**
	 * Gets the data for the view once the window is started
	 * 
	 * @return array of all the data to be used in the table
	 */
	private Object[][] getData() {
		List<Account> allAccounts = db.getAllAccounts();
		Object[][] rows = new Object[allAccounts.size()][4];
		for (int i = 0; i < allAccounts.size(); i++) {
			Account acc = allAccounts.get(i);
			AccountType type = new AccountType();
			type.setId(acc.getFk_AccountType());
			String isCapitalAccount = acc.getCapitalAccount() ? "Ja" : "Nein"; // convert the database entry to an user-readable string
			Object[] row = { acc.getId(), acc.getNumber(), acc.getName(), type.getTypeAsString(), isCapitalAccount };
			rows[i] = row;
		}
		return rows;
	}

	/**
	 * Adds the button on the bottom of the frame.
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		
		JButton editButton = new JButton("Konto bearbeiten");
		editButton.addActionListener(new AccountEditAction());
		
		JButton deleteButton = new JButton("Konto l\u00f6schen");
		deleteButton.addActionListener(new AccountDeleteAction());
		
		JButton closeButton = new JButton("Fenster schliessen");
		closeButton.addActionListener(new FrameCloseAction(frame));
		
		buttonPanel.add(editButton, BorderLayout.WEST);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		buttonPanel.add(closeButton, BorderLayout.EAST);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(closeButton);
	}

	/**
	 * Adds the new account to the account table.
	 * 
	 * @param acc to add to the table
	 */
	public void addAccountToTable(Account acc) {
		DefaultTableModel model = (DefaultTableModel) (accountTable.getModel());
		Object[] newRow = { acc.getId().toString(), acc.getNumber().toString(), acc.getName(),
				acc.getFk_AccountType() };
		model.addRow(newRow);
		frame.repaint();
	}
	
	public class AccountEditAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selRow = 0;
			selRow = accountTable.getSelectedRow();
			// if there is a row selected
			if (selRow != -1) {
				MandantDBHelper helper = new MandantDBHelper();
				DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
				// get transaction data from table model
				int accountid = Integer.parseInt(model.getValueAt(selRow, 0).toString());
				int number = Integer.parseInt(model.getValueAt(selRow, 1).toString());
				String name = model.getValueAt(selRow, 2).toString();
				int fk_AccountType = helper.getAccountTypeIdByName(model.getValueAt(selRow, 3).toString());
				Boolean capitalAccount = Boolean.parseBoolean(model.getValueAt(selRow, 4).toString());
				new EditAccountWindow(parent, accountid, number, name, fk_AccountType, capitalAccount, model, selRow);
			}
		}
	}
	
	/**
	 * ActionListener to delete an account
	 */
	public class AccountDeleteAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selRow = 0;
			selRow = accountTable.getSelectedRow();
			// if there is a row selected
			if (selRow != -1) {
				// ask the user if he really wants to delete the entry
				Object[] options = {"Ja", "Nein"};
				int eingabe = JOptionPane.showOptionDialog(
								null,
								"Sind Sie sicher, Konto wird unwiderruflich gel\u00f6scht?",
								"Best\u00e4tigung",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,
							    null,
							    options,
							    options[1]);
				if (eingabe == 0) {
					DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
					// get accountid from table model
					int accountId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
					// get the transaction from database and delete it. (in
					// database as well as in the table)
					boolean done = db.deleteAccountById(accountId);
					if (done)
					{
						model.removeRow(selRow);
					}
				}
			}
		}
	}
}
