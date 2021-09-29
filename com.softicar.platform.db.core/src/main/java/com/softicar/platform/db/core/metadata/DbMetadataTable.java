package com.softicar.platform.db.core.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents the JDBC metadata of a database table.
 *
 * @author Oliver Richers
 */
public class DbMetadataTable {

	private final DbMetadataSchema schema;
	private final String tableName;
	private final String tableType;
	private final String tableRemarks;

	protected DbMetadataTable(DbMetadataSchema schema, ResultSet resultSet) throws SQLException {

		this.schema = schema;
		this.tableName = resultSet.getString("TABLE_NAME");
		this.tableType = resultSet.getString("TABLE_TYPE");
		this.tableRemarks = resultSet.getString("REMARKS");
	}

	/**
	 * Returns the database schema that this table belongs to.
	 *
	 * @return the schema (never null)
	 */
	public DbMetadataSchema getSchema() {

		return schema;
	}

	/**
	 * Returns the name of this database table.
	 *
	 * @return the table name (never null)
	 */
	public String getTableName() {

		return tableName;
	}

	/**
	 * Returns the JDBC type of this table.
	 * <p>
	 * This can be one of "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY",
	 * "LOCAL TEMPORARY", "ALIAS", "SYNONYM", etc.
	 *
	 * @return the JDBC table type (never null)
	 */
	public String getTableType() {

		return tableType;
	}

	/**
	 * Returns an optional comment on this table.
	 *
	 * @return a table comment (may be null)
	 */
	public String getTableRemarks() {

		return tableRemarks;
	}
}
