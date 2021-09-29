package com.softicar.platform.db.core.dbms.ibm.informix;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * An IBM Informix database JDBC connector, as provided by
 * "{@code ifxjdbc-4.10.jar}".
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbIbmInformixConnector extends AbstractDbConnector {

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:informix-sqli://" + database.getHostname() + "/" + database.getDatabaseName();
	}

	@Override
	protected String getDriverClassName() {

		return "com.informix.jdbc.IfxDriver";
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		return new Properties();
	}
}
