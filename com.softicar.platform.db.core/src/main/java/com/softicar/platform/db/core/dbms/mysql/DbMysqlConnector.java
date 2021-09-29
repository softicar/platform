package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * A MySQL database JDBC connector, as provided by
 * "{@code mysql:mysql-connector-java}" at
 * <a href="https://mvnrepository.com/">mvnrepository.com</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbMysqlConnector extends AbstractDbConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:mysql://" + database.getHostname() + "/" + database.getDatabaseName();
	}

	@Override
	protected String getDriverClassName() {

		return "com.mysql.jdbc.Driver";
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		Properties properties = new Properties();
		properties.put("allowMultiQueries", "true");
		properties.put("characterEncoding", "utf8");
		return properties;
	}
}
