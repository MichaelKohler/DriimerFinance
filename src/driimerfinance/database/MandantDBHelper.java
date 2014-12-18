package driimerfinance.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import driimerfinance.models.Account;
import driimerfinance.models.AccountType;
import driimerfinance.models.Transaction;

/**
 * Helpful methods to work with the mandant database
 */
public class MandantDBHelper {
	private Connection dbconnection;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private DBConnection db = null;

	/**
	 * Constructor
	 * 
	 * @param connection to use
	 */
	public MandantDBHelper(Connection connection) {
		this.dbconnection = connection;
	}
	
	/**
	 * Changes the connection to the desired mandant
	 */
	public MandantDBHelper() {
		Properties prop = new Properties();
		try {
			File propertiesFile = new File("bin/driimerfinance/database/database.properties");
			if (!propertiesFile.exists()) {
				propertiesFile = new File("../../driimerfinance/database/database.properties");
			}
			prop.load(new FileInputStream(propertiesFile.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// read the config from the properties file
		String host = prop.getProperty("host");
		String user = prop.getProperty("user");
		String databasename = prop.getProperty("databasename");
		System.out.println(databasename);
		String password = prop.getProperty("password");

		// instantiate the connection to the datbase with the given credentials
		db = new DBConnection();
		dbconnection = db.createConnection(host, databasename, user, password);
	}

	/**
	 * Initializes the connection.
	 * 
	 * @param host
	 *            to connect to
	 * @param databasename
	 *            to use
	 * @param user
	 *            login for this database
	 * @param password
	 *            of the database's user
	 */
	public MandantDBHelper(String host, String databasename, String user,
			String password) {
		db = new DBConnection();
		dbconnection = db.createConnection(host, databasename, user, password);
	}

	/**
	 * Getter: Returns the transactions of this mandant in the database
	 * 
	 * @return list of all transactions
	 */
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
				transaction.setBetrag(resultSet.getDouble("Betrag"));
				transaction.setBelegNr(resultSet.getInt("BelegNr"));
				transactions.add(transaction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return transactions;
	}

	/**
	 * Getter: Returns the transaction by id of this mandant in the database
	 * 
	 * @return transaction with the given id
	 */
	public Transaction getTransactionById(int transactionId) {
		Transaction transaction = new Transaction();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from buchung where idBuchung=?");
			preparedStatement.setInt(1, transactionId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				transaction.setId(resultSet.getInt("idBuchung"));
				transaction.setDate(resultSet.getDate("Datum"));
				transaction.setFk_SollKonto(resultSet.getInt("fk_SollKonto"));
				transaction.setFk_HabenKonto(resultSet.getInt("fk_HabenKonto"));
				transaction.setBezeichnung(resultSet.getString("bezeichnung"));
				transaction.setBetrag(resultSet.getDouble("Betrag"));
				transaction.setBelegNr(resultSet.getInt("BelegNr"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return transaction;
	}

	/**
	 * Adds a transaction to the database.
	 * 
	 * @param transaction
	 *            to add
	 * @return database id of the added transaction
	 */
	public int addTransaction(Transaction transaction) {
		int returnedKey = 0;
		try {
			preparedStatement = dbconnection
					.prepareStatement(
							"insert into buchung(Datum, fk_SollKonto, fk_HabenKonto, Bezeichnung, Betrag, BelegNr) values (?,?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setDate(1, transaction.getDate());
			preparedStatement.setInt(2, transaction.getFk_SollKonto());
			preparedStatement.setInt(3, transaction.getFk_HabenKonto());
			preparedStatement.setString(4, transaction.getBezeichnung());
			preparedStatement.setDouble(5, transaction.getBetrag());
			preparedStatement.setInt(6, transaction.getBelegNr());
			preparedStatement.execute();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				returnedKey = rs.getInt(1); // ID is the first value of the
											// result
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return returnedKey;
	}

	/**
	 * Updates a transaction in the database.
	 * 
	 * @param transaction
	 *            to update
	 */
	public void updateTransaction(Transaction transaction) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update buchung set Datum=?, fk_SollKonto=?, fk_HabenKonto=?, Bezeichnung=?, Betrag=?, `BelegNr`=? where idBuchung=?");
			preparedStatement.setDate(1, transaction.getDate());
			preparedStatement.setInt(2, transaction.getFk_SollKonto());
			preparedStatement.setInt(3, transaction.getFk_HabenKonto());
			preparedStatement.setString(4, transaction.getBezeichnung());
			preparedStatement.setDouble(5, transaction.getBetrag());
			preparedStatement.setInt(6, transaction.getBelegNr());
			preparedStatement.setInt(7, transaction.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Deletes a transaction from the database.
	 * 
	 * @param transaction
	 *            to delete
	 */
	public void deleteTransaction(Transaction transaction) {
		deleteTransactionById(transaction.getId());
	}

	/**
	 * Deletes a transaction by it's ID.
	 * 
	 * @param id
	 *            to be deleted from the database
	 */
	public void deleteTransactionById(int transactionId) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from buchung where idbuchung=?");
			preparedStatement.setInt(1, transactionId);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Getter: Returns the accounts of this mandant in the database
	 * 
	 * @return list of all accounts
	 */
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement
					.executeQuery("select * from konto order by Nummer ASC");
			while (resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getInt("idKonto"));
				account.setNumber(resultSet.getInt("Nummer"));
				account.setName(resultSet.getString("Name"));
				account.setFk_AccountType(resultSet.getInt("fk_KontoTyp"));
				account.setBalance(resultSet.getDouble("Guthaben"));
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

	public TreeMap getAllAccountsInMap() {
		TreeMap accountMap = new TreeMap<Integer, String>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from konto");
			while (resultSet.next()) {
				accountMap.put(resultSet.getInt("idKonto"), resultSet.getString("Name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return accountMap;
	}

	/**
	 * Getter: Returns the account with this id of this mandant in the database
	 * 
	 * @return account found or a new account
	 */
	public Account getAccountById(int accountId) {
		Account account = new Account();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from konto where idKonto=?");
			preparedStatement.setInt(1, accountId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				account.setId(resultSet.getInt("idKonto"));
				account.setNumber(resultSet.getInt("Nummer"));
				account.setName(resultSet.getString("Name"));
				account.setFk_AccountType(resultSet.getInt("fk_KontoTyp"));
				account.setBalance(resultSet.getDouble("Guthaben"));
				account.setCapitalAccount(resultSet.getBoolean("Kapitalkonto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return account;
	}

	/**
	 * Getter: Returns the account with this name of this mandant in the
	 * database
	 * 
	 * @return account found or a new account
	 */
	public Account getAccountByName(String accountName) {
		Account account = null;
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from konto where Name=?");
			preparedStatement.setString(1, accountName);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getInt("idKonto"));
				account.setNumber(resultSet.getInt("Nummer"));
				account.setName(resultSet.getString("Name"));
				account.setFk_AccountType(resultSet.getInt("fk_KontoTyp"));
				account.setBalance(resultSet.getDouble("Guthaben"));
				account.setCapitalAccount(resultSet.getBoolean("Kapitalkonto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return account;
	}

	/**
	 * Getter: Returns the capital account
	 * 
	 * @return account found
	 */
	public Account getCapitalAccount() {
		Account account = null;
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from konto where Kapitalkonto=?");
			preparedStatement.setInt(1, 1); // 1 == Kapitalaccount

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				account = new Account();
				account.setId(resultSet.getInt("idKonto"));
				account.setNumber(resultSet.getInt("Nummer"));
				account.setName(resultSet.getString("Name"));
				account.setFk_AccountType(resultSet.getInt("fk_KontoTyp"));
				account.setBalance(resultSet.getDouble("Guthaben"));
				account.setCapitalAccount(resultSet.getBoolean("Kapitalkonto"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return account;
	}

	/**
	 * Adds an account to this mandant's database.
	 * 
	 * @param account to add
	 */
	public void addAccount(Account account) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into konto(Nummer, Name, fk_KontoTyp, Guthaben, Kapitalkonto) values (?,?,?,?,?)");
			preparedStatement.setInt(1, account.getNumber());
			preparedStatement.setString(2, account.getName());
			preparedStatement.setInt(3, account.getFk_AccountType());
			preparedStatement.setDouble(4, account.getBalance());
			preparedStatement.setBoolean(5, account.getCapitalAccount());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Updates an account in the database.
	 * 
	 * @param account to update
	 */
	public void updateAccount(Account account) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update konto set Nummer=?, Name=?, fk_KontoTyp=?, Guthaben=?, Kapitalkonto=? where idKonto=?");
			preparedStatement.setInt(1, account.getNumber());
			preparedStatement.setString(2, account.getName());
			preparedStatement.setInt(3, account.getFk_AccountType());
			preparedStatement.setDouble(4, account.getBalance());
			preparedStatement.setBoolean(5, account.getCapitalAccount());
			preparedStatement.setInt(6, account.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Deletes an account from this mandant's database.
	 * 
	 * @param account to delete
	 */
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

	/**
	 * Deletes an account by ID in the database
	 * 
	 * @param accountId to delete
	 * @return true if the deletion was successful, false otherwise
	 */
	public boolean deleteAccountById(int accountId) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from konto where idkonto=?");
			preparedStatement.setInt(1, accountId);
			preparedStatement.execute();
		} catch (SQLException e) {
			// If the account still has dependent transaction, inform the user and abort
			if (e.getSQLState().startsWith("23")) {
				JOptionPane.showMessageDialog(null,
								"Konto kann nicht gel\u00f6scht werden, da es von Buchungen noch verwendet wird",
								"Fehler", JOptionPane.ERROR_MESSAGE);
			}
			e.printStackTrace();
			return false;
		} finally {
			close();
		}
		return true;
	}

	/**
	 * Getter: Returns the account types of this mandant in the database
	 * 
	 * @return list of all account types
	 */
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

	/**
	 * Getter: Returns the account type by id of this mandant in the database
	 * 
	 * @return transaction type by id
	 */
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

	/**
	 * Getter: Returns account type id by name
	 */
	public int getAccountTypeIdByName(String accountTypeName) {
		int accountTypeId = 0;
		try {
			preparedStatement = dbconnection
					.prepareStatement("select idKontotyp from kontotyp where Name=?");
			preparedStatement.setString(1, accountTypeName);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				accountTypeId = resultSet.getInt("idKontotyp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return accountTypeId;
	}

	/**
	 * Adds an accounttype to this mandant's database.
	 * 
	 * @param accounttype to add
	 */
	public void addAccountType(AccountType accounttype) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into kontotyp(idKontotyp, Name) values (?,?)");
			preparedStatement.setInt(1, accounttype.getId());
			preparedStatement.setString(2, accounttype.getName());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Updates an Account Type in the database.
	 * 
	 * @param accounttype to update
	 */
	public void updateAccountType(AccountType accounttype) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update accounttype set name=? where idKonto=?");
			preparedStatement.setString(1, accounttype.getName());
			preparedStatement.setInt(2, accounttype.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Deletes an account type from this mandant's database.
	 * 
	 * @param accounttype to delete
	 */
	public void deleteAccountType(AccountType accounttype) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from kontotyp where idKontotyp=?");
			preparedStatement.setInt(1, accounttype.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Closes the database connection.
	 */
	public void closeConnection() {
		close();
		db.close();
	}

	/**
	 * Closes database related connections.
	 */
	private void close() {
		if (statement != null) {
			DBUtil.close(statement);
		}
		if (resultSet != null) {
			DBUtil.close(resultSet);
		}
	}
}
