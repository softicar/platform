package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collections;

public abstract class AbstractDbTableRowFieldAccessor<R extends IDbTableRow<R, ?>, V> {

	protected final R row;
	protected final IDbField<R, V> field;

	protected AbstractDbTableRowFieldAccessor(R row, IDbField<R, V> field) {

		this.row = row;
		this.field = field;
	}

	protected void prepareAccess() {

		initializeRowIfNecessary();
		reloadRowIfNecessary();
	}

	private void initializeRowIfNecessary() {

		row.initializeLazily();
	}

	private void reloadRowIfNecessary() {

		if (!row.table().getPrimaryKey().containsField(field) && !row.impermanent()) {
			if (row.stub() || row.invalidated()) {
				row.table().refreshAll(Collections.singleton(row));
			}
		}
	}
}
