package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.ER;
import driimerfinance.models.Mandant;

/**
 * Erfolgsrechnung overview window
 * 
 * (c) 2014 Driimer Finance
*/
public class ERViewer {
	JFrame frame = new JFrame("DriimerFinance");
	ImageIcon icon = new ImageIcon("images/DF.png");
	
	/**
	 * Constructor
	 */
    public ERViewer() {
        createGUI();
    }
    
    /**
     * Creates the GUI.
     */
    private void createGUI() {
    	    this.frame.setSize(500, 400);
    	    GUIHelper.centerFrame(this.frame);
    	    this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		String[] headers = { "Aufwand", "Betrag", "Ertrag", "Betrag" };
		Object[][] data = prepareData();
		
		JTable accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable.setPreferredScrollableViewportSize(new Dimension(490, 390));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);
		this.frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
		addButtons();
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
    	    ER er = new ER();
        List<Account> spending = er.getSpendingPositions();
        List<Account> earning = er.getEarningPositions();
        
        int maxLength = spending.size() >= earning.size() ? spending.size() : earning.size();
        int columns = 4;
        Object[][] rows = new Object[maxLength+2][columns];
        double totalSpending = 0;
        double totalEarning = 0;
        for (int i = 0; i < maxLength; i++) {
        	    String nameSpending = "";
        	    String balanceSpending = "";
        	    if (i < spending.size()) {
        	    	    nameSpending = spending.get(i).getName();
        	    	    balanceSpending = FinanceHelper.formatAmount(spending.get(i).getBalance());
        	    	    totalSpending += spending.get(i).getBalance();
        	    }
        	    String nameEarning = "";
        	    String balanceEarning = "";
        	    if (i < earning.size()) {
        	    	    nameEarning = earning.get(i).getName();
        	    	    balanceEarning = FinanceHelper.formatAmount(earning.get(i).getBalance());
        	    	    totalEarning += earning.get(i).getBalance();
    	        }
        	    Object[] names = { nameSpending, balanceSpending, nameEarning, balanceEarning };
        	    rows[i] = names;
        }
        Object[] emptyRow = { "", "", "", "" };
        Object[] totalsRow = { "Total", FinanceHelper.formatAmount(totalSpending), "Total", FinanceHelper.formatAmount(totalEarning) };
        rows[maxLength] = emptyRow;
        rows[maxLength+1] = totalsRow;
        return rows;
    }
}
