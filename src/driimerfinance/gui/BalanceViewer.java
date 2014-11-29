package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Balance;

/**
 * Shows the Balance in a two column view.
 * 
 * (c) 2014 Driimer Finance
*/
public class BalanceViewer {
	JFrame frame = new JFrame("DriimerFinance - Balance");
	
	/**
	 * Constructor
	 */
    public BalanceViewer() {
        createGUI();
        addButtons();
    }
    
    /**
     * Creates the GUI.
     */
    private void createGUI() {
    	    this.frame.setSize(500, 400);
    	    GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		String[] headers = { "Aktiven", "Betrag", "Passiven", "Betrag" };
		Object[][] data = prepareData();
		
		JTable accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(490, 390));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);
		this.frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
    }
    
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton PDFExportButton = new JButton("PDF Export");
		PDFExportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		buttonPanel.add(PDFExportButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(cancelButton);
	}
    
    /**
     * Prepares the data for display.
     * 
     * @return data for display
     */
    private Object[][] prepareData() {
    	    Balance balance = new Balance();
        List<Account> active = balance.getActivePositions();
        List<Account> passive = balance.getPassivePositions();
        
        int maxLength = active.size() >= passive.size() ? active.size() : passive.size();
        int columns = 4;
        Object[][] rows = new Object[maxLength+2][columns];
        double totalActive = 0;
        double totalPassive = 0;
        for (int i = 0; i < maxLength; i++) {
        	    String nameActive = "";
        	    String balanceActive = "";
        	    if (i < active.size()) {
        	    	    nameActive = active.get(i).getName();
        	    	    balanceActive = FinanceHelper.formatAmount(active.get(i).getBalance());
        	    	    totalActive += active.get(i).getBalance();
        	    }
        	    String namePassive = "";
        	    String balancePassive = "";
        	    if (i < passive.size()) {
    	    	        namePassive = passive.get(i).getName();
    	    	        balancePassive = FinanceHelper.formatAmount(passive.get(i).getBalance());
    	    	        totalPassive += passive.get(i).getBalance();
    	        }
        	    Object[] names = { nameActive, balanceActive, namePassive, balancePassive };
        	    rows[i] = names;
        }
        Object[] emptyRow = { "", "", "", "" };
        Object[] totalsRow = { "Total", FinanceHelper.formatAmount(totalActive), "Total", FinanceHelper.formatAmount(totalPassive) };
        rows[maxLength] = emptyRow;
        rows[maxLength+1] = totalsRow;
        return rows;
    }
}
