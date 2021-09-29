package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

public class DbTableRowFlagReader {

	private final byte[] flags;

	public DbTableRowFlagReader(IDbTableRow<?, ?> row) {

		this.flags = row.flags();
	}

	public boolean isImpermanent() {

		return isFlagEnabled(DbTableRowFlag.IMPERMANENT);
	}

	public boolean isDataChanged() {

		return isFlagEnabled(DbTableRowFlag.DATA_CHANGED);
	}

	public boolean isDataChanged(IDbField<?, ?> field) {

		int controlFieldCount = field.getTable().getControlFields().size();

		if (field.getIndex() >= controlFieldCount) {
			return isFlagEnabled(DbTableRowFlag.values().length + field.getIndex() - controlFieldCount);
		} else {
			return false;
		}
	}

	public boolean isDirty() {

		return isFlagEnabled(DbTableRowFlag.DIRTY);
	}

	public boolean isInvalidated() {

		return isFlagEnabled(DbTableRowFlag.INVALIDATED);
	}

	public boolean isStub() {

		return isFlagEnabled(DbTableRowFlag.STUB);
	}

	public boolean isFlagEnabled(DbTableRowFlag flag) {

		return isFlagEnabled(flag.ordinal());
	}

	private boolean isFlagEnabled(int flag) {

		int mask = 1 << flag % 8;
		return (flags[flag / 8] & mask) != 0;
	}
}
