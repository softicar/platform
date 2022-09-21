package com.softicar.platform.db.runtime.test;

import com.softicar.platform.db.core.test.AbstractDbCoreTest;
import com.softicar.platform.db.runtime.table.creator.DbAutomaticTableCreator;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Extension of {@link AbstractDbCoreTest} with {@link DbAutomaticTableCreator}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDbTest extends AbstractDbCoreTest {

	private final DbAutomaticTableCreator tableCreator;
	private Supplier<Integer> autoIncrementSupplier;

	public AbstractDbTest() {

		this.tableCreator = new DbAutomaticTableCreator(this::getAutoIncrement);
		this.autoIncrementSupplier = new DbRandomAutoIncrementSupplier();

		testDatabase.addListener(tableCreator);
	}

	/**
	 * Overrides the default auto-increment supplier for the automatic table
	 * creation.
	 *
	 * @param autoIncrementSupplier
	 *            the new auto-increment supplier to use (never null)
	 */
	public void setAutoIncrementSupplier(Supplier<Integer> autoIncrementSupplier) {

		this.autoIncrementSupplier = Objects.requireNonNull(autoIncrementSupplier);
	}

	private Integer getAutoIncrement() {

		return autoIncrementSupplier.get();
	}
}
