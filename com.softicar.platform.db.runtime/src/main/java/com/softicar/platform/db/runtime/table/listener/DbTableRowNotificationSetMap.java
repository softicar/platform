package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Optional;

class DbTableRowNotificationSetMap {

	private final IdentityHashMap<IDbTable<?, ?>, DbTableRowNotificationSet<?>> map;

	public DbTableRowNotificationSetMap() {

		this.map = new IdentityHashMap<>();
	}

	public boolean isEmpty() {

		return map//
			.values()
			.stream()
			.allMatch(DbTableRowNotificationSet::isEmpty);
	}

	public Collection<DbTableRowNotificationSet<?>> getNotificationSets() {

		return map.values();
	}

	public <R> void addNotifications(IDbTable<R, ?> table, DbTableRowNotificationType notificationType, Collection<R> rows) {

		getOrAddNotificationSet(table).addNotifications(notificationType, rows);
	}

	// ------------------------------ merge ------------------------------ //

	public void addAll(DbTableRowNotificationSetMap other) {

		other.map//
			.values()
			.forEach(this::addAll);
	}

	private <R> void addAll(DbTableRowNotificationSet<R> notificationSet) {

		getOrAddNotificationSet(notificationSet.getTable()).addAll(notificationSet);
	}

	// ------------------------------ purge ------------------------------ //

	public void removeAll(DbTableRowNotificationSetMap other) {

		other.map//
			.values()
			.forEach(this::removeAll);
	}

	private <R> void removeAll(DbTableRowNotificationSet<R> notificationSet) {

		getNotificationSet(notificationSet.getTable())//
			.ifPresent(it -> it.removeAll(notificationSet));
	}

	// ------------------------------ auxiliary ------------------------------ //

	@SuppressWarnings("unchecked")
	private <R> Optional<DbTableRowNotificationSet<R>> getNotificationSet(IDbTable<R, ?> table) {

		return Optional.ofNullable((DbTableRowNotificationSet<R>) map.get(table));
	}

	@SuppressWarnings("unchecked")
	private <R> DbTableRowNotificationSet<R> getOrAddNotificationSet(IDbTable<R, ?> table) {

		return (DbTableRowNotificationSet<R>) map.computeIfAbsent(table, DbTableRowNotificationSet::new);
	}
}
