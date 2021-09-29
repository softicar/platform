package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;

/**
 * Asserts the states of all standard flags of a {@link IDbTableRow}.
 * <p>
 * All flags defined in {@link DbTableRowFlag} are verified on a given
 * {@link IDbTableRow}.
 *
 * @author Oliver Richers
 */
public class DbTableRowFlagsAsserter {

	private final IDbTableRow<?, ?> row;

	public DbTableRowFlagsAsserter(IDbTableRow<?, ?> row) {

		this.row = row;
	}

	public void assertNone() {

		assertGeneralFlags(Collections.emptySet());
		assertFieldChangedFlags(Collections.emptySet());
	}

	public void assertOnlyImpermanent() {

		assertOnly(DbTableRowFlag.IMPERMANENT);
	}

	public void assertOnlyDirty() {

		assertOnly(DbTableRowFlag.DIRTY);
	}

	public void assertOnlyImpermanentAndInvalidated() {

		assertGeneralFlags(DbTableRowFlag.IMPERMANENT, DbTableRowFlag.INVALIDATED);
		assertFieldChangedFlags(Collections.emptySet());
	}

	public void assertOnlyImpermanentAndDirtyAndDataChanged(IDbField<?, ?>...fields) {

		assertGeneralFlags(DbTableRowFlag.IMPERMANENT, DbTableRowFlag.DIRTY, DbTableRowFlag.DATA_CHANGED);
		assertFieldChangedFlags(fields);
	}

	public void assertOnlyDirtyAndDataChanged(IDbField<?, ?>...fields) {

		assertGeneralFlags(DbTableRowFlag.DIRTY, DbTableRowFlag.DATA_CHANGED);
		assertFieldChangedFlags(fields);
	}

	public void assertOnlyInvalidated() {

		assertOnly(DbTableRowFlag.INVALIDATED);
	}

	public void assertOnlyStub() {

		assertOnly(DbTableRowFlag.STUB);
	}

	public void assertOnly(DbTableRowFlag first, DbTableRowFlag...other) {

		assertGeneralFlags(EnumSet.of(first, other));
		assertFieldChangedFlags();
	}

	// ------------------------------ private ------------------------------ //

	private void assertGeneralFlags(DbTableRowFlag first, DbTableRowFlag...other) {

		assertGeneralFlags(EnumSet.of(first, other));
	}

	private void assertGeneralFlags(Set<DbTableRowFlag> flags) {

		for (DbTableRowFlag flag: DbTableRowFlag.values()) {
			if (flags.contains(flag)) {
				Assert
					.assertTrue(//
						String.format("Assumed %s flag to be enabled.", flag),
						new DbTableRowFlagReader(row).isFlagEnabled(flag));
			} else {
				Assert
					.assertFalse(//
						String.format("Assumed %s flag to be disabled.", flag),
						new DbTableRowFlagReader(row).isFlagEnabled(flag));
			}
		}
	}

	private void assertFieldChangedFlags(IDbField<?, ?>...fields) {

		assertFieldChangedFlags(new HashSet<>(Arrays.asList(fields)));
	}

	private void assertFieldChangedFlags(Set<IDbField<?, ?>> fields) {

		for (IDbField<?, ?> field: row.table().getDataFields()) {
			if (fields.contains(field)) {
				Assert
					.assertTrue(//
						String.format("Assumed data-change flag for field %s to be enabled.", field),
						new DbTableRowFlagReader(row).isDataChanged(field));
			} else {
				Assert
					.assertFalse(//
						String.format("Assumed data-change flag for field %s to be disabled.", field),
						new DbTableRowFlagReader(row).isDataChanged(field));
			}
		}
	}
}
