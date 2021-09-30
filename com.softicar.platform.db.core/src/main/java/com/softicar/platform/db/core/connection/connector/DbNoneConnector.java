package com.softicar.platform.db.core.connection.connector;

import com.softicar.platform.db.core.database.IDbDatabase;
import java.sql.Connection;
import javax.sql.DataSource;

public class DbNoneConnector implements IDbConnector {

	private static final String ERROR_MESSAGE = "No database server defined.";

	@Override
	public Connection connect(IDbDatabase database) {

		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public DataSource createConnectionPool(IDbDatabase database) {

		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}
}
