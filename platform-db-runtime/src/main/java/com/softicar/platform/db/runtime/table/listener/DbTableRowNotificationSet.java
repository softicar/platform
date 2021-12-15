package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;

class DbTableRowNotificationSet<R> implements IDbTableRowNotificationSet<R> {

	private final IDbTable<R, ?> table;
	private final Collection<DbTableRowNotification<R>> notifications;

	public DbTableRowNotificationSet(IDbTable<R, ?> table) {

		this.table = table;
		this.notifications = new HashList<>();
	}

	public IDbTable<R, ?> getTable() {

		return table;
	}

	@Override
	public Collection<DbTableRowNotification<R>> getAllNotifications() {

		return notifications;
	}

	public boolean isEmpty() {

		return notifications.isEmpty();
	}

	public void addNotifications(DbTableRowNotificationType notificationType, Collection<R> rows) {

		rows.forEach(row -> notifications.add(new DbTableRowNotification<>(notificationType, row)));
	}

	public void addAll(DbTableRowNotificationSet<R> other) {

		notifications.addAll(other.notifications);
	}

	public void removeAll(DbTableRowNotificationSet<R> other) {

		notifications.removeAll(other.notifications);
	}
}
