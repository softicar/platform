package com.softicar.platform.db.core.transaction;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.core.connection.IDbConnection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * An {@link IDbTransaction} instance represents a logical transaction on
 * the database management system (DBMS).
 * <p>
 * An {@link IDbTransaction} may represent a root (physical) transaction or a
 * nested (logical) transaction.
 * <ul>
 * <li>A root transaction is a physical transaction on the DBMS, opened with the
 * <i>START TRANSACTION</i> command and closed with the <i>COMMIT</i> or
 * <i>ROLLBACK</i> command.</li>
 * <li>A nested transaction is opened by the <i>SAVEPOINT</i> command and closed
 * by either doing nothing or executing the <i>ROLLBACK TO SAVEPOINT</i>
 * command.</li>
 * </ul>
 */
public interface IDbTransaction extends ITransaction {

	int getId();

	/**
	 * Tests whether this {@link IDbTransaction} is a root transaction.
	 *
	 * @return <i>true</i> if this is a root transaction; <i>false</i> otherwise
	 */
	default boolean isRootTransaction() {

		return !getParentTransaction().isPresent();
	}

	/**
	 * Asserts that this {@link IDbTransaction} is a root transaction.
	 *
	 * @throws SofticarDeveloperException
	 *             if the assertion fails
	 */
	default void assertIsRootTransaction() {

		if (!isRootTransaction()) {
			throw new SofticarDeveloperException("Unexpected nesting of transactions.");
		}
	}

	/**
	 * Tests whether this {@link IDbTransaction} object represents the currently
	 * active transaction on the respective {@link IDbConnection}.
	 *
	 * @return <i>true</i> if this {@link IDbTransaction} is open and no nested
	 *         {@link IDbTransaction} is active; <i>false</i> otherwise
	 */
	boolean isCurrentTransaction();

	Optional<IDbTransaction> getParentTransaction();

	DayTime getBegin();

	/**
	 * Starts this transaction if it has not been started yet.
	 */
	@Override
	void start();

	@Override
	void commit();

	@Override
	void rollback();

	/**
	 * This method makes sure that this transaction is finished properly.
	 * <p>
	 * If neither {@link #commit()} nor {@link #rollback()} was called on this
	 * transaction, this method executes a rollback of this transaction. The
	 * <i>close</i> method is usually called automatically by the <i>try</i>
	 * block that allocated this transaction.
	 */
	@Override
	void close();

	// -------------------- data -------------------- //

	<T> void putData(T data);

	<T> Optional<T> getData(Class<T> dataClass);

	<T> T getOrPutData(Class<T> dataClass, Supplier<T> dataFactory);
}
