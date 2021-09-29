package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransactionListener;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.Optional;

/**
 * This class hooks into a {@link DbTransaction} instance and collects all
 * {@link DbTableRowNotification} objects that were generated during that
 * {@link DbTransaction}.
 * <p>
 * On <i>COMMIT</i> of a nested {@link DbTransaction}, all
 * {@link DbTableRowNotification} objects are propagated to the parent
 * {@link DbTransaction}.
 * <p>
 * Finally, on <i>COMMIT</i> of the root {@link DbTransaction}, this class
 * notifies all {@link IDbTableListener} in the order they were added.
 *
 * @author Oliver Richers
 */
public class DbTableRowCommitNotifier implements IDbTransactionListener {

	// TODO consider making this constant configurable
	private static final int MAXIMUM_NOTIFICATION_ITERATIONS = 10;
	private final DbTableRowNotificationSetMap doneNotifications;
	private DbTableRowNotificationSetMap queuedNotifications;

	public DbTableRowCommitNotifier() {

		this.doneNotifications = new DbTableRowNotificationSetMap();
		this.queuedNotifications = new DbTableRowNotificationSetMap();
	}

	// -------------------- static -------------------- //

	/**
	 * Registers {@link DbTableRowNotification} objects for the given
	 * {@link IDbTableRow} objects in the {@link DbTableRowCommitNotifier} of
	 * the current transaction.
	 *
	 * @param table
	 *            the table of the affected table rows (never <i>null</i>)
	 * @param notificationType
	 *            the notification type (never <i>null</i>)
	 * @param rows
	 *            the modified table rows (never <i>null</i>)
	 */
	public static <R> void addNotification(IDbTable<R, ?> table, DbTableRowNotificationType notificationType, Collection<R> rows) {

		if (table.hasTableListeners() && !rows.isEmpty()) {
			DbConnections//
				.getOrPutTransactionData(DbTableRowCommitNotifier.class, DbTableRowCommitNotifier::new)
				.ifPresent(it -> it.addNotifications(table, notificationType, rows));
		}
	}

	private <R> void addNotifications(IDbTable<R, ?> table, DbTableRowNotificationType notificationType, Collection<R> rows) {

		queuedNotifications.addNotifications(table, notificationType, rows);
	}

	// -------------------- before commit -------------------- //

	/**
	 * If this is the {@link DbTableRowCommitNotifier} of the root transaction,
	 * this method calls {@link IDbTableListener#beforeCommit} on all
	 * {@link IDbTableListener} instances of all affected tables.
	 * <p>
	 * Otherwise, this method does nothing.
	 */
	@Override
	public void beforeCommit(IDbTransaction transaction) {

		if (transaction.isRootTransaction()) {
			notifyBeforeCommitListeners();
		}
	}

	private void notifyBeforeCommitListeners() {

		for (int i = 0; i < MAXIMUM_NOTIFICATION_ITERATIONS; i++) {
			DbTableRowNotificationSetMap notifications = flushQueue();
			notifications.removeAll(doneNotifications);
			if (notifications.isEmpty()) {
				return;
			} else {
				notifications.getNotificationSets().forEach(this::notifyBeforeCommit);
				doneNotifications.addAll(notifications);
			}
		}
		throw new SofticarDeveloperException("Too many before-commit iterations. Stopping to prevent infinite loop.");
	}

	private DbTableRowNotificationSetMap flushQueue() {

		DbTableRowNotificationSetMap notifications = this.queuedNotifications;
		this.queuedNotifications = new DbTableRowNotificationSetMap();
		return notifications;
	}

	private <R> void notifyBeforeCommit(DbTableRowNotificationSet<R> notificationSet) {

		notificationSet//
			.getTable()
			.getTableListeners()
			.forEach(it -> it.beforeCommit(notificationSet));
	}

	// -------------------- after commit -------------------- //

	/**
	 * If this is the {@link DbTableRowCommitNotifier} of the root transaction,
	 * this method calls {@link IDbTableListener#afterCommit} on all
	 * {@link IDbTableListener} instances of all affected tables.
	 * <p>
	 * Otherwise, if this is the {@link DbTableRowCommitNotifier} of a nested
	 * transaction, all collected {@link DbTableRowNotification} objects are
	 * just propagated to the parent transaction.
	 */
	@Override
	public void afterCommit(IDbTransaction transaction) {

		// elevate rows if this is not a root transaction
		transaction//
			.getParentTransaction()
			.ifPresent(this::propagate);

		// notify table listener if this is the root transaction
		if (transaction.isRootTransaction()) {
			doneNotifications.getNotificationSets().forEach(this::notifyAfterCommit);
		}
	}

	private void propagate(IDbTransaction parentTransaction) {

		Optional<DbTableRowCommitNotifier> parentNotifier = parentTransaction.getData(DbTableRowCommitNotifier.class);
		if (parentNotifier.isPresent()) {
			parentNotifier.get().mergeChild(this);
		} else {
			parentTransaction.putData(this);
		}
	}

	private void mergeChild(DbTableRowCommitNotifier childNotifier) {

		queuedNotifications.addAll(childNotifier.queuedNotifications);
	}

	private <R> void notifyAfterCommit(DbTableRowNotificationSet<R> notificationSet) {

		notificationSet//
			.getTable()
			.getTableListeners()
			.forEach(it -> it.afterCommit(notificationSet));
	}
}
