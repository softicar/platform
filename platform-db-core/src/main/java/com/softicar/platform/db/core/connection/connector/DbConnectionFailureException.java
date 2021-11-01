package com.softicar.platform.db.core.connection.connector;

import com.softicar.platform.db.core.SofticarSqlException;
import java.sql.SQLException;

/**
 * Indicates a failure to connect to the DBMS.
 *
 * @author Oliver Richers
 */
public class DbConnectionFailureException extends SofticarSqlException {

	public DbConnectionFailureException(SQLException sqlException) {

		super(sqlException);
	}
}
