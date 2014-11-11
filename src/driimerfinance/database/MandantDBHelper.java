package driimerfinance.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import driimerfinance.models.Account;
import driimerfinance.models.AccountType;
import driimerfinance.models.Transaction;


public class MandantDBHelper {
	private Connection dbconnection;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private DBConnection db = null;

	public MandantDBHelper(String host, String databasename,
			String user, String password) {
		db = new DBConnection();
		dbconnection = db.getConnection(host, databasename, user, password);
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from buchung");
			while (resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(resultSet.getInt("idBuchung"));
				transaction.setDate(resultSet.getDate("Datum"));
				transaction.setFk_SollKonto(resultSet.getInt("fk_SollKonto"));
				transaction.setFk_HabenKonto(resultSet.getInt("fk_HabenKonto"));
				transaction.setBezeichnung(resultSet.getString("Bezeichnung"));
				transaction.setBetrag(resultSet.getInt("Betrag"));
				transaction.setBelegNr(resultSet.getInt("Beleg-Nr"));
				transactions.add(transaction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return transactions;
	}

		
	public Transaction getTransactionById(int transactionId) {
		Transaction transaction = new Transaction();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from buchung where idBuchung=?");
			preparedStatement.setInt(1, transactionId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				transaction.setId(resultSet.getInt("idMandanten"));
				transaction.setDate(resultSet.getDate("Datum"));
				transaction.setFk_SollKonto(resultSet.getInt("fk_SollKonto"));
				transaction.setFk_HabenKonto(resultSet.getInt("fk_HabenKonto"));
				transaction.setBezeichnung(resultSet.getString("bezeichnung"));
				transaction.setBetrag(resultSet.getInt("Betrag"));
				transaction.setBelegNr(resultSet.getInt("Beleg-Nr"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return transaction;
	}

	public void addTransaction(Transaction transaction) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into buchung(Datum, fk_SollKonto, fk_HabenKonto, Bezeichnung, Betrag, Beleg-Nr) values (?,?,?,?,?,?)");
			preparedStatement.setDate(1, transaction.getDate());
			preparedStatement.setInt(2, transaction.getFk_SollKonto());
			preparedStatement.setInt(3, transaction.getFk_HabenKonto());
			preparedStatement.setString(4,  transaction.getBezeichnung());
			preparedStatement.setInt(5, transaction.getBetrag());
			preparedStatement.setInt(6, transaction.getBelegNr());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void updateTransaction(Transaction transaction) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update buchung set Datum=?, fk_SollKonto=?, fk_HabenKonto=?, Bezeichnung=?, Betrag=?, Beleg-Nr=? where idBuchung=?");
			preparedStatement.setDate(1, transaction.getDate());
			preparedStatement.setInt(2, transaction.getFk_SollKonto());
			preparedStatement.setInt(3, transaction.getFk_HabenKonto());
			preparedStatement.setString(4, transaction.getBezeichnung());
			preparedStatement.setInt(5, transaction.getBetrag());
			preparedStatement.setInt(6, transaction.getBelegNr());
			preparedStatement.setInt(7, transaction.getId());
			preparedStatement.execute();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteTransaction(Transaction transaction) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from buchung where idBuchung=?");
			preparedStatement.setInt(1, transaction.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from konto");
			while (resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getInt("idKonto"));
				account.setName(resultSet.getString("Name"));
				account.setFk_AccountType(resultSet.getInt("fk_KontoTyp"));
				account.setBalance(resultSet.getInt("Guthaben"));
				account.setCapitalAccount(resultSet.getBoolean("Kapitalkonto"));
				accounts.add(account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return accounts;
	}
	
	
	public Account getAccountById(int accountId) {
		Account account= new Account();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from konto where idKonto=?");
			preparedStatement.setInt(1, accountId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				account.setId(resultSet.getInt("idKonto"));
				account.setName(resultSet.getString("Name"));
				account.setFk_AccountType(resultSet.getInt("fk_KontoTyp"));
				account.setBalance(resultSet.getInt("Guthaben"));
				account.setCapitalAccount(resultSet.getBoolean("Kapitalkonto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return account;
	}
	
	public void addAccount(Account account) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into konto(Name, fk_KontoTyp, Guthaben, Kapitalkonto) values (?,?,?,?)");
			preparedStatement.setString(1, account.getName());
			preparedStatement.setInt(2, account.getFk_AccountType());
			preparedStatement.setInt(3, account.getBalance());
			preparedStatement.setBoolean(4,  account.getCapitalAccount());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void updateAccount(Account account) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update konto set Name=?, fk_KontoTyp=?, Guthaben=?, Kapitalkonto=? where idKonto=?");
			preparedStatement.setString(1, account.getName());
			preparedStatement.setInt(2, account.getFk_AccountType());
			preparedStatement.setInt(3, account.getBalance());
			preparedStatement.setBoolean(4,  account.getCapitalAccount());
			preparedStatement.setInt(5, account.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void deleteAccount(Account account) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from konto where idKonto=?");
			preparedStatement.setInt(1, account.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public List<AccountType> getAllAccountTypes() {
		List<AccountType> accounttypes = new ArrayList<AccountType>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from kontotyp");
			while (resultSet.next()) {
				AccountType accounttype = new AccountType();
				accounttype.setId(resultSet.getInt("idKontotyp"));
				accounttype.setName(resultSet.getString("Name"));
				accounttypes.add(accounttype);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return accounttypes;
	}
	
	
	public AccountType getAccountTypeById(int accounttypeId) {
		AccountType accounttype = new AccountType();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from kontotyp where idKontotyp=?");
			preparedStatement.setInt(1, accounttypeId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				accounttype.setId(resultSet.getInt("idKontotyp"));
				accounttype.setName(resultSet.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return accounttype;
	}
	
	// you need to close all three (connection, statement, resultset) to make
	// sure
	public void closeConnection() {
		close();
		db.close();
	}

	private void close() {
		DBUtil.close(statement);
		DBUtil.close(resultSet);
	}

}
