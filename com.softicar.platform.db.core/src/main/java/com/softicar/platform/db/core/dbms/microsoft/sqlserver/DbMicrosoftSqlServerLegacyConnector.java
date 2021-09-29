package com.softicar.platform.db.core.dbms.microsoft.sqlserver;

import com.softicar.platform.db.core.database.IDbDatabase;

/**
 * A Microsoft SQL Server database JDBC connector, as provided by the legacy
 * "{@code sqljdbc4.0}" driver series.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbMicrosoftSqlServerLegacyConnector extends DbMicrosoftSqlServerConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:microsoft:sqlserver://" + database.getHostname();
	}

	@Override
	protected String getDriverClassName() {

		return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	}
}
