package com.softicar.platform.db.core.dbms.h2;

import com.softicar.platform.db.core.connection.connector.AbstractDbConnector;
import com.softicar.platform.db.core.database.IDbDatabase;
import java.util.Properties;

/**
 * An H2 database JDBC connector, as provided by "{@code com.h2database:h2}" at
 * <a href="https://mvnrepository.com/">mvnrepository.com</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbH2MemoryConnector extends AbstractDbConnector {

	private boolean databaseToUpper;

	public DbH2MemoryConnector() {

		this.databaseToUpper = false;
	}

	public void setDatabaseToUpper(boolean databaseToUpper) {

		this.databaseToUpper = databaseToUpper;
	}

	@Override
	protected String getUrl(IDbDatabase database) {

		return "jdbc:h2:mem:" + database.getDatabaseName();
	}

	@Override
	protected String getDriverClassName() {

		return org.h2.Driver.class.getCanonicalName();
	}

	@Override
	protected Properties createDefaultProperties(IDbDatabase database) {

		Properties properties = new Properties();
		properties.put("DATABASE_TO_UPPER", databaseToUpper? "true" : "false");
		properties.put("MODE", "MySQL");
		return properties;
	}
}
