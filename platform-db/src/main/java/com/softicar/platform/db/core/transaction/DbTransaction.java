package com.softicar.platform.db.core.transaction;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;

/**
 * Represents a database transaction.
 * <p>
 * MySQL by itself does not support nested transactions, but through the use of
 * save points, we can simulate this feature. The outermost transaction executes
 * the BEGIN statement when it's created, while all nested transactions only
 * execute the SAVEPOINT statement with a unique name, that is assigned to each
 * of them. On commit of the nested transactions, nothing happens, while only on
 * rollback, a ROLLBACK TO SAVEPOINT statement to their respective save point is
 * done. Finally, when the outermost transaction is committed, a COMMIT
 * statement is executed, or a ROLLBACK if you call the rollback() function.
 *
 * @see DbTransactionHierarchy
 * @author Oliver Richers
 */
public class DbTransaction extends AbstractDbTransaction {

	/**
	 * Starts a new transaction on the default database connection.
	 * <p>
	 * If another transaction was already started, this will not really start a
	 * new transaction but create a new save point. If you rollback this
	 * transaction, the rollback will only undo the changes made after this save
	 * point. Changes made before this transaction was started will be retained.
	 */
	@SuppressWarnings("resource")
	public DbTransaction() {

		this(DbConnections.getConnection());
	}

	/**
	 * Starts a new transaction on the specified database connection.
	 */
	public DbTransaction(IDbConnection connection) {

		super(connection);

		start();
	}
}
