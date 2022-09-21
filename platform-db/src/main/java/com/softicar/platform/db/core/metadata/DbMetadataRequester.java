package com.softicar.platform.db.core.metadata;

import com.softicar.platform.db.core.SofticarSqlException;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.IDbConnection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * Facility to query database metadata.
 *
 * @author Oliver Richers
 */
public class DbMetadataRequester {

	private final DatabaseMetaData metaData;

	@SuppressWarnings("resource")
	public DbMetadataRequester() {

		this(DbConnections.getConnection());
	}

	public DbMetadataRequester(IDbConnection connection) {

		this.metaData = connection.getMetaData();
	}

	// ------------------------------ schemas ------------------------------ //

	/**
	 * Returns all existing database schemas.
	 *
	 * @return a map from schema name to {@link DbMetadataSchema} (never null)
	 */
	public Map<String, DbMetadataSchema> getSchemas() {

		return getSchemas(null);
	}

	/**
	 * Returns the names of all existing database schemas.
	 *
	 * @return all existing schemas (never null)
	 */
	public Set<String> getSchemaNames() {

		return getSchemas().keySet();
	}

	/**
	 * Returns the database schema with the given name.
	 *
	 * @param schemaName
	 *            the name of the schema (never null)
	 * @return the schema as {@link Optional} (never null)
	 */
	public Optional<DbMetadataSchema> getSchema(String schemaName) {

		Objects.requireNonNull(schemaName);
		return Optional.ofNullable(getSchemas(schemaName).get(schemaName));
	}

	// ------------------------------ tables ------------------------------ //

	/**
	 * Returns all existing database tables of the given schema.
	 *
	 * @param schema
	 *            the schema to get all tables from (never null)
	 * @return a map from table name to {@link DbMetadataTable} (never null)
	 */
	public Map<String, DbMetadataTable> getTables(DbMetadataSchema schema) {

		Objects.requireNonNull(schema);
		return getTables(schema, null);
	}

	/**
	 * Returns the names of all existing database tables of the given schema.
	 *
	 * @param schema
	 *            the schema to get all tables from (never null)
	 * @return all existing tables (never null)
	 */
	public Set<String> getTableNames(DbMetadataSchema schema) {

		Objects.requireNonNull(schema);
		return getTables(schema).keySet();
	}

	/**
	 * Returns the database table with the given name.
	 *
	 * @param schema
	 *            the schema containing the table (never null)
	 * @param tableName
	 *            the name of the table (never null)
	 * @return the table as {@link Optional} (never null)
	 */
	public Optional<DbMetadataTable> getTable(DbMetadataSchema schema, String tableName) {

		Objects.requireNonNull(schema);
		Objects.requireNonNull(tableName);
		return Optional.ofNullable(getTables(schema, tableName).get(tableName));
	}

	// ------------------------------ columns ------------------------------ //

	/**
	 * Returns all existing database table columns of the given table.
	 *
	 * @param table
	 *            the table to get all columns from (never null)
	 * @return a map from column name to {@link DbMetadataColumn} (never null)
	 */
	public Map<String, DbMetadataColumn> getColumns(DbMetadataTable table) {

		Objects.requireNonNull(table);
		return getColumns(table, null);
	}

	/**
	 * Returns the names all existing database table columns of the given table.
	 *
	 * @param table
	 *            the table to get all columns from (never null)
	 * @return all existing columns (never null)
	 */
	public Set<String> getColumnNames(DbMetadataTable table) {

		Objects.requireNonNull(table);
		return getColumns(table).keySet();
	}

	/**
	 * Returns the database table column with the given name.
	 *
	 * @param table
	 *            the table containing the column (never null)
	 * @param columnName
	 *            the name of the column (never null)
	 * @return the column as {@link Optional} (never null)
	 */
	public Optional<DbMetadataColumn> getTable(DbMetadataTable table, String columnName) {

		Objects.requireNonNull(table);
		Objects.requireNonNull(columnName);
		return Optional.ofNullable(getColumns(table, columnName).get(columnName));
	}

	// ------------------------------ private ------------------------------ //

	private Map<String, DbMetadataSchema> getSchemas(String schemaPattern) {

		Map<String, DbMetadataSchema> schemas = new TreeMap<>();
		try (ResultSet resultSet = metaData.getSchemas(null, schemaPattern)) {
			while (resultSet.next()) {
				DbMetadataSchema schema = new DbMetadataSchema(resultSet);
				schemas.put(schema.getSchemaName(), schema);
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		return schemas;
	}

	private Map<String, DbMetadataTable> getTables(DbMetadataSchema schema, String tablePattern) {

		Map<String, DbMetadataTable> tables = new TreeMap<>();
		try (ResultSet resultSet = metaData.getTables(schema.getCatalogName(), schema.getSchemaName(), tablePattern, null)) {
			while (resultSet.next()) {
				DbMetadataTable table = new DbMetadataTable(schema, resultSet);
				tables.put(table.getTableName(), table);
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		return tables;
	}

	private Map<String, DbMetadataColumn> getColumns(DbMetadataTable table, String columnPattern) {

		Map<String, DbMetadataColumn> columns = new TreeMap<>();
		try (ResultSet resultSet = metaData
			.getColumns(//
				table.getSchema().getCatalogName(),
				table.getSchema().getSchemaName(),
				table.getTableName(),
				columnPattern)) {
			while (resultSet.next()) {
				DbMetadataColumn column = new DbMetadataColumn(table, resultSet);
				columns.put(column.getColumnName(), column);
			}
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
		return columns;
	}
}
