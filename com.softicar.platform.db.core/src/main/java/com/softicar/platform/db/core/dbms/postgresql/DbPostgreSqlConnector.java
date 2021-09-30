package com.softicar.platform.db.core.dbms.postgresql;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * A PostgreSQL database JDBC connector, as provided by
 * "{@code org.postgresql:postgresql}" at
 * <a href="https://mvnrepository.com/">mvnrepository.com</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbPostgreSqlConnector extends AbstractDbConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:postgresql://" + database.getHostname() + "/" + database.getDatabaseName();
	}

	@Override
	protected String getDriverClassName() {

		return "org.postgresql.Driver";
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		return new Properties();
	}
}
