package com.softicar.platform.integration.database.structure;

import com.google.gson.Gson;
import java.util.Objects;

/**
 * A structural definition of a database table.
 * <p>
 * <b>WARNING:</b> This class is used to (de-)serialize JSON objects via
 * {@link Gson}. Do not alter it unless absolutely necessary.
 *
 * @author Alexander Schmidt
 */
public class DatabaseStructureTableDefinition {

	private final String tableName;
	private final String createStatement;

	public DatabaseStructureTableDefinition(String tableName, String createStatement) {

		this.tableName = Objects.requireNonNull(tableName);
		this.createStatement = Objects.requireNonNull(createStatement);
	}

	public String getTableName() {

		return tableName;
	}

	public String getCreateStatement() {

		return createStatement;
	}
}
