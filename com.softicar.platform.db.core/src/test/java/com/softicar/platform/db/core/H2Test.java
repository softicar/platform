package com.softicar.platform.db.core;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.database.DbDatabases;
import com.softicar.platform.db.core.statement.DbStatement;
import org.junit.Assert;
import org.junit.Test;

public class H2Test extends Assert {

	@Test
	public void test() {

		try (IDbConnection connection = DbConnections.getConnection(DbDatabases.H2_MEMORY_UNSHARED)) {
			connection.executeUncached(new DbStatement("CREATE SCHEMA test"));
			connection.executeUncached(new DbStatement("CREATE TABLE test.test (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR)"));
			connection.executeUncached(new DbStatement("INSERT INTO test.test (name) VALUES ('a'), ('b'), ('c')"));

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
}
