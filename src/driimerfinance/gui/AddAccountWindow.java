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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.helpers.GUIHelper;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class AddAccountWindow {

	JFrame _frame = new JFrame("DriimerFinance - Konto hinzufügen");
	
	String[] _types = { "Test1", "Test2" };
	
	JTextField _numberField = null;
	JTextField _nameField = null;
	JComboBox _typeField = null;
	
    public AddAccountWindow() {
        createGUI();
    }
    
    private void createGUI() {
        addForm();
		addButtons();
		_frame.setSize(400, 200);
		GUIHelper.centerFrame(_frame);
		_frame.setVisible(true);
    }
    
    private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(3, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel numberLabel = new JLabel("Nummer");
		_numberField = new JTextField();
		_numberField.setPreferredSize(new Dimension(150, 20));
		JLabel nameLabel = new JLabel("Name");
		_nameField = new JTextField();
		_nameField.setPreferredSize(new Dimension(150, 20));
		JLabel typeLabel = new JLabel("Typ");
		_typeField = new JComboBox(_types);
		_typeField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(numberLabel);
		formPanel.add(_numberField);
		formPanel.add(nameLabel);
		formPanel.add(_nameField);
		formPanel.add(typeLabel);
		formPanel.add(_typeField);

		_frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement save of Account
				// TODO: use _nameField and similar variables for getting the name
				_frame.dispose();
			}
		});
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_frame.dispose();
			}
		});
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		_frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
}
