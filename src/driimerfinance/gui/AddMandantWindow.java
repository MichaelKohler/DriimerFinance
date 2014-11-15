package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
 */
public class AddMandantWindow {

	JFrame frame = new JFrame("DriimerFinance - Mandant hinzufügen");
	JTextField nameField = null;

	public AddMandantWindow() {
		createGUI();
	}

	private void createGUI() {
		addForm();
		addButtons();
		this.frame.setSize(400, 100);
		GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
	}

	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel nameLabel = new JLabel("Name");
		this.nameField = new JTextField();
		this.nameField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(nameLabel);
		formPanel.add(this.nameField);

		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Der Name muss ausgefüllt sein!", "Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Mandant newMandant = new Mandant();
				newMandant.setName(nameField.getText());
				newMandant.createInDB();
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
