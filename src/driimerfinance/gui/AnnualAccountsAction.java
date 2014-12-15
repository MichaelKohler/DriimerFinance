package driimerfinance.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import driimerfinance.models.AnnualAccounts;

/**
 * Performs the annual accounts procedures.
 * 
 * (c) 2014 Driimer Finance
*/
public class AnnualAccountsAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent event) {
		AnnualAccounts annualAccounts = new AnnualAccounts();
		annualAccounts.processEarnings(); // calculate the win and book it
		annualAccounts.createStatements(); // print all the export in PDF
		annualAccounts.clearEarningPositions(); // reset all positions to 0 for the new year
		annualAccounts.clearSpendingPositions(); // reset all positions to 0 for the new year
	}
}
