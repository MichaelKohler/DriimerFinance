package driimerfinance.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.models.User;

public class DriimerDBHelperTest {
	
	private Connection dbConnection = null;
	private DriimerDBHelper helper = null;

	@Before
	public void setup() throws SQLException
	{
	   this.dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:testcase;shutdown=true", "sa", null);
	   helper = new DriimerDBHelper(this.dbConnection);
	   Statement stmt = this.dbConnection.createStatement();
	   stmt.execute("create table user (idUser integer, Name varchar(250), Vorname varchar(250), Password varchar(250), username varchar(45))");
	   stmt.execute("create table mandanten (idMandanten integer, Name varchar(250), DBSchema varchar(250))");
	   this.dbConnection.commit();
	}

	@After
	public void tearDown() throws Exception
	{
	   this.dbConnection.close();
	}
	
	@Test
	public void testAddUserAndGetByXXX() {
		int expectedId = 0;
		String expectedName = "Testuser";
		String expectedUsername = "testuser";
		String expectedVorname = "VornameUser";
		String expectedPassword = "testpassword";
		User user = new User();
		user.setId(expectedId);
		user.setName(expectedName);
		user.setUsername(expectedUsername);
		user.setVorname(expectedVorname);
		user.setPassword(expectedPassword);
		helper.addUser(user);
		User outputByName = helper.getUserByName(expectedUsername);
		assertTrue(outputByName.getId() == expectedId);
		assertTrue(outputByName.getName().equals(expectedName));
		assertTrue(outputByName.getUsername().equals(expectedUsername));
		assertTrue(outputByName.getVorname().equals(expectedVorname));
		assertTrue(outputByName.getPassword().equals(expectedPassword));
		/*User outputById = helper.getUserByName(expectedUsername);
		assertTrue(outputById.equals(outputByName));
		String password = helper.getUserPasswordById(expectedId);
		assertTrue(password.equals(expectedPassword));*/
		List<User> outputAll = helper.getAllUsers();
		assertTrue(outputAll.size() == 1);
	}

}
