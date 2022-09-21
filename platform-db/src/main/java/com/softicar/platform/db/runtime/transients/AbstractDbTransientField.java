package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbBasicTableRow;

public abstract class AbstractDbTransientField<R extends IDbBasicTableRow<R>, V> implements IDbTransientField<R, V> {

	protected V readValueFromCache(R tableRow) {

		return tableRow.table().getTransientValue(tableRow, this);
	}

	protected void writeValueToCache(R tableRow, V value) {

		tableRow.table().setTransientValue(tableRow, this, value);
	}
}
