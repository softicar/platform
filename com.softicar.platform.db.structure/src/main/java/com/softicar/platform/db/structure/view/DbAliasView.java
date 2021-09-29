package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.core.table.DbTableName;

/**
 * An alias view is a view that aims to provide an alternative name for a table.
 * <p>
 * Usually, an alias view is defined as <i>SELECT</i> statement with a simple
 * <i>FROM</i> clause and without <i>JOIN</i> clauses. All table columns are
 * directly mapped to the columns of the view.
 * <p>
 * For example
 *
 * <pre>
 * {@code
 * SELECT tab.a AS a, tab.b AS b FROM db.tab
 * }
 * </pre>
 *
 * @author Oliver Richers
 */
public class DbAliasView {

	private final DbTableName viewName;
	private final DbTableName tableName;
	private final DbAliasViewColumnMap columnMap;

	public DbAliasView(DbTableName viewName, DbTableName tableName, DbAliasViewColumnMap columnMap) {

		this.viewName = viewName;
		this.tableName = tableName;
		this.columnMap = columnMap;
	}

	/**
	 * Returns the name of the view.
	 *
	 * @return the view name (never null)
	 */
	public DbTableName getViewName() {

		return viewName;
	}

	/**
	 * Returns the table from which the view selects its data.
	 *
	 * @return the target table name (never null)
	 */
	public DbTableName getTableName() {

		return tableName;
	}

	/**
	 * Returns a {@link DbAliasViewColumnMap}, reflecting the mapping
	 * between the view columns and the table columns.
	 *
	 * @return instance of {@link DbAliasViewColumnMap} (never null)
	 */
	public DbAliasViewColumnMap getColumnMap() {

		return columnMap;
	}
}
