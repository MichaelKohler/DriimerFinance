package driimerfinance.gui;

import java.awt.BorderLayout;
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
import driimerfinance.models.AnnualAccounts;
import driimerfinance.models.Balance;
import driimerfinance.services.PDFExporter;

/**
 * Shows the Balance in a two column view.
 * 
 * (c) 2014 Driimer Finance
 */
public class BalanceViewer {
	JFrame frame = new JFrame("DriimerFinance - Balance");
	ImageIcon icon = new ImageIcon("images/DF.png");
	private JTable accountTable = null;

	/**
	 * Constructor
	 */
	public BalanceViewer() {
		createGUI(true);
		addButtons();
	}

	/**
	 * Constructor
	 */
	public BalanceViewer(boolean show) {
		createGUI(show);
		addButtons();
	}

	/**
	 * Creates the GUI.
	 */
	private void createGUI(boolean show) {
		this.frame.setSize(500, 400);
		GUIHelper.centerFrame(this.frame);
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(show);

		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		String[] headers = { "Aktiven", "Betrag", "Passiven", "Betrag" };
		Object[][] data = prepareData();

		accountTable = new JTable(new DefaultTableModel(data, headers));
		accountTable
				.setPreferredScrollableViewportSize(new Dimension(490, 390));
		accountTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(accountTable);
		tablePanel.add(scrollPane);
		this.frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}

	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton PDFExportButton = new JButton("PDF Export");
		PDFExportButton.addActionListener(new PDFExportAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
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

		int maxLength = active.size() >= passive.size() ? active.size()
				: passive.size();
		int columns = 4;
		Object[][] rows = new Object[maxLength + 4][columns];
		double totalActive = 0;
		double totalPassive = 0;
		for (int i = 0; i < maxLength; i++) {
			String nameActive = "";
			String balanceActive = "";
			if (i < active.size()) {
				nameActive = active.get(i).getName();
				balanceActive = FinanceHelper.formatAmount(active.get(i)
						.getBalance());
				totalActive += active.get(i).getBalance();
			}
			String namePassive = "";
			String balancePassive = "";
			if (i < passive.size()) {
				namePassive = passive.get(i).getName();
				balancePassive = FinanceHelper.formatAmount(passive.get(i)
						.getBalance());
				totalPassive += passive.get(i).getBalance();
			}
			Object[] names = { nameActive, balanceActive, namePassive,
					balancePassive };
			rows[i] = names;
		}
		Object[] emptyRow = { "", "", "", "" };
		Object[] totalsRow = { "Total",
				FinanceHelper.formatAmount(totalActive), "Total",
				FinanceHelper.formatAmount(totalPassive) };
		double totalSpending = new AnnualAccounts().calculateTotalSpending();
        double totalEarning = new AnnualAccounts().calculateTotalEarning();
        rows[maxLength] = emptyRow;
        rows[maxLength + 1] = prepareWinRow(totalSpending, totalEarning);
		rows[maxLength + 2] = emptyRow;
		rows[maxLength + 3] = totalsRow;
		return rows;
	}
	
	/**
     * Calculates the win or loss and adds them to a tablerow
     * 
     * @param total spending
     * @param total earning
     * @return table row with the appropriate cells filled with earning or loss
     */
    public static Object[] prepareWinRow(double totalSpending, double totalEarning) {
    	    double winOrLoss = totalEarning - totalSpending;
    	    if (winOrLoss < 0) { // loss
    	    	    Object[] row = { "Verlust", FinanceHelper.formatAmount(Math.abs(winOrLoss)), "", "" };
    	    	    return row;
    	    } else { // loss
    	    	    Object[] row = { "", "", "Gewinn", FinanceHelper.formatAmount(Math.abs(winOrLoss)) };
    	    	    return row;
    	    }
    }

	public void exportPDF() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Destination");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "PDF",
				"pdf"));
		// disable the "All files" option.
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			PDFExporter pdf = new PDFExporter();
			String filePath = chooser.getSelectedFile().getAbsolutePath();
			if (!filePath.endsWith(".pdf")) {
				filePath = filePath + ".pdf";
			}
			System.out.println("Filepath: " + filePath);
			pdf.setOutputPath(filePath);
			try {
				System.out.println("creating pdf");
				pdf.createBalancePdf(accountTable);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("No Selection ");
		}
	}

	public class PDFExportAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			exportPDF();
		}
	}
}
