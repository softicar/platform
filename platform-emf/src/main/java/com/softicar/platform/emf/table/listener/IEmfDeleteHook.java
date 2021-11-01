package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;

/**
 * Interface to hook into the <i>DELETE</i> process of {@link IDbTableRow}.
 *
 * @author Oliver Richers
 */
public interface IEmfDeleteHook<R> {

	/**
	 * Called before the <i>DELETE</i> of an {@link IDbTableRow}.
	 *
	 * @param tableRows
	 *            a {@link Collection} of all {@link IDbTableRow} objects being
	 *            deleted from the database (never <i>null</i>)
	 */
	default void beforeDelete(Collection<R> tableRows) {

		DevNull.swallow(tableRows);
	}

	/**
	 * Called after the <i>DELETE</i> of an {@link IDbTableRow}.
	 *
	 * @param tableRows
	 *            a {@link Collection} of all {@link IDbTableRow} objects that
	 *            have been deleted from the database (never <i>null</i>)
	 */
	default void afterDelete(Collection<R> tableRows) {

		DevNull.swallow(tableRows);
	}
}
