package com.softicar.platform.db.core.metadata;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents the JDBC metadata of a database schema.
 *
 * @author Oliver Richers
 */
public class DbMetadataSchema {

	private final String catalogName;
	private final String schemaName;

	protected DbMetadataSchema(ResultSet resultSet) throws SQLException {

		this.catalogName = resultSet.getString("TABLE_CATALOG");
		this.schemaName = resultSet.getString("TABLE_SCHEM");
	}

	/**
	 * Returns the catalog name of this database schema.
	 *
	 * @return the catalog name (may be null)
	 */
	public String getCatalogName() {

		return catalogName;
	}

	/**
	 * Returns the name of this database schema.
	 *
	 * @return the schema name (never null)
	 */
	public String getSchemaName() {

		return schemaName;
	}
}
