package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;

/**
 * A listener interface for the insertion, deletion or update of table rows.
 * <p>
 * <b>Important:</b> listeners are singletons; do not add any non-final fields
 * to you listener or you will get race-conditions.
 *
 * @author Oliver Richers
 */
public interface IDbTableListener<R> {

	/**
	 * This method is called before the <i>INSERT</i> or <i>UPDATE</i> of a set
	 * of {@link IDbTableRow} objects.
	 * <p>
	 * Implementing this method is useful if you want to compute derived values
	 * or to validate the content of the table rows before they are saved.
	 *
	 * @param rows
	 *            the inserted or updated table rows (never <i>null</i>)
	 */
	default void beforeSave(Collection<R> rows) {

		DevNull.swallow(rows);
	}

	/**
	 * This method is called after the <i>INSERT</i> or <i>UPDATE</i> of a set
	 * of {@link IDbTableRow} objects.
	 *
	 * @param rows
	 *            the inserted or updated table rows (never <i>null</i>)
	 */
	default void afterSave(Collection<R> rows) {

		DevNull.swallow(rows);
	}

	/**
	 * This method is called before the <i>DELETE</i> of a set of
	 * {@link IDbTableRow} objects.
	 *
	 * @param rows
	 *            the deleted table rows (never <i>null</i>)
	 */
	default void beforeDelete(Collection<R> rows) {

		DevNull.swallow(rows);
	}

	/**
	 * This method is called after the <i>DELETE</i> of a set of
	 * {@link IDbTableRow} objects.
	 *
	 * @param rows
	 *            the deleted table rows (never <i>null</i>)
	 */
	default void afterDelete(Collection<R> rows) {

		DevNull.swallow(rows);
	}

	/**
	 * This method is called right before the <i>COMMIT</i> command of a root
	 * transaction is executed.
	 * <p>
	 * The supplied {@link IDbTableRowNotificationSet} informs about all
	 * {@link IDbTableRow} objects that have been affected (deleted, loaded or
	 * saved) by the {@link DbTransaction}.
	 * <p>
	 * Overriding this method is useful for example to implement an audit or log
	 * table. It might also be used to update the values of some computed
	 * fields, e.g. the sum of an invoice when the items have changed.
	 * <p>
	 * Please note that if you save or delete a table row without a transaction,
	 * an implicit transaction will be opened automatically. This means that
	 * this method will be called even if you do not use {@link DbTransaction}
	 * explicitly.
	 *
	 * @param notifications
	 *            notifications about all table rows affected by the
	 *            {@link DbTransaction} (never <i>null</i>)
	 */
	default void beforeCommit(IDbTableRowNotificationSet<R> notifications) {

		DevNull.swallow(notifications);
	}

	/**
	 * This method is called right after the <i>COMMIT</i> command of a root
	 * transaction was executed.
	 * <p>
	 * Equivalent to {@link #beforeCommit} but called <b>after</b> the
	 * <i>COMMIT</i>. Overriding this method is useful to trigger for example UI
	 * updates.
	 *
	 * @param notifications
	 *            notifications about all table rows affected by the
	 *            {@link DbTransaction} (never <i>null</i>)
	 */
	default void afterCommit(IDbTableRowNotificationSet<R> notifications) {

		DevNull.swallow(notifications);
	}
}
