package com.softicar.platform.db.core.transaction;

import com.softicar.platform.common.core.utils.DevNull;

/**
 * Listener interface for {@link IDbTransaction}.
 * <p>
 * Listener instances are registered on {@link IDbTransactionHierarchy}.
 *
 * @author Oliver Richers
 */
public interface IDbTransactionListener {

	/**
	 * This method is called right before the commit of a transaction.
	 *
	 * @param transaction
	 *            the transaction (never null)
	 */
	@SuppressWarnings("resource")
	default void beforeCommit(IDbTransaction transaction) {

		DevNull.swallow(transaction);
	}

	/**
	 * This method is called right before the rollback of a transaction.
	 *
	 * @param transaction
	 *            the transaction (never null)
	 */
	@SuppressWarnings("resource")
	default void beforeRollback(IDbTransaction transaction) {

		DevNull.swallow(transaction);
	}

	/**
	 * This method is called after the commit of a transaction.
	 *
	 * @param transaction
	 *            the transaction (never null)
	 */
	@SuppressWarnings("resource")
	default void afterCommit(IDbTransaction transaction) {

		DevNull.swallow(transaction);
	}

	/**
	 * This method is called after the rollback of a transaction.
	 *
	 * @param transaction
	 *            the transaction (never null)
	 */
	@SuppressWarnings("resource")
	default void afterRollback(IDbTransaction transaction) {

		DevNull.swallow(transaction);
	}
}
