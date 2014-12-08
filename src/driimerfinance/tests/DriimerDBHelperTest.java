package driimerfinance.tests;

import java.sql.SQLException;

import com.mockrunner.jdbc.BasicJDBCTestCaseAdapter;
import com.mockrunner.jdbc.StatementResultSetHandler;
import com.mockrunner.mock.jdbc.MockConnection;
import com.mockrunner.mock.jdbc.MockResultSet;

import driimerfinance.database.DriimerDBHelper;

public class DriimerDBHelperTest extends BasicJDBCTestCaseAdapter {
	private void prepareEmptyResultSet() {
		MockConnection connection = getJDBCMockObjectFactory()
				.getMockConnection();
		StatementResultSetHandler statementHandler = connection
				.getStatementResultSetHandler();
		MockResultSet result = statementHandler.createResultSet();
		statementHandler.prepareGlobalResultSet(result);
	}

	public void testGetAllUsers() throws SQLException {
		prepareEmptyResultSet();
		DriimerDBHelper helper = new DriimerDBHelper();
		helper.getAllUsers();
		verifySQLStatementExecuted("select * from user");
        verifyNotRolledBack();
        verifyAllResultSetsClosed();
        verifyAllStatementsClosed();
	}
}
