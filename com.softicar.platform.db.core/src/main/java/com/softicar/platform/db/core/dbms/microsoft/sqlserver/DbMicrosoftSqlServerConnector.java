package com.softicar.platform.db.core.dbms.microsoft.sqlserver;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * A Microsoft SQL Server database JDBC connector, as provided by
 * "{@code com.microsoft.sqlserver:mssql-jdbc}" at
 * <a href="https://mvnrepository.com/">mvnrepository.com</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbMicrosoftSqlServerConnector extends AbstractDbConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:sqlserver://" + database.getHostname();
	}

	@Override
	protected String getDriverClassName() {

		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		Properties properties = new Properties();
		String databaseName = database.getDatabaseName();
		if (databaseName != null && !databaseName.isEmpty()) {
			properties.put("databaseName", databaseName);
		}
		return properties;
	}
}
