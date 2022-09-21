package com.softicar.platform.db.core.dbms.ibm.as400;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * An IBM AS400 database JDBC connector, as provided by
 * "{@code net.sf.jt400:jt400}" at
 * <a href="https://mvnrepository.com/">mvnrepository.com</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbIbmAs400Connector extends AbstractDbConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:as400://" + database.getHostname() + "/" + database.getDatabaseName();
	}

	@Override
	protected String getDriverClassName() {

		return "com.ibm.as400.access.AS400JDBCDriver";
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		return new Properties();
	}
}
