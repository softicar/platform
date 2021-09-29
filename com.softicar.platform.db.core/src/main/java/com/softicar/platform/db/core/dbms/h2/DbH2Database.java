package com.softicar.platform.db.core.dbms.h2;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.DbConnectionProperties;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.connection.IDbConnectionProperties;
import com.softicar.platform.db.core.connection.IDbServerType;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implements {@link IDbDatabase} using the H2 in-memory database.
 *
 * @author Oliver Richers
 */
public class DbH2Database implements IDbDatabase {

	private static final String HOSTNAME = "";
	private final String databaseName;
	private boolean permanent;
	private boolean databaseToUpper;

	public DbH2Database(String databaseName) {

		this.databaseName = databaseName;
		this.permanent = false;
		this.databaseToUpper = false;
	}

	/**
	 * Defines if this in-memory database will be permanent or volatile.
	 * <p>
	 * A permanent database will be kept in memory until the Java virtual
	 * machine quits. Otherwise, the database content will vanish a soon as the
	 * last connection to it is closed.
	 *
	 * @param permanent
	 *            <i>true</i> to keep this database permanent in memory,
	 *            <i>false</i> to wipe all data when the last connection closes.
	 */
	public void setPermanent(boolean permanent) {

		this.permanent = permanent;
	}

	/**
	 * Defines if database (schema) names will be converted to upper case for
	 * this in-memory database.
	 * <p>
	 * Conversion is applied by JDBC during query execution.
	 *
	 * @param databaseToUpper
	 *            <i>true</i> to convert schema names to upper case,
	 *            <i>false</i> to leave them as they are
	 */
	public void setDatabaseToUpper(boolean databaseToUpper) {

		this.databaseToUpper = databaseToUpper;
	}

	@Override
	public IDbServerType getServerType() {

		return DbServerType.H2_MEMORY;
	}

	@Override
	public String getHostname() {

		return HOSTNAME;
	}

	@Override
	public String getDatabaseName() {

		return databaseName;
	}

	@Override
	public IDbConnectionProperties getConnectionProperties() {

		DbConnectionProperties properties = new DbConnectionProperties();
		properties.setUsername("");
		properties.setPassword("");
		if (permanent) {
			properties.setProperty("DB_CLOSE_DELAY", "-1");
		}
		properties.setProperty("DATABASE_TO_UPPER", databaseToUpper? "true" : "false");
		return properties;
	}

	// TODO avoid suppression of warning by adding DbStatement.executeQuery(IDbConnection)
	@SuppressWarnings("resource")
	public void createSchema(String name) {

		DbConnections//
			.getConnection(this)
			.executeUncached(new DbStatement("CREATE SCHEMA " + name));
	}

	// TODO avoid suppression of warning by adding DbStatement.executeQuery(IDbConnection)
	@SuppressWarnings("resource")
	public Set<String> getAllSchemas() {

		Set<String> schemas = new TreeSet<>();
		try (DbResultSet resultSet = DbConnections.getConnection(this).executeQuery(new DbStatement("SHOW SCHEMAS"))) {
			while (resultSet.next()) {
				schemas.add(resultSet.getString(1));
			}
		}
		return schemas;
	}
}
