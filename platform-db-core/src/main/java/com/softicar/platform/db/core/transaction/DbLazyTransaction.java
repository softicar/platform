package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;

/**
 * This transaction postpones the execution of SQL statements as much as
 * possible.
 * <p>
 * If not necessary, this transaction does not execute any SQL statements at
 * all. Even when this transaction class does not execute any SQL statements, it
 * still has an effect on the {@link IDbTransactionListener}. The listeners are
 * called as usual.
 *
 * @author Oliver Richers
 */
public class DbLazyTransaction extends AbstractDbTransaction {

	@SuppressWarnings("resource")
	public DbLazyTransaction() {

		this(DbConnections.getConnection());
	}

	public DbLazyTransaction(IDbConnection connection) {

		super(connection);
	}
}
