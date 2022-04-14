package com.softicar.platform.db.core.test;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.dbms.h2.DbH2Database;
import org.junit.Test;

public class DbTestDatabaseTest extends AbstractTest {

	private static final String SCHEMA_NAME = "foo";

	@Test
	public void testDatabaseIsPermanent() {

		DbH2Database database = new DbTestDatabase();
		database.createSchema(SCHEMA_NAME);

		DbConnections.closeAll();

		assertTrue(database.getAllSchemas().contains(SCHEMA_NAME));
	}
}
