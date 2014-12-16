package driimerfinance.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driimerfinance.database.MandantDBHelper;
import driimerfinance.models.Account;
import driimerfinance.models.Transaction;

public class MandantDBHelperTest {
	
	private Connection dbConnection = null;
	private MandantDBHelper helper = null;

	@Before
	public void setup() throws SQLException
	{
	   this.dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testcase;shutdown=true", "sa", null);
	   helper = new MandantDBHelper(this.dbConnection);
	   Statement stmt = this.dbConnection.createStatement();
	   stmt.execute("create table Konto (idKonto integer, Nummer integer, Name varchar(250), fk_KontoTyp integer, Guthaben decimal(10,2), Kapitalkonto tinyint)");
	   stmt.execute("create table Buchung (idBuchung integer, Datum datetime, fk_SollKonto integer, fk_HabenKonto integer, Bezeichnung varchar(250), Betrag decimal(10,2), BelegNr varchar(250))");
	   this.dbConnection.commit();
	}

	@After
	public void tearDown() throws Exception
	{
	   this.dbConnection.close();
	}
	
	@Test
	public void testAddAccountAndGetByXXX() {
		int expectedId = 0;
		int expectedNumber = 1000;
		String expectedName = "testkonto";
		int expectedKontoTyp = 1;
		double expectedAmount = 10.50;
	    boolean expectedCapitalaccount = true;
	    Account account = new Account();
	    account.setId(expectedId);
	    account.setNumber(expectedNumber);
	    account.setName(expectedName);
	    account.setFk_AccountType(expectedKontoTyp);
	    account.setBalance(expectedAmount);
	    account.setCapitalAccount(expectedCapitalaccount);
	    helper.addAccount(account);
	    List<Account> outputAll = helper.getAllAccounts();
	    assertTrue(outputAll.size() == 1);
	    Account outputByName = helper.getAccountByName(expectedName);
	    assertTrue(outputByName.getBalance() == expectedAmount);
	    assertTrue(outputByName.getCapitalAccount());
	    assertTrue(outputByName.getFk_AccountType() == 1);
	    assertTrue(outputByName.getId() == expectedId);
	    assertTrue(outputByName.getName().equals(expectedName));
	    assertTrue(outputByName.getNumber() == expectedNumber);
	    /*Account outputById = helper.getAccountById(expectedId);
	    assertTrue(outputById.equals(outputByName));*/
	    Account outputCapitalAccount = helper.getCapitalAccount();
	    assertTrue(outputCapitalAccount.getBalance() == expectedAmount);
	    assertTrue(outputCapitalAccount.getCapitalAccount());
	    assertTrue(outputCapitalAccount.getFk_AccountType() == 1);
	    assertTrue(outputCapitalAccount.getId() == expectedId);
	    assertTrue(outputCapitalAccount.getName().equals(expectedName));
	    assertTrue(outputCapitalAccount.getNumber() == expectedNumber);
	}
	
	@Test
	public void testAddTransactionAndGetByXXX() {
		int expectedId = 0;
		Date expectedDate = new Date(1, 4, 2014);
		int expectedSoll = 1;
		int expectedHaben = 2;
		double expectedAmount = 23.50;
		int expectedReceipt = 5;
		String expectedDescription = "testtransaction";
		Transaction trans = new Transaction();
		trans.setId(expectedId);
		trans.setDate(expectedDate);
		trans.setAmount(expectedAmount);
		trans.setFk_SollKonto(expectedSoll);
		trans.setFk_HabenKonto(expectedHaben);
		trans.setBelegNr(expectedReceipt);
		trans.setBetrag(expectedAmount);
		trans.setBezeichnung(expectedDescription);
		helper.addTransaction(trans);
		List<Transaction> outputAll = helper.getAllTransactions();
		assertTrue(outputAll.size() == 1);
		assertTrue(outputAll.get(0).getBelegNr() == expectedReceipt);
		assertTrue(outputAll.get(0).getBezeichnung().equals(expectedDescription));
		assertTrue(outputAll.get(0).getDate().equals(expectedDate));
		assertTrue(outputAll.get(0).getFk_HabenKonto() == expectedHaben);
		assertTrue(outputAll.get(0).getFk_SollKonto() == expectedSoll);
		assertTrue(outputAll.get(0).getId() == expectedId);
		assertTrue(outputAll.get(0).getBetrag() == expectedAmount);
		/*Transaction outputById = helper.getTransactionById(expectedId);
		assertTrue(outputById.getBelegNr() == expectedReceipt);
		assertTrue(outputById.getBetrag() == expectedAmount);
		assertTrue(outputById.getBezeichnung().equals(expectedDescription));
		assertTrue(outputById.getDate().equals(expectedDate));
		assertTrue(outputById.getFk_HabenKonto() == expectedHaben);
		assertTrue(outputById.getFk_SollKonto() == expectedSoll);
		assertTrue(outputById.getId() == expectedId);*/
	}

}
