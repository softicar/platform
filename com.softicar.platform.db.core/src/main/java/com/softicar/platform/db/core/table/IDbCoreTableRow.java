package com.softicar.platform.db.core.table;

/**
 * Core interface of all database table rows.
 *
 * @author Oliver Richers
 */
public interface IDbCoreTableRow {

	/**
	 * Reloads the data of this table row from the database.
	 * <p>
	 * This method locks the entry in the database for updates, i.e. as a write
	 * operation would do.
	 *
	 * @return <i>true</i> if the table row was reloaded successfully, i.e. it
	 *         still exists in database
	 */
	boolean reloadForUpdate();
}
