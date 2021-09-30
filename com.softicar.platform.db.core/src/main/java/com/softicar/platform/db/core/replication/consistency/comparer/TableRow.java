package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.replication.consistency.AbstractTableRow;
import com.softicar.platform.db.core.table.definition.DbTableDefinition;
import java.util.ArrayList;
import java.util.List;

class TableRow extends AbstractTableRow {

	private final DbTableDefinition tableDefinition;

	public TableRow(DbTableDefinition tableDefinition, List<String> values) {

		super(tableDefinition.getColumns(), values);
		this.tableDefinition = tableDefinition;
	}

	public TableRow(DbTableDefinition tableDefinition, DbResultSet resultSet) {

		super(tableDefinition.getColumns(), readValues(tableDefinition, resultSet));
		this.tableDefinition = tableDefinition;
	}

	public TableKeyRow getPrimaryKeyRow() {

		return new TableKeyRow(tableDefinition.getPrimaryKey(), getValueMap());
	}

	private static List<String> readValues(DbTableDefinition tableDefinition, DbResultSet resultSet) {

		int count = tableDefinition.getColumns().size();

		ArrayList<String> values = new ArrayList<>(count);
		for (int index = 1; index <= count; index++) {
			values.add(resultSet.getString(index));
		}
		return values;
	}
}
