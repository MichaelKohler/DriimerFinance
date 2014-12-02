package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.helpers.FinanceHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Transaction;
import driimerfinance.services.PDFExporter;

/**
 * Journal overview window
 * 
 * (c) 2014 Driimer Finance
 */
public class JournalWindow extends OneColumnViewer {

	JFrame frame = new JFrame("Buchungsjournal");
	ImageIcon icon = new ImageIcon("images/DF.png");
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
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
	}

	/**
	 * Adds the content.
	 */
	private void addTable() {
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		String[] headers = { "ID", "Datum", "Soll-Konto", "Haben-Konto",
				"Buchungssatz", "Betrag", "Beleg-Nr" };
		Object[][] data = {};
		transactionTable = new JTable(new DefaultTableModel(data, headers));
		transactionTable.setPreferredScrollableViewportSize(new Dimension(640,
				490));
		transactionTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(transactionTable);
		tablePanel.add(scrollPane);

		MandantDBHelper helper = new MandantDBHelper();
		List<Transaction> transactions = db.getAllTransactions();
		for (Transaction transaction : transactions) {
			DefaultTableModel model = (DefaultTableModel) (transactionTable
					.getModel());
			Account sollAccount = helper.getAccountById(transaction
					.getFk_SollKonto());
			Account habenAccount = helper.getAccountById(transaction
					.getFk_HabenKonto());
			Object[] newRow = { transaction.getId().toString(),
					transaction.getStringDate(), sollAccount.getName(),
					habenAccount.getName(), transaction.getBezeichnung(),
					FinanceHelper.formatAmount(transaction.getBetrag()),
					transaction.getBelegNr() };
			model.addRow(newRow);
		}

		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
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
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".pdf")) {
						filePath = filePath + ".pdf";
					}
					System.out.println("Filepath: " + filePath);
					pdf.setOutputPaht(filePath);
					try {
						System.out.println("creating pdf");
						pdf.createPdf(transactionTable);
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
		JButton editButton = new JButton("Buchung bearbeiten");
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = 0;
				selRow = transactionTable.getSelectedRow();
				// if there is a row selected
				if (selRow != -1) {
					MandantDBHelper helper = new MandantDBHelper();
					DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
					// get transaction data from table model
					int transactionId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
					String date = model.getValueAt(selRow, 1).toString();
					int fk_fromAccount = helper.getAccountByName(model.getValueAt(selRow, 2).toString()).getId();
					int fk_toAccount = helper.getAccountByName(model.getValueAt(selRow, 3).toString()).getId();
					String description = model.getValueAt(selRow, 4).toString();
					//double amount = Double.parseDouble(model.getValueAt(selRow, 5).toString());
					double amount = FinanceHelper.unformatAmount(model.getValueAt(selRow, 5).toString());
					int receiptNumber = Integer.parseInt(model.getValueAt(selRow, 6).toString());
					new EditTransactionWindow(parent, transactionId, date, fk_fromAccount, fk_toAccount, description, amount, receiptNumber);
					//new EditTransactionWindow(parent);
				}
			}
		});

		JButton deleteButton = new JButton("Buchung l\u00f6schen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = 0;
				selRow = transactionTable.getSelectedRow();
				// if there is a row selected
				if (selRow != -1) {
					Object[] options = {"Ja", "Nein"};
					int eingabe = JOptionPane.showOptionDialog(
									null,
									"Sind Sie sicher, Buchung wird unwiderruflich gel\u00f6scht?",
									"Best\u00e4tigung",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE,
								    null,
								    options,
								    options[1]);
					if (eingabe == 0) {
						DefaultTableModel model = (DefaultTableModel) transactionTable
								.getModel();
						// get transactiontid from table model
						int transactionId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
						// get the transaction from database and delete it. (in
						// database as well as in the table)
						db.deleteTransactionById(transactionId);
						model.removeRow(selRow);
					}
				}

			}
		});
	
		JButton closeButton = new JButton("Fenster Schliessen");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// we don't need to save this since an account plan is only a
				// virtual entity
				frame.dispose();
			}
		});

		buttonPanel.add(PDFExportButton, BorderLayout.WEST);
		buttonPanel.add(editButton, BorderLayout.WEST);
		buttonPanel.add(deleteButton, BorderLayout.CENTER);
		buttonPanel.add(closeButton, BorderLayout.EAST);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(closeButton);
	}

	public void refreshTable() {
		frame.repaint();
	}

	// public void addAccountToTable(Account acc) {
	// DefaultTableModel model = (DefaultTableModel) (accountTable.getModel());
	// Object[] newRow = { acc.getId().toString(), acc.getName(),
	// acc.getFk_AccountType() };
	// model.addRow(newRow);
	// frame.repaint();
	// }

}
