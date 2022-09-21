package com.softicar.platform.db.core.database;

/**
 * Scope for {@link DbCurrentDatabase#set(IDbDatabase)}.
 *
 * @author Oliver Richers
 */
public class DbDatabaseScope implements IDbDatabaseScope {

	private final IDbDatabase originalDatabase;

	public DbDatabaseScope(IDbDatabase database) {

		// remember original database
		this.originalDatabase = DbCurrentDatabase.get();

		// enforce to use another database connection,
		DbCurrentDatabase.set(database);
	}

	@Override
	public void close() {

		// go back to original database connection
		DbCurrentDatabase.set(originalDatabase);
	}
}
