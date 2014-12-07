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
		System.out.println("Running action...");
		AnnualAccounts annualAccounts = new AnnualAccounts();
		annualAccounts.processEarnings();
		annualAccounts.createStatements();
		annualAccounts.clearEarningPositions();
		annualAccounts.clearSpendingPositions();
	}
}
