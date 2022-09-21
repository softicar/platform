package com.softicar.platform.db.core;

import java.sql.SQLException;

/**
 * Old wrapper for {@link SQLException}. Use {@link SqlRuntimeException}
 * instead.
 * 
 * @see SqlRuntimeException
 * @author Oliver Richers
 */
public class SofticarSqlException extends SqlRuntimeException {

	private static final long serialVersionUID = 1L;

	public SofticarSqlException(SQLException sqlException) {

		super(sqlException);
	}
}
