package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.db.core.replication.consistency.AbstractTableRow;
import com.softicar.platform.db.core.table.definition.DbTableKeyDefinition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TableKeyRow extends AbstractTableRow {

	public TableKeyRow(DbTableKeyDefinition keyDefinition, List<String> values) {

		super(keyDefinition.getColumns(), values);
	}

	public TableKeyRow(DbTableKeyDefinition keyDefinition, Map<String, String> valueMap) {

		super(keyDefinition.getColumns(), getValues(keyDefinition, valueMap));
	}

	private static List<String> getValues(DbTableKeyDefinition keyDefinition, Map<String, String> valueMap) {

		ArrayList<String> values = new ArrayList<>(keyDefinition.getColumns().size());
		for (String column: keyDefinition.getColumns()) {
			values.add(valueMap.get(column));
		}
		return values;
	}
}
