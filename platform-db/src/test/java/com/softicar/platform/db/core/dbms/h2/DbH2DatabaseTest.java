package com.softicar.platform.db.core.dbms.h2;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.connection.DbConnections;
import java.security.SecureRandom;
import org.junit.Test;

public class DbH2DatabaseTest extends AbstractTest {

	private static final String SCHEMA_NAME = "foo";

	@Test
	public void testPermanentDatabase() {

		DbH2Database database = new DbH2Database("permanent" + new SecureRandom().nextInt());
		database.setPermanent(true);
		database.createSchema(SCHEMA_NAME);

		DbConnections.closeAll();

		assertTrue(database.getAllSchemas().contains(SCHEMA_NAME));
	}

	@Test
	public void testVolatileDatabase() {

		DbH2Database database = new DbH2Database("volatile" + new SecureRandom().nextInt());
		database.setPermanent(false);
		database.createSchema(SCHEMA_NAME);

		DbConnections.closeAll();

		assertFalse(database.getAllSchemas().contains(SCHEMA_NAME));
	}
}
