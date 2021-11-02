package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.table.listener.IDbTableRowNotificationSet;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Interface to hook into the <i>COMMIT</i> process of the root
 * {@link DbTransaction}.
 *
 * @author Oliver Richers
 */
public interface IEmfCommitHook<R> {

	/**
	 * Called before the <i>COMMIT</i> of a root {@link DbTransaction}.
	 *
	 * @param notificationSet
	 *            an {@link IDbTableRowNotificationSet} describing all
	 *            {@link IDbTableRow} objects affected by the
	 *            {@link DbTransaction} (never <i>null</i>)
	 */
	default void beforeCommit(IDbTableRowNotificationSet<R> notificationSet) {

		DevNull.swallow(notificationSet);
	}

	/**
	 * Called after the <i>COMMIT</i> of a root {@link DbTransaction}.
	 *
	 * @param notificationSet
	 *            an {@link IDbTableRowNotificationSet} describing all
	 *            {@link IDbTableRow} objects affected by the
	 *            {@link DbTransaction} (never <i>null</i>)
	 */
	default void afterCommit(IDbTableRowNotificationSet<R> notificationSet) {

		DevNull.swallow(notificationSet);
	}
}
