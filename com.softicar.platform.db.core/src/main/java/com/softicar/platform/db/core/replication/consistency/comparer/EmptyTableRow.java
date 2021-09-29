package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.db.core.table.definition.DbTableDefinition;
import java.util.Arrays;

class EmptyTableRow extends TableRow {

	public EmptyTableRow(DbTableDefinition tableDefinition) {

		super(tableDefinition, Arrays.asList(new String[tableDefinition.getColumns().size()]));
	}
}
