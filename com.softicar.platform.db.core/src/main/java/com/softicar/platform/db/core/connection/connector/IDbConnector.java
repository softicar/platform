package com.softicar.platform.db.core.connection.connector;

import com.softicar.platform.db.core.database.IDbDatabase;
import java.sql.Connection;
import javax.sql.DataSource;

public interface IDbConnector {

	Connection connect(IDbDatabase database);

	DataSource createConnectionPool(IDbDatabase database);
}
