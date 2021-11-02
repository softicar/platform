package com.softicar.platform.db.runtime.table.listener;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Enumerates the possible types of {@link DbTableRowNotification} objects.
 *
 * @author Oliver Richers
 */
public enum DbTableRowNotificationType {

	/**
	 * Denotes that a {@link IDbTableRow} was deleted from the database, i.e.
	 * <i>DELETE</i>.
	 */
	DELETE,

	/**
	 * Denotes that a {@link IDbTableRow} was loaded from the database, i.e.
	 * <i>SELECT</i>.
	 */
	LOAD,

	/**
	 * Denotes that a {@link IDbTableRow} was saved to the database, i.e.
	 * <i>INSERT</i> or <i>UPDATE</i>.
	 */
	SAVE,
}
