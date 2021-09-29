package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.core.table.DbTableName;

/**
 * Describes the structure of a database view.
 *
 * @author Oliver Richers
 */
public interface IDbViewStructure {

	/**
	 * Returns the {@link DbTableName} of this database view.
	 *
	 * @return the {@link DbTableName} (never null)
	 */
	DbTableName getViewName();

	/**
	 * Returns the <i>SELECT</i> query of the view.
	 *
	 * @return the view query (never null)
	 */
	String getViewQuery();
}
