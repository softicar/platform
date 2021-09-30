package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import java.util.Collection;

public class DbListeningTestObjectTable extends DbObjectTable<DbListeningTestObject> {

	private final Singleton<DbTestTableListener> listener = new Singleton<>(DbTestTableListener::new);

	public DbListeningTestObjectTable() {

		super(DbListeningTestObject.BUILDER);

		addTableListener(new DelegatingListener());
	}

	public DbTestTableListener getListener() {

		return this.listener.get();
	}

	private class DelegatingListener implements IDbTableListener<DbListeningTestObject> {

		@Override
		public void beforeSave(Collection<DbListeningTestObject> rows) {

			getListener().beforeSave(rows);
		}

		@Override
		public void afterSave(Collection<DbListeningTestObject> rows) {

			getListener().afterSave(rows);
		}

		@Override
		public void beforeDelete(Collection<DbListeningTestObject> rows) {

			getListener().beforeDelete(rows);
		}

		@Override
		public void afterDelete(Collection<DbListeningTestObject> rows) {

			getListener().afterDelete(rows);
		}

		@Override
		public void beforeCommit(IDbTableRowNotificationSet<DbListeningTestObject> notifications) {

			getListener().beforeCommit(notifications);
		}

		@Override
		public void afterCommit(IDbTableRowNotificationSet<DbListeningTestObject> notifications) {

			getListener().afterCommit(notifications);
		}
	}
}
