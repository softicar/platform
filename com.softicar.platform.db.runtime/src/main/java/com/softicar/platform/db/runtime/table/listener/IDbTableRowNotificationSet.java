package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;
import java.util.HashSet;
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

	default Set<R> getAllTableRows() {

		return getAllNotifications()//
			.stream()
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashSet::new));
	}

	default Set<R> getAllTableRows(DbTableRowNotificationType notificationType) {

		return getAllNotifications()//
			.stream()
			.filter(it -> it.getNotificationType() == notificationType)
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashSet::new));
	}

	default Collection<R> getDeletedRows() {

		return getAllTableRows(DbTableRowNotificationType.DELETE);
	}

	default Collection<R> getLoadedRows() {

		return getAllTableRows(DbTableRowNotificationType.LOAD);
	}

	default Collection<R> getSavedRows() {

		return getAllTableRows(DbTableRowNotificationType.SAVE);
	}
}
