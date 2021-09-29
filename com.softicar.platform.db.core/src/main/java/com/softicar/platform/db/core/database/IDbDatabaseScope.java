package com.softicar.platform.db.core.database;

/**
 * Scope interface for {@link DbCurrentDatabase#set(IDbDatabase)}.
 *
 * @author Oliver Richers
 */
public interface IDbDatabaseScope extends AutoCloseable {

	/**
	 * Closes the scope and will not throw a checked exception.
	 */
	@Override
	void close();
}
