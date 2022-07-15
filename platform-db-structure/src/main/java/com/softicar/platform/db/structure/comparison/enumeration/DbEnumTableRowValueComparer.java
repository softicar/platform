package com.softicar.platform.db.structure.comparison.enumeration;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import java.util.Objects;

public class DbEnumTableRowValueComparer {

	private final IDbColumnStructure columnStructure;

	public DbEnumTableRowValueComparer(IDbColumnStructure columnStructure) {

		this.columnStructure = columnStructure;
	}

	public boolean isEqual(IDbEnumTableRowStructure referenceStructure, IDbEnumTableRowStructure sampleStructure) {

		DbEnumTableRowValueToObjectConverter converter = new DbEnumTableRowValueToObjectConverter();
		Object referenceValue = referenceStructure.getValue(columnStructure).convert(converter);
		Object sampleValue = sampleStructure.getValue(columnStructure).convert(converter);
		return Objects.equals(referenceValue, sampleValue);
	}
}
