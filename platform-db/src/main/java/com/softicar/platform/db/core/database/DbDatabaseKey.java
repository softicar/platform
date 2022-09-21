package com.softicar.platform.db.core.database;

import com.softicar.platform.db.core.connection.IDbServerType;
import java.util.Objects;

/**
 * The logical key of a database.
 * <p>
 * Databases with an equal key are considered to be the same database. The key
 * is a triplet of the {@link IDbServerType} the hostname and the database name.
 *
 * @author Oliver Richers
 */
public class DbDatabaseKey {

	private final IDbServerType serverType;
	private final String hostname;
	private final String databaseName;

	public DbDatabaseKey(IDbDatabase database) {

		this.serverType = database.getServerType();
		this.hostname = database.getHostname();
		this.databaseName = database.getDatabaseName();
	}

	public DbDatabaseKey(IDbServerType serverType, String hostname, String databaseName) {

		this.serverType = serverType;
		this.hostname = hostname;
		this.databaseName = databaseName;
	}

	public IDbServerType getServerType() {

		return serverType;
	}

	public String getHostname() {

		return hostname;
	}

	public String getDatabaseName() {

		return databaseName;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DbDatabaseKey) {
			DbDatabaseKey otherKey = (DbDatabaseKey) object;
			return Objects.equals(serverType, otherKey.serverType)//
					&& Objects.equals(hostname, otherKey.hostname)//
					&& Objects.equals(databaseName, otherKey.databaseName);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(serverType, hostname, databaseName);
	}
}
