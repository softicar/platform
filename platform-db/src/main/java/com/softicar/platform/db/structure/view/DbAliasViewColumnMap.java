package com.softicar.platform.db.structure.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class DbAliasViewColumnMap {

	private final List<DbAliasViewColumnPair> columnPairs;
	private final Map<String, String> tableColumnToViewColumnMap;
	private final Map<String, String> viewColumnToTableColumnMap;

	public DbAliasViewColumnMap() {

		this.columnPairs = new ArrayList<>();
		this.tableColumnToViewColumnMap = new TreeMap<>();
		this.viewColumnToTableColumnMap = new TreeMap<>();
	}

	public DbAliasViewColumnMap addColumnPair(String viewColumn, String tableColumn) {

		return addColumnPair(new DbAliasViewColumnPair(viewColumn, tableColumn));
	}

	public DbAliasViewColumnMap addColumnPair(DbAliasViewColumnPair columnPair) {

		columnPairs.add(columnPair);
		viewColumnToTableColumnMap.put(columnPair.getViewColumn(), columnPair.getTableColumn());
		tableColumnToViewColumnMap.put(columnPair.getTableColumn(), columnPair.getViewColumn());
		return this;
	}

	public List<DbAliasViewColumnPair> getColumnPairs() {

		return Collections.unmodifiableList(columnPairs);
	}

	public Optional<String> getViewColumnForTableColumn(String tableColumn) {

		return Optional.ofNullable(tableColumnToViewColumnMap.get(tableColumn));
	}

	public Optional<String> getTableColumnForViewColumn(String viewColumn) {

		return Optional.ofNullable(viewColumnToTableColumnMap.get(viewColumn));
	}

	@Override
	public String toString() {

		return columnPairs.toString();
	}
}
