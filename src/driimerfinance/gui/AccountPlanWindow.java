package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;

/**
 * Account plan overview
 * 
 * (c) 2014 Driimer Finance
*/
public class AccountPlanWindow {

	JFrame frame = new JFrame("DriimerFinance - Kontenplan");
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
		frame.setVisible(true);
    }
    
    /**
     * Adds the content of the window.
     */
    private void addForm() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton addButton = new JButton("Konto hinzuf\u00fcgen");
		addButton.setPreferredSize(new Dimension(400, 20));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddAccountWindow(parent);
			}
		});
		buttonPanel.add(addButton);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		String[] headers = { "Nummer", "Name", "Typ", "Kapitalkonto" };
		Object[][] data = {  };
		
		accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);

		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
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
				// we don't need to save this since an account plan is only a virtual entity
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
	 * @param acc to add to the table
	 */
	public void addAccountToTable(Account acc) {
		DefaultTableModel model = (DefaultTableModel) (accountTable.getModel());
		Object[] newRow = { acc.getId().toString(), acc.getName(), acc.getFk_AccountType() };
		model.addRow(newRow);
		frame.repaint();
	}
}
