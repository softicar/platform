package com.softicar.platform.db.core.transaction;

import java.util.ArrayList;
import java.util.List;

class TestListener implements IDbTransactionListener {

	private final List<String> notifications = new ArrayList<>();

	@Override
	public void beforeCommit(IDbTransaction transaction) {

		addNotification("beforeCommit", transaction);
	}

	@Override
	public void beforeRollback(IDbTransaction transaction) {

		addNotification("beforeRollback", transaction);
	}

	@Override
	public void afterCommit(IDbTransaction transaction) {

		addNotification("afterCommit", transaction);
	}

	@Override
	public void afterRollback(IDbTransaction transaction) {

		addNotification("afterRollback", transaction);
	}

	public List<String> getNotifications() {

		return notifications;
	}

	private void addNotification(String name, IDbTransaction transaction) {

		notifications.add(String.format("%s(%s)", name, transaction.getId()));
	}
}
