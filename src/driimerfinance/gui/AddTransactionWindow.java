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
public class AddTransactionWindow {

	JFrame _frame = new JFrame("DriimerFinance - Buchung hinzufügen");
	String[] _sollKonti = { "Foo", "Bla", "Bar", "Test" };
	String[] _habenKonti = { "Baz", "Blubb" };

	public AddTransactionWindow() {
		createGUI();
	}

	private void createGUI() {
		addForm();
		addButtons();
		_frame.setSize(400, 300);
		GUIHelper.centerFrame(_frame);
		_frame.setVisible(true);
	}

	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(6, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel dateLabel = new JLabel("Datum");
		JTextField dateField = new JTextField();
		dateField.setPreferredSize(new Dimension(150, 20));
		JLabel sollLabel = new JLabel("Soll Konto");
		JComboBox sollField = new JComboBox(_sollKonti);
		sollField.setPreferredSize(new Dimension(150, 20));
		JLabel habenLabel = new JLabel("Haben Konto");
		JComboBox habenField = new JComboBox(_habenKonti);
		habenField.setPreferredSize(new Dimension(150, 20));
		JLabel buchungLabel = new JLabel("Buchungssatz");
		JTextField buchungField = new JTextField();
		buchungField.setPreferredSize(new Dimension(150, 20));
		JLabel betragLabel = new JLabel("Betrag");
		JTextField betragField = new JTextField();
		betragField.setPreferredSize(new Dimension(150, 20));
		JLabel belegLabel = new JLabel("Beleg Nr.");
		JTextField belegField = new JTextField();
		belegField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(dateLabel);
		formPanel.add(dateField);
		formPanel.add(sollLabel);
		formPanel.add(sollField);
		formPanel.add(habenLabel);
		formPanel.add(habenField);
		formPanel.add(buchungLabel);
		formPanel.add(buchungField);
		formPanel.add(betragLabel);
		formPanel.add(betragField);
		formPanel.add(belegLabel);
		formPanel.add(belegField);

		_frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement save of Buchung
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
