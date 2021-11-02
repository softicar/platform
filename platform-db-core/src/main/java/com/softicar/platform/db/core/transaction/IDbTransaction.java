package com.softicar.platform.db.core.transaction;

import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.common.date.DayTime;
import java.util.Optional;
import java.util.function.Supplier;

public interface IDbTransaction extends ITransaction {

	int getId();

	boolean isRootTransaction();

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
