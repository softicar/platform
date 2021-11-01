package com.softicar.platform.db.core.table;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.DbStatements;
import com.softicar.platform.db.core.statement.DbStatement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Fetches {@link DbTableName} instances which reflect tables that exist in the
 * currently connected database.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbTableNameFetcher {

	private static final String BASIC_QUERY = "SELECT TABLE_SCHEMA, TABLE_NAME, TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES WHERE 1";
	private final Set<String> tableTypesToConsider;
	private final Set<String> tableSchemasToIgnore;

	public DbTableNameFetcher() {

		this.tableTypesToConsider = new TreeSet<>();
		this.tableSchemasToIgnore = new TreeSet<>();
	}

	/**
	 * Adds the given table type to the set of table types to consider.
	 * <p>
	 * If no table types are explicitly added, all table types will be
	 * considered.
	 *
	 * @param tableType
	 *            the table type to consider (never null)
	 * @return this object
	 */
	public DbTableNameFetcher addTableType(String tableType) {

		tableTypesToConsider.add(tableType);
		return this;
	}

	/**
	 * Adds the given table schema to the set of schemas to be ignored.
	 *
	 * @param tableSchema
	 *            the table schema to ignore (never null)
	 * @return this object
	 */
	public DbTableNameFetcher addIgnoredTableSchema(String tableSchema) {

		tableSchemasToIgnore.add(tableSchema);
		return this;
	}

	/**
	 * Adds the given table schemas to the set of schemas to be ignored.
	 *
	 * @param tableSchemas
	 *            the table schemas to ignore (never null)
	 * @return this object
	 */
	public DbTableNameFetcher addIgnoredTableSchemas(Collection<String> tableSchemas) {

		tableSchemasToIgnore.addAll(tableSchemas);
		return this;
	}

	/**
	 * Fetches all {@link DbTableName} objects for all tables in the currently
	 * connected database, that match the applied filters.
	 * <p>
	 * Call {@link #addTableType} and {@link #addIgnoredTableSchema} to limit
	 * the considered tables.
	 *
	 * @return a set of all matching {@link DbTableName} objects (never null)
	 */
	public Set<DbTableName> fetchAllTableNames() {

		return fetchAllAsMap().keySet();
	}

	/**
	 * Fetches all {@link DbTableName} objects for all tables in the currently
	 * connected database, that match the applied filters.
	 * <p>
	 * Call {@link #addTableType} and {@link #addIgnoredTableSchema} to limit
	 * the considered tables.
	 * <p>
	 * The returned table types conform to the table types defined by the SQL
	 * information schema standard, e.g. BASE TABLE, VIEW, etc.
	 *
	 * @return a map from the {@link DbTableName} to the table type (never null)
	 */
	public Map<DbTableName, String> fetchAllAsMap() {

		Map<DbTableName, String> tables = new TreeMap<>();
		try (DbResultSet resultSet = createQuery().executeQuery()) {
			while (resultSet.next()) {
				tables.put(new DbTableName(resultSet.getString(1), resultSet.getString(2)), resultSet.getString(3));
			}
		}
		return tables;
	}

	private DbStatement createQuery() {

		DbStatement query = new DbStatement(BASIC_QUERY);
		if (!tableTypesToConsider.isEmpty()) {
			query.addText(" AND TABLE_TYPE IN " + DbStatements.createQuestionMarkList(tableTypesToConsider.size()));
			query.addParameters(tableTypesToConsider);
		}
		if (!tableSchemasToIgnore.isEmpty()) {
			query.addText(" AND TABLE_SCHEMA NOT IN " + DbStatements.createQuestionMarkList(tableSchemasToIgnore.size()));
			query.addParameters(tableSchemasToIgnore);
		}
		return query;
	}
}
