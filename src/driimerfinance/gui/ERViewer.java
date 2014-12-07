package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;

import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.ER;
import driimerfinance.services.PDFExporter;


/**
 * Erfolgsrechnung overview window
 * 
 * (c) 2014 Driimer Finance
*/
public class ERViewer {
	JFrame frame = new JFrame("DriimerFinance");
	ImageIcon icon = new ImageIcon("images/DF.png");
	JTable accountTable = null;
	
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
		
		accountTable = new JTable(new DefaultTableModel(data, headers));
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

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Select Destination");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setFileFilter(new FileNameExtensionFilter("PDF Files",
						"PDF", "pdf"));
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					PDFExporter pdf = new PDFExporter();
					String filePath = chooser.getSelectedFile()
							.getAbsolutePath();
					if (!filePath.endsWith(".pdf")) {
						filePath = filePath + ".pdf";
					}
					System.out.println("Filepath: " + filePath);
					pdf.setOutputPath(filePath);
					try {
						System.out.println("creating pdf");
						pdf.createErPdf(accountTable);
					} catch (DocumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println("No Selection ");
				}

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
        int additionalLines = 4;
        Object[][] rows = new Object[maxLength+additionalLines][columns];
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
        rows[maxLength] = emptyRow;
        rows[maxLength+1] = prepareWinRow(totalSpending, totalEarning);
        rows[maxLength+2] = emptyRow;
        Object[] totalsRow = { "Total", FinanceHelper.formatAmount(totalSpending), "Total", FinanceHelper.formatAmount(totalEarning) };
        rows[maxLength+3] = totalsRow;
        return rows;
    }
    
    /**
     * Calculates the win or loss.
     * 
     * @param total spending
     * @param total earning
     * @return table row with the appropriate cells filled with earning or loss
     */
    private Object[] prepareWinRow(double totalSpending, double totalEarning) {
    	    double winOrLoss = totalEarning - totalSpending;
    	    if (winOrLoss >= 0) { // win or neutral
    	    	    Object[] row = { "Gewinn", FinanceHelper.formatAmount(winOrLoss), "", "" };
    	    	    return row;
    	    } else { // loss
    	    	    Object[] row = { "", "", "Verlust", FinanceHelper.formatAmount(Math.abs(winOrLoss)) };
    	    	    return row;
    	    }
    }
}
