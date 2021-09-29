package com.softicar.platform.db.core;

import java.sql.SQLException;

/**
 * Wrapper of type {@link RuntimeException} around {@link SQLException}.
 * 
 * @author Oliver Richers
 */
public class SqlRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final SQLException sqlException;

	public SqlRuntimeException(SQLException sqlException) {

		super(sqlException);

		this.sqlException = sqlException;
	}

	public SQLException getSqlException() {

		return sqlException;
	}
}
