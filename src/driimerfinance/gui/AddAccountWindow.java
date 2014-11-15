package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class AddAccountWindow {

	JFrame frame = new JFrame("DriimerFinance - Konto hinzufügen");
	AccountPlanWindow parent = null;
	
	String[] types = { "Test1", "Test2" };
	
	JTextField numberField = null;
	JTextField nameField = null;
	JComboBox typeField = null;
	
    public AddAccountWindow(AccountPlanWindow accPlanWin) {
    	    this.parent = accPlanWin;
        createGUI();
    }
    
    private void createGUI() {
        addForm();
		addButtons();
		this.frame.setSize(400, 200);
		GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
    }
    
    private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(3, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel numberLabel = new JLabel("Nummer");
		this.numberField = new JTextField();
		this.numberField.setPreferredSize(new Dimension(150, 20));
		JLabel nameLabel = new JLabel("Name");
		this.nameField = new JTextField();
		this.nameField.setPreferredSize(new Dimension(150, 20));
		JLabel typeLabel = new JLabel("Typ");
		this.typeField = new JComboBox(types);
		this.typeField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(numberLabel);
		formPanel.add(this.numberField);
		formPanel.add(nameLabel);
		formPanel.add(this.nameField);
		formPanel.add(typeLabel);
		formPanel.add(this.typeField);

		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Account newAcc = new Account();
				try {
					newAcc.setId(Integer.parseInt(numberField.getText()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Die Konto-Nr. muss eine Nummer sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (nameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Der Name muss ausgefüllt sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				newAcc.setName(nameField.getText());
				int typeCode = typeField.getSelectedIndex();
				newAcc.setFk_AccountType(typeCode);
				newAcc.createInDB();
				parent.addAccountToTable(newAcc);
				frame.dispose();
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
