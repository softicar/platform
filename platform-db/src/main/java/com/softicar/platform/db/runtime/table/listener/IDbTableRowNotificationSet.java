package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
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

	/**
	 * Returns all {@link IDbTableRow} instances with a notification of any
	 * type.
	 *
	 * @return all {@link IDbTableRow} instances with a notification (never
	 *         <i>null</i>)
	 */
	default Collection<R> getAllTableRows() {

		return getAllNotifications()//
			.stream()
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashList::new));
	}

	/**
	 * Returns all {@link IDbTableRow} instances with a notification of the
	 * given type.
	 *
	 * @param notificationType
	 *            the {@link DbTableRowNotificationType} to filter for (never
	 *            <i>null</i>)
	 * @return all matching {@link IDbTableRow} instances (never <i>null</i>)
	 */
	default Collection<R> getAllTableRows(DbTableRowNotificationType notificationType) {

		return getAllNotifications()//
			.stream()
			.filter(it -> it.getNotificationType() == notificationType)
			.map(DbTableRowNotification::getTableRow)
			.collect(Collectors.toCollection(HashList::new));
	}

	/**
	 * Returns all {@link IDbTableRow} instances with a notification of type
	 * {@link DbTableRowNotificationType#DELETE}.
	 *
	 * @return all deleted {@link IDbTableRow} instances (never <i>null</i>)
	 */
	default Collection<R> getDeletedRows() {

		return getAllTableRows(DbTableRowNotificationType.DELETE);
	}

	/**
	 * Returns all {@link IDbTableRow} instances with a notification of type
	 * {@link DbTableRowNotificationType#SAVE}.
	 *
	 * @return all saved {@link IDbTableRow} instances (never <i>null</i>)
	 */
	default Collection<R> getSavedRows() {

		return getAllTableRows(DbTableRowNotificationType.SAVE);
	}

	/**
	 * Returns all {@link IDbTableRow} instances with a notification of type
	 * {@link DbTableRowNotificationType#CHANGE}.
	 *
	 * @return all persistently changed {@link IDbTableRow} instances (never
	 *         <i>null</i>)
	 */
	default Collection<R> getChangedRows() {

		return getAllTableRows(DbTableRowNotificationType.CHANGE);
	}
}
