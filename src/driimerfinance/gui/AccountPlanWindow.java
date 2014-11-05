package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import driimerfinance.helpers.GUIHelper;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
*/
public class AccountPlanWindow {

	JFrame _frame = new JFrame("DriimerFinance - Kontenplan");
	
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
		addButton.setPreferredSize(new Dimension(440, 20));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAccountWindow accWin = new AddAccountWindow();
			}
		});
		buttonPanel.add(addButton);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JTable accountTable = new JTable();
		tablePanel.add(accountTable);

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
}
