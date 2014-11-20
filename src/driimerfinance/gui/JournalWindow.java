package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;
import driimerfinance.models.Transaction;

/**
 * Journal overview window
 * 
 * (c) 2014 Driimer Finance
*/
public class JournalWindow extends OneColumnViewer {
	
	JFrame frame = new JFrame("Buchungsjournal");
	JTable transactionTable = new JTable();
	JPanel tablePanel = new JPanel();
	MandantDBHelper db = new MandantDBHelper();
	/**
	 * Constructor
	 */
    public JournalWindow() {
        createGUI();
    }
    
    /**
     * Creates the GUI
     */
    private void createGUI() {
        addTable();
        this.frame.setSize(650, 500);
		GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
    }
    
    
	/**
	 * Adds the content.
	 */
	private void addTable() {
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		String[] headers = { "Datum", "Soll-Konto", "Haben-Konto", "Buchungssatz", "Betrag", "Beleg-Nr" };
		Object[][] data = {  };
		transactionTable = new JTable(new DefaultTableModel(data, headers));
		transactionTable.setPreferredScrollableViewportSize(new Dimension(640, 490));
		transactionTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(transactionTable);
		tablePanel.add(scrollPane);
		
		List<Transaction> transactions = db.getAllTransactions();
		for ( Transaction transaction : transactions) {
			DefaultTableModel model = (DefaultTableModel) (transactionTable.getModel());
			Object[] newRow = { transaction.getId().toString(), transaction.getFk_SollKonto(), transaction.getFk_HabenKonto(), transaction.getBezeichnung(), transaction.getBetrag(), transaction.getBelegNr() };
			model.addRow(newRow);
		}
			
		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}

}
