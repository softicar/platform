package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Objects;

public class DbTableRowFieldWriter<R extends IDbTableRow<R, P>, P, V> extends AbstractDbTableRowFieldAccessor<R, V> {

	public DbTableRowFieldWriter(R row, IDbField<R, V> field) {

		super(row, field);
	}

	public boolean write(V value) {

		prepareAccess();

		if (Objects.equals(value, field.getValue(row))) {
			new DbTableRowFlagWriter(row).setDirty(true);
			return false;
		} else {
			new DbTableRowBackuper<>(row).backupForRollback();
			field.setValueDirectly(row, value);
			new DbTableRowFlagWriter(row).setDataChanged(field);
			return true;
		}
	}
}
