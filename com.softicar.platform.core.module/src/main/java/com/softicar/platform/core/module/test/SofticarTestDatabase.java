package com.softicar.platform.core.module.test;

import com.softicar.platform.core.module.test.fixture.IDatabaseTestFixtureApplier;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.test.DbTestDatabase;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;

/**
 * Extension of the {@link DbTestDatabase}.
 * <p>
 * Uses {@link DbAutomaticTableCreator} to create all accessed database tables.
 *
 * @author Oliver Richers
 */
public class SofticarTestDatabase extends DbTestDatabase {

	public SofticarTestDatabase() {

		addListener(new DbAutomaticTableCreator(() -> 1));
	}

	/**
	 * Executes the given {@link IDatabaseTestFixtureApplier} to the internal
	 * {@link DbTestDatabase}.
	 *
	 * @param fixtureApplier
	 *            a {@link IDatabaseTestFixtureApplier} (never null)
	 * @return the applied test fixture
	 */
	public <T> T applyFixture(IDatabaseTestFixtureApplier<T> fixtureApplier) {

		try (DbDatabaseScope databaseScope = new DbDatabaseScope(this)) {
			try (DbConnectionOverrideScope connectionScope = new DbConnectionOverrideScope(this)) {
				return fixtureApplier.apply();
			}
		}
	}
}
