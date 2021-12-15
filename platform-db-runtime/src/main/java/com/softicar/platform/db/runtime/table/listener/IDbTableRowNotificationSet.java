package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link Set} of {@link DbTableRowNotification} objects for a given
 * {@link IDbTable}.
 *
 * @author Oliver Richers
 */
public interface IDbTableRowNotificationSet<R> {

	List<DbTableRowNotification<R>> getAllNotifications();

	default List<R> getAllTableRows() {

		return getAllNotifications()//
			.stream()
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashList::new));
	}

	default List<R> getAllTableRows(DbTableRowNotificationType notificationType) {

		return getAllNotifications()//
			.stream()
			.filter(it -> it.getNotificationType() == notificationType)
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashList::new));
	}

	default List<R> getDeletedRows() {

		return getAllTableRows(DbTableRowNotificationType.DELETE);
	}

	default List<R> getLoadedRows() {

		return getAllTableRows(DbTableRowNotificationType.LOAD);
	}

	default List<R> getSavedRows() {

		return getAllTableRows(DbTableRowNotificationType.SAVE);
	}
}
