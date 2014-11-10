package driimerfinance.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import driimerfinance.models.Account;
import driimerfinance.models.Mandant;
import driimerfinance.models.Transaction;
import driimerfinance.models.User;

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
			resultSet = statement.executeQuery("select * from konto");
			while (resultSet.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(resultSet.getInt("idBuchung"));
				transaction.setDate(resultSet.getDate("Datum"));
				transaction.setSollKonto(resultSet.getString("fk_SollKonto"));
				transaction.setHabenKonto(resultSet.getString("fk_HabenKonto"));
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

	public User getUserById(int userId) {
		User user = new User();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from user where idUser=?");
			preparedStatement.setInt(1, userId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				user.setId(resultSet.getInt("idUser"));
				user.setName(resultSet.getString("Name"));
				user.setVorname(resultSet.getString("Vorname"));
				user.setPassword(resultSet.getString("Password"));
				user.setUsername(resultSet.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user;
	}

	public void addUser(User user) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into user(Name, Vorname, Password, username) values (?,?,?,?)");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getVorname());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteUser(User user) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from users where idUser=?");
			preparedStatement.setInt(1, user.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void updateUser(User user) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update user set Name=?, Vorname=?, Password=?, username=? where idUser=?");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getVorname());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public List<Mandant> getAllMantanten() {
		List<Mandant> mandanten = new ArrayList<Mandant>();
		try {
			statement = dbconnection.createStatement();
			// resultSet gets the result of the SQL query
			resultSet = statement.executeQuery("select * from mandanten");
			while (resultSet.next()) {
				Mandant mandant = new Mandant();
				mandant.setID(resultSet.getInt("idMandanten"));
				mandant.setName(resultSet.getString("Name"));
				mandant.setDBSchema(resultSet.getString("DBSchema"));
				mandanten.add(mandant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mandanten;
	}

	public Mandant getMandantById(int mandantId) {
		Mandant mandant = new Mandant();
		try {
			preparedStatement = dbconnection
					.prepareStatement("select * from mandanten where idMandanten=?");
			preparedStatement.setInt(1, mandantId);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				mandant.setID(resultSet.getInt("idMandanten"));
				mandant.setName(resultSet.getString("Name"));
				mandant.setDBSchema(resultSet.getString("DBSchema"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return mandant;
	}

	public void addMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("insert into mandanten(Name, DBSchema) values (?,?)");
			preparedStatement.setString(1, mandant.getName());
			preparedStatement.setString(2, mandant.getDBSchema());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("delete from mandanten where idmandanten=?");
			preparedStatement.setInt(1, mandant.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void updateMandant(Mandant mandant) {
		try {
			preparedStatement = dbconnection
					.prepareStatement("update mandanten set Name=?, DBSchema=? where idMandanten=?");
			preparedStatement.setString(1, mandant.getName());
			preparedStatement.setString(2, mandant.getDBSchema());
			preparedStatement.setInt(3, mandant.getId());
			preparedStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
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
