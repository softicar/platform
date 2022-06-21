package com.softicar.platform.core.module.test;

import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.test.DbTestDatabase;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;
import java.util.function.Supplier;

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
	 * @deprecated use {@link IDbDatabase#apply}
	 */
	@Deprecated
	public <T> T applyFixture(Supplier<T> supplier) {

		return super.apply(supplier);
	}
}
