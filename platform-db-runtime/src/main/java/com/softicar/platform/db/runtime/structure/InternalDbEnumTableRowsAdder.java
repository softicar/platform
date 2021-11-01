package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.structure.enums.DbEnumTableRowStructure;
import com.softicar.platform.db.structure.table.DbTableStructure;

class InternalDbEnumTableRowsAdder<E extends IDbEnumTableRowEnum<E, ?>> {

	private final DbTableStructure tableStructure;
	private final IDbEnumTable<?, E> enumTable;

	public InternalDbEnumTableRowsAdder(DbTableStructure tableStructure, IDbEnumTable<?, E> enumTable) {

		this.tableStructure = tableStructure;
		this.enumTable = enumTable;
	}

	public void addAll() {

		for (E enumValue: enumTable.getEnums()) {
			DbEnumTableRowStructure tableRowStructure = new DbEnumTableRowStructure(tableStructure);
			enumValue.setFields(new InternalDbEnumTableRowStructureInitializer<>(tableRowStructure));
			tableStructure.addEnumTableRow(tableRowStructure);
		}
	}
}
