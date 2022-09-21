package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Objects;

/**
 * A notification that a {@link IDbTableRow} was delete, loaded or saved.
 *
 * @author Oliver Richers
 */
public class DbTableRowNotification<R> {

	private final DbTableRowNotificationType notificationType;
	private final R tableRow;

	public DbTableRowNotification(DbTableRowNotificationType notificationType, R tableRow) {

		this.notificationType = Objects.requireNonNull(notificationType);
		this.tableRow = Objects.requireNonNull(tableRow);
	}

	public DbTableRowNotificationType getNotificationType() {

		return notificationType;
	}

	public R getTableRow() {

		return tableRow;
	}

	@Override
	public int hashCode() {

		return Objects.hash(notificationType, tableRow);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DbTableRowNotification) {
			DbTableRowNotification<?> other = (DbTableRowNotification<?>) object;
			return notificationType.equals(other.notificationType) && tableRow.equals(other.tableRow);
		} else {
			return false;
		}
	}
}
