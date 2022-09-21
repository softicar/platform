package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

public class DbTableRowFieldReader<R extends IDbTableRow<R, ?>, V> extends AbstractDbTableRowFieldAccessor<R, V> {

	public DbTableRowFieldReader(R row, IDbField<R, V> field) {

		super(row, field);
	}

	public V read() {

		prepareAccess();
		return field.getValueDirectly(row);
	}
}
