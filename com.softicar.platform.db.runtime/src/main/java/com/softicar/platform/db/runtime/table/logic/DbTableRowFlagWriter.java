package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Arrays;

/**
 * Toggles the flags of {@link IDbTableRow}.
 * <p>
 * All flags defined in {@link DbTableRowFlag} can be enabled or disabled.
 * Furthermore, the data-changed flags for all fields can be enabled or
 * disabled.
 * <p>
 * This is the only class writing to the flags of {@link IDbTableRow}.
 *
 * @author Oliver Richers
 */
public class DbTableRowFlagWriter {

	private final byte[] flags;

	/**
	 * Constructs the writer with the given {@link IDbTableRow}.
	 *
	 * @param row
	 *            the row to modify (never null)
	 */
	public DbTableRowFlagWriter(IDbTableRow<?, ?> row) {

		this.flags = row.flags();
	}

	/**
	 * Constructs the writer with the given flag array.
	 *
	 * @param flags
	 *            the flags to modify (never null)
	 */
	public DbTableRowFlagWriter(byte[] flags) {

		this.flags = flags;
	}

	/**
	 * Toggles the {@link DbTableRowFlag#IMPERMANENT} flag.
	 *
	 * @param on
	 *            <i>true</i> to enable the flag and <i>false</i> to disable the
	 *            flag
	 */
	public void setImpermanent(boolean on) {

		setFlag(DbTableRowFlag.IMPERMANENT, on);
	}

	/**
	 * Enables the data-changed flag of the given field as well as the
	 * {@link DbTableRowFlag#DATA_CHANGED} flag for the {@link IDbTableRow}.
	 * <p>
	 * This method does nothing in case of a control field, e.g. the ID field.
	 *
	 * @param field
	 *            the field to enable the data-changed flag for
	 */
	public void setDataChanged(IDbField<?, ?> field) {

		int controlFieldCount = field.getTable().getControlFields().size();

		if (field.getIndex() >= controlFieldCount) {
			setFlag(DbTableRowFlag.DIRTY, true);
			setFlag(DbTableRowFlag.DATA_CHANGED, true);
			setFlag(DbTableRowFlag.values().length + field.getIndex() - controlFieldCount, true);
		}
	}

	/**
	 * Toggles the {@link DbTableRowFlag#DIRTY} flag.
	 *
	 * @param on
	 *            <i>true</i> to enabled the flag and <i>false</i> to disable
	 *            the flag
	 */
	public void setDirty(boolean on) {

		setFlag(DbTableRowFlag.DIRTY, on);
	}

	/**
	 * Toggles the {@link DbTableRowFlag#INVALIDATED} flag.
	 *
	 * @param on
	 *            <i>true</i> to enabled the flag and <i>false</i> to disable
	 *            the flag
	 */
	public void setInvalidated(boolean on) {

		setFlag(DbTableRowFlag.INVALIDATED, on);
	}

	/**
	 * Toggles the {@link DbTableRowFlag#STUB} flag.
	 *
	 * @param on
	 *            <i>true</i> to enabled the flag and <i>false</i> to disable
	 *            the flag
	 */
	public void setStub(boolean on) {

		setFlag(DbTableRowFlag.STUB, on);
	}

	/**
	 * Enables the given flags.
	 * <p>
	 * All other flags will remain unchanged.
	 *
	 * @param flags
	 *            the flags to enable (never null)
	 */
	public void setFlags(DbTableRowFlag...flags) {

		for (DbTableRowFlag flag: flags) {
			setFlag(flag, true);
		}
	}

	/**
	 * Disables all flags, including the data-changed flags for all fields.
	 */
	public void clearFlags() {

		Arrays.fill(flags, (byte) 0);
	}

	// ------------------------------ private ------------------------------ //

	private void setFlag(DbTableRowFlag flag, boolean on) {

		setFlag(flag.ordinal(), on);
	}

	private void setFlag(int flag, boolean on) {

		int mask = 1 << flag % 8;
		if (on) {
			flags[flag / 8] |= mask;
		} else {
			flags[flag / 8] &= ~mask;
		}
	}
}
