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
import driimerfinance.database.DriimerDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;

/**
 * Add a new mandant form
 * 
 * (c) 2014 Driimer Finance
 */
public class EditMandantWindow {

	final JFrame frame = new JFrame("DriimerFinance - Mandant editieren");
	ImageIcon icon = new ImageIcon("images/DF.png");
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
		this.frame.setIconImage(icon.getImage());
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

			// make table non-editable
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		});
		mandantTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
		mandantTable.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(mandantTable);
		tablePanel.add(scrollPane);
		
		// Add all mandants from the DB to the list
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
		deleteButton.addActionListener(new DeleteMandantAction());
		JButton editButton = new JButton("Mandant editieren");
		editButton.addActionListener(new UpdateMandantAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(deleteButton, BorderLayout.WEST);
		buttonPanel.add(editButton, BorderLayout.CENTER);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(cancelButton);
	}
	
	/**
	 * ActionListener to delete a mandant
	 */
	public class DeleteMandantAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selRow = mandantTable.getSelectedRow();
			//if there is a row selected
			if ( selRow != -1 ) {
				// ask the user for confirmation
				Object[] options = {"Ja", "Nein"};
				int eingabe = JOptionPane.showOptionDialog(
								null,
								"Sind Sie sicher, Mandant und ALLE seine Daten (Buchungen, Konten, etc.) werden unwiderruflich gel\u00f6scht?",
								"Best\u00e4tigung",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE,
							    null,
							    options,
							    options[1]);
				if (eingabe == 0) {
					DefaultTableModel model = (DefaultTableModel)mandantTable.getModel();
					//get mandantid from table model
					int mandantId = Integer.parseInt(model.getValueAt(selRow, 0).toString());
					
					//get the mandant from database and delete it. (in database as well as in the table)
					driimerdb.deleteMandantById(mandantId);
					model.removeRow(selRow);
					MainWindowSingleton.getMainWindowInstance().reload();
					frame.toFront();
				}
			}
		}
	}
	
	/**
	 * ActionListener to update a mandant
	 */
	public class UpdateMandantAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selRow = mandantTable.getSelectedRow();
			//if there is a row selected
			if ( selRow != -1 ) {
				//Ask user for new Name
				String name = JOptionPane.showInputDialog("Neuer Name:");
				
				if (name != null) {
					DefaultTableModel model = (DefaultTableModel) mandantTable
							.getModel();
					//get mandantid from table model
					int mandantId = Integer.parseInt(model
							.getValueAt(selRow, 0).toString());
					//get the mandant from database and modify it. (in database as well as in the table)
					Mandant mandant = driimerdb.getMandantById(mandantId);
					mandant.setName(name);
					mandant.updateInDB();
					//GUI update
					mandantTable.setValueAt(name, selRow, 1);
					mandantTable.repaint();
					MainWindowSingleton.getMainWindowInstance().reload();
					frame.toFront();
				}
			}
		}
	}
}
