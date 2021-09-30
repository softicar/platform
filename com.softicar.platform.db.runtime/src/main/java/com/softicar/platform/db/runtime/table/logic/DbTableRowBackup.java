package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.common.container.array.ArrayCopying;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * Backup of an {@link IDbTableRow}.
 *
 * @author Oliver Richers
 */
public class DbTableRowBackup<R extends IDbTableRow<R, P>, P> {

	private final WeakReference<R> rowReference;
	private final R backup;

	public DbTableRowBackup(R row) {

		this.rowReference = new WeakReference<>(row);
		this.backup = row//
			.table()
			.getRowFactory()
			.get()
			.initializer()
			.initializeFlagsAndSetFieldsToNull();

		backupFields(row);
		backupFlags(row);
	}

	public void restore() {

		R row = rowReference.get();
		if (row != null) {
			boolean undoSave = row.pk() != null && backup.pk() == null;
			boolean undoDelete = row.pk() == null && backup.pk() != null;

			removeFromCache(row, undoSave);
			restoreFields(row);
			restoreFlags(row);
			addToCache(row, undoDelete);
		}
	}

	public <V> boolean isChanged(IDbField<R, V> field) {

		R row = rowReference.get();
		if (row != null) {
			return !Objects.equals(field.getValue(row), field.getValue(backup));
		} else {
			return false;
		}
	}

	public <V> V getValue(IDbField<R, V> field) {

		return field.getValueDirectly(backup);
	}

	// -------------------- fields -------------------- //

	private void backupFields(R row) {

		row.table().getAllFields().forEach(field -> backupField(row, field));
	}

	private <V> void backupField(R row, IDbField<R, V> field) {

		V value = field.getValueDirectly(row);
		field.setValueDirectly(backup, field.copyValue(value));
	}

	private void restoreFields(R row) {

		row.table().getAllFields().forEach(field -> restoreField(row, field));
	}

	private <V> void restoreField(R row, IDbField<R, V> field) {

		field.setValueDirectly(row, field.getValueDirectly(backup));
	}

	// -------------------- flags -------------------- //

	private void backupFlags(R row) {

		ArrayCopying.copyArray(row.flags(), backup.flags());
	}

	private void restoreFlags(R row) {

		ArrayCopying.copyArray(backup.flags(), row.flags());
	}

	// -------------------- cache -------------------- //

	private void addToCache(R row, boolean undoDelete) {

		if (undoDelete) {
			row.table().getCache().put(row.pk(), row);
		}
	}

	private void removeFromCache(R row, boolean undoSave) {

		if (undoSave) {
			row.table().getCache().remove(row.pk());
		}
	}
}
