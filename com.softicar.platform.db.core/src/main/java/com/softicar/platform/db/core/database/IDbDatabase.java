package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.connection.IDbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbServerType;
import com.softicar.platform.db.core.statement.IDbStatementExecutionListener;

/**
 * Defines the database server and the name of the database.
 *
 * @author Oliver Richers
 */
public interface IDbDatabase extends IDbStatementExecutionListener {

	/**
	 * Returns the type of database management system.
	 *
	 * @return the DBMS system type (never null)
	 */
	IDbServerType getServerType();

	/**
	 * Returns the name of the host containing this database.
	 *
	 * @return the hostname (never null)
	 */
	String getHostname();

	/**
	 * Returns the name of the database.
	 *
	 * @return the database name (never null)
	 */
	String getDatabaseName();

	/**
	 * Returns additional connection properties for this database, e.g. username
	 * and password.
	 *
	 * @return the connection properties (never null)
	 */
	IDbConnectionProperties getConnectionProperties();
}
