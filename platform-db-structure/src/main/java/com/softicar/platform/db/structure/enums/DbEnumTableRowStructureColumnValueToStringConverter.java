package com.softicar.platform.db.structure.enums;

import com.softicar.platform.db.structure.column.IDbColumnStructure;

public class DbEnumTableRowStructureColumnValueToStringConverter {

	private final IDbEnumTableRowStructure structure;

	public DbEnumTableRowStructureColumnValueToStringConverter(IDbEnumTableRowStructure structure) {

		this.structure = structure;
	}

	public String convert(IDbColumnStructure columnStructure) {

		return String
			.format(//
				"%s=%s",
				columnStructure.getName(),
				structure.getValue(columnStructure).toString());
	}
}
