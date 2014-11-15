package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class AccountPlanWindow {

	JFrame _frame = new JFrame("DriimerFinance - Kontenplan");
	JTable _accountTable = new JTable();
	AccountPlanWindow _parent = this;
	String[] _headers = { "Nummer", "Name", "Typ", "Kapitalkonto" };
	Object[][] _data = { { "Test", "Test", "Test", "x" } };
	
    public AccountPlanWindow() {
        createGUI();
    }
    
    private void createGUI() {
    	    addForm();
		addButtons();
		_frame.setSize(400, 500);
		GUIHelper.centerFrame(_frame);
		_frame.setVisible(true);
    }
    
    private void addForm() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton addButton = new JButton("Konto hinzufügen");
		addButton.setPreferredSize(new Dimension(400, 20));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddAccountWindow(_parent);
			}
		});
		buttonPanel.add(addButton);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		_accountTable = new JTable(new DefaultTableModel(_data, _headers));
		_accountTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
		_accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(_accountTable);
		tablePanel.add(scrollPane);

		_frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		_frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// we don't need to save this since an account plan is only a virtual entity
				_frame.dispose();
			}
		});
		buttonPanel.add(okButton, BorderLayout.CENTER);
		_frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void addAccountToTable(Account acc) {
		DefaultTableModel model = (DefaultTableModel) (_accountTable.getModel());
		Object[] newRow = { acc.getId().toString(), acc.getName(), acc.getFk_AccountType() };
		model.addRow(newRow);
		_frame.repaint();
	}
}
