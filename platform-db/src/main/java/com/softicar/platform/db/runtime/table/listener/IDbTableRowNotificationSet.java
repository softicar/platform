package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link Set} of {@link DbTableRowNotification} objects for a given
 * {@link IDbTable}.
 *
 * @author Oliver Richers
 */
public interface IDbTableRowNotificationSet<R> {

	Collection<DbTableRowNotification<R>> getAllNotifications();

	default Collection<R> getAllTableRows() {

		return getAllNotifications()//
			.stream()
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashList::new));
	}

	default Collection<R> getAllTableRows(DbTableRowNotificationType notificationType) {

		return getAllNotifications()//
			.stream()
			.filter(it -> it.getNotificationType() == notificationType)
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashList::new));
	}

	default Collection<R> getDeletedRows() {

		return getAllTableRows(DbTableRowNotificationType.DELETE);
	}

	default Collection<R> getSavedRows() {

		return getAllTableRows(DbTableRowNotificationType.SAVE);
	}

	default Collection<R> getChangedRows() {

		return getAllTableRows(DbTableRowNotificationType.CHANGE);
	}
}
