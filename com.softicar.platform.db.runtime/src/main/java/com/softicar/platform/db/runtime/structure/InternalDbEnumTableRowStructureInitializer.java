package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;

class InternalDbEnumTableRowStructureInitializer<R> implements ISqlFieldValueConsumer<R> {

	private final DbEnumTableRowStructure tableRowStructure;

	public InternalDbEnumTableRowStructureInitializer(DbEnumTableRowStructure tableRowStructure) {

		this.tableRowStructure = tableRowStructure;
	}

	@Override
	public <V> void consumeFieldValue(ISqlField<R, V> field, V value) {

		tableRowStructure.setValue(getColumn(field), IDbEnumTableRowValue.valueOf(value));
	}

	private IDbColumnStructure getColumn(ISqlField<?, ?> field) {

		return tableRowStructure.getTableStructure().getColumnByPhysicalNameOrThrow(field.getName());
	}
}
