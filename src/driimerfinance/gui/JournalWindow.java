package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.GUIHelper;
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
	JournalWindow parent = this;
	
	
	
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
        addButtons();
        this.frame.setSize(650, 500);
		GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
    }
    
    
	/**
	 * Adds the content.
	 */
	private void addTable() {
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		String[] headers = { "ID", "Datum", "Soll-Konto", "Haben-Konto", "Buchungssatz", "Betrag", "Beleg-Nr" };
		Object[][] data = {  };
		transactionTable = new JTable(new DefaultTableModel(data, headers));
		transactionTable.setPreferredScrollableViewportSize(new Dimension(640, 490));
		transactionTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(transactionTable);
		tablePanel.add(scrollPane);
		
		List<Transaction> transactions = db.getAllTransactions();
		for ( Transaction transaction : transactions) {
			DefaultTableModel model = (DefaultTableModel) (transactionTable.getModel());
			Object[] newRow = { transaction.getId().toString(), transaction.getStringDate(), transaction.getFk_SollKonto(), transaction.getFk_HabenKonto(), transaction.getBezeichnung(), transaction.getBetrag(), transaction.getBelegNr() };
			model.addRow(newRow);
		}
			
		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}
	
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		
		JButton editButton = new JButton("Buchung bearbeiten");
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = 0;
				selRow = transactionTable.getSelectedRow();
				//if there is a row selected
				if ( selRow != -1 ) {
					DefaultTableModel model = (DefaultTableModel)transactionTable.getModel();
					//get transaction data from table model
					int transactionId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
					String date = model.getValueAt(selRow, 1).toString();
					int fk_fromAccount = Integer.parseInt(model.getValueAt(selRow, 2).toString());
					int fk_toAccount = Integer.parseInt(model.getValueAt(selRow, 3).toString());
					String description = model.getValueAt(selRow, 4).toString();
					double amount = Double.parseDouble(model.getValueAt(selRow, 5).toString());
					int receiptNumber = Integer.parseInt(model.getValueAt(selRow, 6).toString());
					new EditTransactionWindow(parent);
				}
			}
		});
		
		JButton deleteButton = new JButton("Buchung löschen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = 0;
				selRow = transactionTable.getSelectedRow();
				//if there is a row selected
				if ( selRow != -1 ) {
					int eingabe = JOptionPane.showConfirmDialog(null,
	                        "Sind Sie sicher, Buchung wird unwiderruflich gel\u00f6scht?",
	                        "Best\u00e4tigung",
	                        JOptionPane.YES_NO_OPTION);
					if (eingabe == 0) {
						DefaultTableModel model = (DefaultTableModel)transactionTable.getModel();
						//get transactiontid from table model
						int transactionId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
						//get the transaction from database and delete it. (in database as well as in the table)
						db.deleteTransactionById(transactionId);
						model.removeRow(selRow);	
					}
				}
					
			}
		});
		
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// we don't need to save this since an account plan is only a
				// virtual entity
				frame.dispose();
			}
		});
		
		buttonPanel.add(editButton, BorderLayout.WEST);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(cancelButton);
	}

	public void refreshTable() {
		frame.repaint();		
	}
	
//	public void addAccountToTable(Account acc) {
//		DefaultTableModel model = (DefaultTableModel) (accountTable.getModel());
//		Object[] newRow = { acc.getId().toString(), acc.getName(),
//				acc.getFk_AccountType() };
//		model.addRow(newRow);
//		frame.repaint();
//	}

}
