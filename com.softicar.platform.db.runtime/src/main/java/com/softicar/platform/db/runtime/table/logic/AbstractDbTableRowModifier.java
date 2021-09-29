package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbOptionalLazyTransaction;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.listener.IDbTableListener;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.function.BiConsumer;

public abstract class AbstractDbTableRowModifier<R extends IDbTableRow<R, P>, P> {

	protected final IDbTable<R, P> table;

	public AbstractDbTableRowModifier(IDbTable<R, P> table) {

		this.table = table;
	}

	protected void backupRowsForRollback(Collection<R> rows) {

		new DbTableRowBackuper<>(rows).backupForRollback();
	}

	protected DbOptionalLazyTransaction createOptionalLazyTransaction() {

		boolean necessary = table.hasTableListeners() && !DbConnections.isTransactionStarted();
		return new DbOptionalLazyTransaction(necessary);
	}

	protected void sendNotification(BiConsumer<IDbTableListener<R>, Collection<R>> listenerCallback, Collection<R> rows) {

		if (requiresNotification(rows)) {
			table.getTableListeners().forEach(listener -> listenerCallback.accept(listener, rows));
		}
	}

	private boolean requiresNotification(Collection<R> rows) {

		return table.hasTableListeners() && !rows.isEmpty();
	}
}
