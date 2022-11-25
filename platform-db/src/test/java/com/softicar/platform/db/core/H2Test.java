package com.softicar.platform.db.core;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.database.DbDatabases;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.List;
import org.junit.Test;

public class H2Test extends AbstractTest {

	private final List<String> setupStatements;

	public H2Test() {

		this.setupStatements = List
			.of(//
				"CREATE SCHEMA test",
				"CREATE TABLE test.test (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR)",
				"INSERT INTO test.test (name) VALUES ('a'), ('b'), ('c')");
	}

	@Test
	public void test() {

		try (IDbConnection connection = DbConnections.getConnection(DbDatabases.H2_MEMORY_UNSHARED)) {
			setupStatements.stream().map(DbStatement::new).forEach(connection::executeUncached);

			try (DbResultSet resultSet = connection.executeQuery(new DbStatement("SELECT id, name FROM test.test ORDER BY id"))) {
				assertTrue(resultSet.next());
				assertEquals(1, resultSet.getInt("id"));
				assertEquals("a", resultSet.getString("name"));

				assertTrue(resultSet.next());
				assertEquals(2, resultSet.getInt("id"));
				assertEquals("b", resultSet.getString("name"));

				assertTrue(resultSet.next());
				assertEquals(3, resultSet.getInt("id"));
				assertEquals("c", resultSet.getString("name"));

				assertFalse(resultSet.next());
			}
		}
	}

	@Test
	public void testCaseInsensitivity() {

		try (IDbConnection connection = DbConnections.getConnection(DbDatabases.H2_MEMORY_UNSHARED)) {
			setupStatements.stream().map(DbStatement::new).forEach(connection::executeUncached);

			try (DbResultSet resultSet = connection.executeQuery(new DbStatement("SELECT id, name FROM test.test WHERE name = 'C'"))) {
				assertTrue(resultSet.next());
				assertEquals(3, resultSet.getInt("id"));
				assertEquals("c", resultSet.getString("name"));

				assertFalse(resultSet.next());
			}
		}
	}
}
