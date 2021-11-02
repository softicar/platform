package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.listener.DbTableRowCommitNotifier;
import com.softicar.platform.db.runtime.table.listener.DbTableRowNotificationType;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.List;

public class DbTableRowResultSetReader<R extends IDbTableRow<R, P>, P> {

	private final DbTable<R, P> table;
	private final DbResultSet resultSet;
	private final int baseIndex;

	public DbTableRowResultSetReader(DbTable<R, P> table, DbResultSet resultSet, int baseIndex) {

		this.table = table;
		this.resultSet = resultSet;
		this.baseIndex = baseIndex;
	}

	public R read() {

		P primaryKey = table.getPrimaryKey().getFromResultSet(resultSet, baseIndex);

		if (primaryKey == null) {
			// in case of a LEFT JOIN the primary key may actually be NULL
			return null;
		}

		R row = table.getCache().getSimple(primaryKey);
		if (row == null) {
			row = table//
				.getRowFactory()
				.get()
				.initializer()
				.initializeFlagsAndSetPkFields(primaryKey);
			table.getCache().put(primaryKey, row);
			initializeDataFieldsFromResultSet(row);
			queueNotification(row);
		} else if (row.dirty() && !row.invalidated()) {
			// retain pending modifications
		} else {
			initializeDataFieldsFromResultSet(row);
			new DbTableRowFlagWriter(row).clearFlags();
			queueNotification(row);
		}
		return row;
	}

	private void initializeDataFieldsFromResultSet(R row) {

		for (IDbField<R, ?> field: row.table().getDataFields()) {
			initializeFieldFromResultSet(row, field, baseIndex + field.getIndex());
		}
	}

	private <V> void initializeFieldFromResultSet(R row, IDbField<R, V> field, int index) {

		V value = field.getValueType().getValue(resultSet, index);
		field.setValueDirectly(row, value);
	}

	private void queueNotification(R row) {

		DbTableRowCommitNotifier.addNotification(table, DbTableRowNotificationType.LOAD, List.of(row));
	}
}
