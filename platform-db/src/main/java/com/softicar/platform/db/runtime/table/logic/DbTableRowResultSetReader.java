package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.listener.DbTableRowCommitNotifier;
import com.softicar.platform.db.runtime.table.listener.DbTableRowNotificationType;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.List;
import java.util.Objects;

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
			setDataFieldsFromResultSet(row);
		} else if (row.dirty() && !row.invalidated()) {
			// retain pending modifications
		} else {
			var changed = setDataFieldsFromResultSet(row);
			addNotification(row, changed);
			new DbTableRowFlagWriter(row).clearFlags();
		}
		return row;
	}

	private boolean setDataFieldsFromResultSet(R row) {

		var changed = false;
		for (IDbField<R, ?> field: row.table().getDataFields()) {
			changed |= setFieldFromResultSet(row, field, baseIndex + field.getIndex());
		}
		return changed;
	}

	private <V> boolean setFieldFromResultSet(R row, IDbField<R, V> field, int index) {

		var oldValue = field.getValueDirectly(row);
		var newValue = field.getValueType().getValue(resultSet, index);
		field.setValueDirectly(row, newValue);
		return !Objects.equals(oldValue, newValue);
	}

	private void addNotification(R row, boolean changed) {

		if (changed && !row.stub()) {
			DbTableRowCommitNotifier.addNotification(table, DbTableRowNotificationType.CHANGE, List.of(row));
		}
	}
}
