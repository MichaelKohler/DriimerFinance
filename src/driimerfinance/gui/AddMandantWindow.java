package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
public class AddMandantWindow {

	JFrame _frame = new JFrame("DriimerFinance - Mandant hinzufügen");

	public AddMandantWindow() {
		createGUI();
	}

	private void createGUI() {
		addForm();
		addButtons();
		_frame.setSize(400, 100);
		GUIHelper.centerFrame(_frame);
		_frame.setVisible(true);
	}

	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel nameLabel = new JLabel("Name");
		JTextField nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(nameLabel);
		formPanel.add(nameField);

		_frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement save of Mandant
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
