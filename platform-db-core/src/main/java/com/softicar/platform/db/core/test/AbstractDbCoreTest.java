package com.softicar.platform.db.core.test;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.database.DbCurrentDatabase;

/**
 * Base class for unit test working on an H2 in-memory database.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDbCoreTest extends AbstractTest {

	protected final DbTestDatabase testDatabase;

	public AbstractDbCoreTest() {

//		Log.finfo(System.getProperty("user.timezone"));

		this.testDatabase = new DbTestDatabase();

		DbCurrentDatabase.set(this.testDatabase);
	}
}
