package com.softicar.platform.db.core.dbms.mariadb;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * A MariaDB database JDBC connector, as provided by
 * "{@code org.mariadb.jdbc:mariadb-java-client}" at
 * <a href="https://mvnrepository.com/">mvnrepository.com</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbMariaDbConnector extends AbstractDbConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:mariadb://" + database.getHostname() + "/" + database.getDatabaseName();
	}

	@Override
	protected String getDriverClassName() {

		return "org.mariadb.jdbc.Driver";
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		Properties properties = new Properties();
		properties.put("allowMultiQueries", "true");
		properties.put("characterEncoding", "utf8");
		return properties;
	}
}
