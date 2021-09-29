package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;

/**
 * Interface to hook into the <i>SAVE</i> process of {@link IDbTableRow}.
 *
 * @author Oliver Richers
 */
public interface IEmfSaveHook<R> {

	/**
	 * Called before the <i>INSERT</i> or <i>UPDATE</i> of an
	 * {@link IDbTableRow}.
	 *
	 * @param tableRows
	 *            a {@link Collection} of all {@link IDbTableRow} objects being
	 *            saved to the database (never <i>null</i>)
	 */
	default void beforeSave(Collection<R> tableRows) {

		DevNull.swallow(tableRows);
	}

	/**
	 * Called after the <i>INSERT</i> or <i>UPDATE</i> of an
	 * {@link IDbTableRow}.
	 *
	 * @param tableRows
	 *            a {@link Collection} of all {@link IDbTableRow} objects that
	 *            have been saved to the database (never <i>null</i>)
	 */
	default void afterSave(Collection<R> tableRows) {

		DevNull.swallow(tableRows);
	}
}
