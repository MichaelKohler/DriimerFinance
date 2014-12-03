package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
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
		frame.setSize(400, 500);
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

		accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);

		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}

	/**
	 * Gets the data for the view once the window is started
	 */
	private Object[][] getData() {
		MandantDBHelper helper = new MandantDBHelper();
		List<Account> allAccounts = helper.getAllAccounts();
		Object[][] rows = new Object[allAccounts.size()][4];
		for (int i = 0; i < allAccounts.size(); i++) {
			Account acc = allAccounts.get(i);
			AccountType type = new AccountType();
			type.setId(acc.getFk_AccountType());
			Object[] row = { acc.getId(), acc.getNumber(), acc.getName(), type.getTypeAsString(), acc.getCapitalAccount() };
			rows[i] = row;
		}
		return rows;
	}

	/**
	 * Adds the button on the bottom of the frame.
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// we don't need to save this since an account plan is only a
				// virtual entity
				frame.dispose();
			}
		});
		buttonPanel.add(okButton, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}

	/**
	 * Adds the new account to the account table.
	 * 
	 * @param acc
	 *            to add to the table
	 */
	public void addAccountToTable(Account acc) {
		DefaultTableModel model = (DefaultTableModel) (accountTable.getModel());
		Object[] newRow = { acc.getId().toString(), acc.getNumber().toString(), acc.getName(),
				acc.getFk_AccountType() };
		model.addRow(newRow);
		frame.repaint();
	}
}
