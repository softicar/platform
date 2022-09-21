package com.softicar.platform.db.core.table;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import java.util.Objects;

/**
 * Represents the qualified name of a database table.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DbTableName implements Comparable<DbTableName> {

	private final String databaseName;
	private final String simpleName;

	public DbTableName(String databaseName, String simpleName) {

		this.databaseName = Objects.requireNonNull(databaseName);
		this.simpleName = Objects.requireNonNull(simpleName);
	}

	/**
	 * Returns the name of the database in which the table resides.
	 *
	 * @return the name of the database in which the table resides (never null)
	 */
	public String getDatabaseName() {

		return databaseName;
	}

	/**
	 * Returns the simple (unqualified) name of the table (i.e. without any
	 * database prefix).
	 *
	 * @return the simple (unqualified) name of the table (never null)
	 */
	public String getSimpleName() {

		return simpleName;
	}

	/**
	 * Returns the canonical (qualified) name of the table (i.e. with database
	 * prefix).
	 *
	 * @return the canonical (qualified) name of the table (never null)
	 */
	public String getCanonicalName() {

		return getDatabaseName() + "." + getSimpleName();
	}

	/**
	 * Returns the quoted, qualified name of the table (i.e. with database
	 * prefix).
	 * <p>
	 * The type of the quoted depends on the {@link IDbServerQuirks} of the
	 * current database connection.
	 *
	 * @return the quoted, qualified name of the table (never null)
	 */
	public String getQuoted() {

		IDbServerQuirks serverQuirks = DbConnections.getServerQuirks();
		return serverQuirks.getQuotedIdentifier(getDatabaseName()) + "." + serverQuirks.getQuotedIdentifier(getSimpleName());
	}

	/**
	 * Checks whether this {@link DbTableName} is equal to the given
	 * {@link DbTableName}.
	 *
	 * @param other
	 *            the {@link DbTableName} to which this {@link DbTableName}
	 *            shall be compared (never null)
	 * @return <i>true</i> if this {@link DbTableName} is equal to the given
	 *         {@link DbTableName}; <i>false</i> otherwise
	 */
	public boolean equals(DbTableName other) {

		return other != null && databaseName.equals(other.databaseName) && simpleName.equals(other.simpleName);
	}

	@Override
	public int compareTo(DbTableName other) {

		int result = databaseName.compareToIgnoreCase(other.databaseName);
		return result != 0? result : simpleName.compareToIgnoreCase(other.simpleName);
	}

	@Override
	public boolean equals(Object other) {

		return other instanceof DbTableName && equals((DbTableName) other);
	}

	@Override
	public int hashCode() {

		return Objects.hash(databaseName, simpleName);
	}

	@Override
	public String toString() {

		return getQuoted();
	}
}
