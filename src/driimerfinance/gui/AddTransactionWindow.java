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
	
	JTextField _dateField = null;
	JComboBox _sollField = null;
	JComboBox _habenField = null;
	JTextField _buchungField =null;
	JTextField _betragField = null;
	JTextField _belegField = null;

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
		_dateField = new JTextField();
		_dateField.setPreferredSize(new Dimension(150, 20));
		JLabel sollLabel = new JLabel("Soll Konto");
		_sollField = new JComboBox(_sollKonti);
		_sollField.setPreferredSize(new Dimension(150, 20));
		JLabel habenLabel = new JLabel("Haben Konto");
		_habenField = new JComboBox(_habenKonti);
		_habenField.setPreferredSize(new Dimension(150, 20));
		JLabel buchungLabel = new JLabel("Buchungssatz");
		_buchungField = new JTextField();
		_buchungField.setPreferredSize(new Dimension(150, 20));
		JLabel betragLabel = new JLabel("Betrag");
		_betragField = new JTextField();
		_betragField.setPreferredSize(new Dimension(150, 20));
		JLabel belegLabel = new JLabel("Beleg Nr.");
		_belegField = new JTextField();
		_belegField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(dateLabel);
		formPanel.add(_dateField);
		formPanel.add(sollLabel);
		formPanel.add(_sollField);
		formPanel.add(habenLabel);
		formPanel.add(_habenField);
		formPanel.add(buchungLabel);
		formPanel.add(_buchungField);
		formPanel.add(betragLabel);
		formPanel.add(_betragField);
		formPanel.add(belegLabel);
		formPanel.add(_belegField);

		_frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: implement save of Buchung
				// TODO: use _whatever variables from class scope to get text from fields
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
