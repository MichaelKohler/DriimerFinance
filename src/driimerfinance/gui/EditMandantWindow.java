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

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;

/**
 * Add a new mandant form
 * 
 * (c) 2014 Driimer Finance
 */
public class EditMandantWindow {

	JFrame frame = new JFrame("DriimerFinance - Mandant editieren");
	JTable mandantTable = new JTable();
	JPanel tablePanel = new JPanel();
	DriimerDBHelper driimerdb = new DriimerDBHelper();

	/**
	 * Constructor
	 */
	public EditMandantWindow() {
		createGUI();
	}

	/**
     * Creates the GUI
     */
	private void createGUI() {
		addForm();
		addButtons();
		this.frame.setSize(420, 200);
		GUIHelper.centerFrame(this.frame);
		this.frame.setVisible(true);
	}

	/**
	 * Adds the content.
	 */
	private void addForm() {
		
		tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		String[] headers = { "Nummer", "Name" };
		Object[][] data = {  };
		mandantTable = new JTable(new DefaultTableModel(data, headers) {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		mandantTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
		mandantTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(mandantTable);
		tablePanel.add(scrollPane);
		
		
		
		List<Mandant> mandanten = driimerdb.getAllMantanten();
		for ( Mandant mandant : mandanten) {
			DefaultTableModel model = (DefaultTableModel) (mandantTable.getModel());
			Object[] newRow = { mandant.getId().toString(), mandant.getName() };
			model.addRow(newRow);
		}
			
		frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	}

	/**
	 * Adds the buttons on the bottom of the frame.
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton deleteButton = new JButton("Mandant l\u00f6schen");
		
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = 0;
				selRow = mandantTable.getSelectedRow();
				//if there is a row selected
				if ( selRow != -1 ) {
					DefaultTableModel model = (DefaultTableModel)mandantTable.getModel();
					//get mandantid from table model
					int mandantId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
					
					//get the mandant from database and delete it. (in database as well as in the table)
					driimerdb.deleteMandantById(mandantId);
					model.removeRow(selRow);
				}
			}
		});
		JButton editButton = new JButton("Mandant editieren");
		editButton.addActionListener(new ActionListener() {
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
		buttonPanel.add(deleteButton, BorderLayout.WEST);
		buttonPanel.add(editButton, BorderLayout.CENTER);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(cancelButton);
	}

}
