package com.softicar.platform.db.core.statement;

import com.softicar.platform.db.core.table.IDbCoreTable;
import java.util.Collection;
import java.util.Set;

/**
 * Represents a database statement.
 *
 * @author Oliver Richers
 */
public interface IDbStatement {

	/**
	 * Returns whether this statement is empty.
	 *
	 * @return true if this statement contains no text and no parameters
	 */
	boolean isEmpty();

	/**
	 * Returns the statement text.
	 *
	 * @return the statement text (never null)
	 */
	String getText();

	/**
	 * Returns the prepared statement parameters.
	 *
	 * @return the statement parameters (never null)
	 */
	Collection<Object> getParameters();

	/**
	 * Returns the database tables that this statement requires.
	 *
	 * @return the referenced tables (never null)
	 */
	Set<IDbCoreTable> getTables();
}
