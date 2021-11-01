package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.connection.DbConnections;

/**
 * A {@link DbLazyTransaction} which only starts a new transaction if none is
 * active.
 *
 * @author Oliver Richers
 */
public class DbOptionalLazyTransaction implements AutoCloseable {

	private DbLazyTransaction transaction;

	public DbOptionalLazyTransaction() {

		this(!isInTransaction());
	}

	public DbOptionalLazyTransaction(boolean necessary) {

		this.transaction = getTransaction(necessary);
	}

	public void commit() {

		if (transaction != null) {
			transaction.commit();
			transaction = null;
		}
	}

	public void rollback() {

		if (transaction != null) {
			transaction.rollback();
			transaction = null;
		}
	}

	@Override
	public void close() {

		if (transaction != null) {
			transaction.close();
			transaction = null;
		}
	}

	private static DbLazyTransaction getTransaction(boolean necessary) {

		return necessary? new DbLazyTransaction() : null;
	}

	private static boolean isInTransaction() {

		return DbConnections.isTransactionStarted();
	}
}
