package com.softicar.platform.db.structure.enums;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import java.util.stream.Collectors;

public class DbEnumTableRowStructureToStringConverter {

	public String convert(IDbEnumTableRowStructure structure) {

		return structure//
			.getTableStructure()
			.getColumns()
			.stream()
			.filter(this::isRelevantColumn)
			.map(new DbEnumTableRowStructureColumnValueToStringConverter(structure)::convert)
			.collect(Collectors.joining(", "));
	}

	private boolean isRelevantColumn(IDbColumnStructure columnStructure) {

		return isNotIgnored(columnStructure) && isNotIdColumn(columnStructure);
	}

	private boolean isNotIdColumn(IDbColumnStructure columnStructure) {

		return !columnStructure.getTableStructure().getIdColumn().get().equals(columnStructure);
	}

	private boolean isNotIgnored(IDbColumnStructure columnStructure) {

		return !columnStructure.isIgnored();
	}
}
