package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.DbStatements;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Loads database views from the SQL information schema.
 *
 * @author Oliver Richers
 */
public class DbViewStructuresLoader {

	private static final String BASIC_QUERY = "SELECT TABLE_SCHEMA, TABLE_NAME, VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS";
	private final Set<String> tableSchemasToIgnore;

	public DbViewStructuresLoader() {

		this.tableSchemasToIgnore = new TreeSet<>();
		addIgnoredTableSchema("INFORMATION_SCHEMA");
	}

	/**
	 * Adds the given table schema to the set of schemas to be ignored.
	 *
	 * @param tableSchema
	 *            the table schema to ignore (never null)
	 * @return this object
	 */
	public DbViewStructuresLoader addIgnoredTableSchema(String tableSchema) {

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
	public DbViewStructuresLoader addIgnoredTableSchemas(Collection<String> tableSchemas) {

		tableSchemasToIgnore.addAll(tableSchemas);
		return this;
	}

	public Map<DbTableName, DbViewStructure> loadAll() {

		Map<DbTableName, DbViewStructure> views = new TreeMap<>();
		try (DbResultSet resultSet = createQuery().executeQuery()) {
			while (resultSet.next()) {
				DbTableName viewName = new DbTableName(resultSet.getString(1), resultSet.getString(2));
				views.put(viewName, new DbViewStructure(viewName, resultSet.getString(3)));
			}
		}
		return views;
	}

	private DbStatement createQuery() {

		DbStatement query = new DbStatement(BASIC_QUERY);
		if (!tableSchemasToIgnore.isEmpty()) {
			query.addText(" WHERE TABLE_SCHEMA NOT IN " + DbStatements.createQuestionMarkList(tableSchemasToIgnore.size()));
			query.addParameters(tableSchemasToIgnore);
		}
		return query;
	}
}
