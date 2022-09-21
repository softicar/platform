package com.softicar.platform.db.runtime.table.listener;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DbTestTableListener implements IDbTableListener<DbListeningTestObject> {

	private final List<String> notifications = new ArrayList<>();
	private IDbTableListener<DbListeningTestObject> extraListener;

	public void addNotification(String notification) {

		notifications.add(notification);
	}

	@Override
	public void beforeSave(Collection<DbListeningTestObject> objects) {

		notifications.add("beforeSave(%s)".formatted(implode(objects)));
		Optional.ofNullable(extraListener).ifPresent(it -> it.beforeSave(objects));
	}

	@Override
	public void afterSave(Collection<DbListeningTestObject> objects) {

		notifications.add("afterSave(%s)".formatted(implode(objects)));
		Optional.ofNullable(extraListener).ifPresent(it -> it.afterSave(objects));
	}

	@Override
	public void beforeDelete(Collection<DbListeningTestObject> objects) {

		notifications.add("beforeDelete(%s)".formatted(implode(objects)));
		Optional.ofNullable(extraListener).ifPresent(it -> it.beforeDelete(objects));
	}

	@Override
	public void afterDelete(Collection<DbListeningTestObject> objects) {

		notifications.add("afterDelete(%s)".formatted(implode(objects)));
		Optional.ofNullable(extraListener).ifPresent(it -> it.afterDelete(objects));
	}

	private String implode(Collection<DbListeningTestObject> objects) {

		return objects//
			.stream()
			.map(DbListeningTestObject::getString)
			.sorted()
			.collect(Collectors.joining(","));
	}

	@Override
	public void beforeCommit(IDbTableRowNotificationSet<DbListeningTestObject> notificationSet) {

		notifications.add("beforeCommit(%s)".formatted(implode(notificationSet)));
		Optional.ofNullable(extraListener).ifPresent(it -> it.beforeCommit(notificationSet));
	}

	@Override
	public void afterCommit(IDbTableRowNotificationSet<DbListeningTestObject> notificationSet) {

		notifications.add("afterCommit(%s)".formatted(implode(notificationSet)));
		Optional.ofNullable(extraListener).ifPresent(it -> it.afterCommit(notificationSet));
	}

	@Override
	public String toString() {

		return notifications.stream().collect(Collectors.joining("\n"));
	}

	private String implode(IDbTableRowNotificationSet<DbListeningTestObject> notificationSet) {

		return notificationSet//
			.getAllNotifications()
			.stream()
			.map(it -> it.getNotificationType() + "-" + it.getTableRow().getString())
			.sorted()
			.collect(Collectors.joining(","));
	}

	public void setExtraListener(IDbTableListener<DbListeningTestObject> listener) {

		extraListener = listener;
	}

	public void assertNotifications(String expectedNotifications) {

		assertEquals(expectedNotifications.trim(), toString());
	}

	public void clearNotifications() {

		notifications.clear();
	}
}
