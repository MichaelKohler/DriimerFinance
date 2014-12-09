package driimerfinance.tests;

import java.sql.SQLException;

import com.mockrunner.jdbc.BasicJDBCTestCaseAdapter;
import com.mockrunner.jdbc.StatementResultSetHandler;
import com.mockrunner.mock.jdbc.MockConnection;
import com.mockrunner.mock.jdbc.MockResultSet;

import driimerfinance.database.DriimerDBHelper;
import driimerfinance.models.User;

public class DriimerDBHelperTest extends BasicJDBCTestCaseAdapter {

	public void testGetAllUsers() throws SQLException {
		MockConnection connection = getJDBCMockObjectFactory()
				.getMockConnection();
		StatementResultSetHandler statementHandler = connection
				.getStatementResultSetHandler();
		MockResultSet result = statementHandler.createResultSet();
		statementHandler.prepareGlobalResultSet(result);
		
		DriimerDBHelper helper = new DriimerDBHelper();
		helper.getAllUsers();
		verifySQLStatementExecuted("select * from user");
        verifyNotRolledBack();
        verifyAllResultSetsClosed();
        verifyAllStatementsClosed();
	}
	
	/*public void testGetUserById() throws SQLException {
		MockConnection connection = getJDBCMockObjectFactory()
				.getMockConnection();
		StatementResultSetHandler statementHandler = connection
				.getStatementResultSetHandler();
		MockResultSet result = statementHandler.createResultSet();
		result.addColumn("idUser", new Integer[]{1});
		result.addColumn("Name", new String[]{"Foo"});
		result.addColumn("Vorname", new String[]{"Foo"});
		result.addColumn("Password", new String[]{"Foo"});
		result.addColumn("username", new String[]{"Foo"});
		statementHandler.prepareGlobalResultSet(result);
		
		DriimerDBHelper helper = new DriimerDBHelper();
		helper.getUserById(1);
		verifySQLStatementExecuted("select * from user where idUser");
        verifyNotRolledBack();
        verifyAllResultSetsClosed();
        verifyAllStatementsClosed();
	}*/
}
