package driimerfinance.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.helpers.GUIHelper;
import driimerfinance.models.Mandant;
import driimerfinance.services.RawDataImporter;

/**
 * Add a new transaction with this window.
 * 
 * (c) 2014 Driimer Finance
 */
public class ImportWindow {

	JFrame frame = new JFrame("Import");
	ImageIcon icon = new ImageIcon("images/DF.png");
	private JTextField nameField;

	/**
	 * Constructor
	 */
	public ImportWindow() {
		createGUI();
	}

	/**
	 * Creates the GUI
	 */
	private void createGUI() {
		addForm();
		addButtons();
		this.frame.setSize(400, 300);
		GUIHelper.centerFrame(this.frame);
		this.frame.setIconImage(icon.getImage());
		this.frame.setVisible(true);
	}

	private void addForm() {
		JPanel formPanel = new JPanel();
		GridLayout layout = new GridLayout(1, 2);
		formPanel.setLayout(layout);
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel nameLabel = new JLabel("Mandant Name");
		this.nameField = new JTextField();
		this.nameField.setPreferredSize(new Dimension(150, 20));

		formPanel.add(nameLabel);
		formPanel.add(this.nameField);

		this.frame.getContentPane().add(formPanel, BorderLayout.CENTER);
	}

	/**
	 * Adds the buttons on the bottom of the frame.
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("Choose File");
		okButton.addActionListener(new SaveMandantAction());
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new FrameCloseAction(frame));
		buttonPanel.add(okButton, BorderLayout.WEST);
		buttonPanel.add(cancelButton, BorderLayout.EAST);
		this.frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.frame.getRootPane().setDefaultButton(okButton);
	}

	public class SaveMandantAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (nameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame,
						"Der Name muss ausgef\u00fcllt sein!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String path = "";
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Select Destination");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setFileFilter(new FileNameExtensionFilter("SQL Files",
					"SQL", "sql"));
			// disable the "All files" option.
			chooser.setAcceptAllFileFilterUsed(false);
			if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				path = chooser.getSelectedFile().getAbsolutePath();
				DriimerDBHelper helper = new DriimerDBHelper();
				String mandantName = nameField.getText();
				Mandant existingMandant = helper.getMandantByName(mandantName);
				Mandant newMandant = null;
				if (existingMandant == null) {
					System.out.println("Mandant existiert noch nicht. Anlegen...");
					newMandant = new Mandant();
					newMandant.setName(mandantName);
					newMandant.setDBSchema(nameField.getText());
					newMandant.createInDB();
					(new Thread(new RawDataImporter(path, newMandant))).start();
					MainWindowSingleton.getMainWindowInstance().reload();
					frame.dispose();
				} else {
					System.out
							.println("Mandant exisitert. Daten importieren...");
					System.out.println("path: " + path);
					(new Thread(new RawDataImporter(path, existingMandant)))
							.start();
					frame.dispose();
				}
			}
		}
	}

}
