package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import java.util.Collection;
import java.util.stream.Collectors;

public class DbEnumTableRowStructureComparer {

	public boolean isNotEqual(IDbEnumTableRowStructure referenceStructure, IDbEnumTableRowStructure sampleStructure) {

		return !isEqual(referenceStructure, sampleStructure);
	}

	public boolean isEqual(IDbEnumTableRowStructure referenceStructure, IDbEnumTableRowStructure sampleStructure) {

		for (IDbColumnStructure columnStructure: getCommonColumnStructures(referenceStructure, sampleStructure)) {
			boolean equal = new DbEnumTableRowValueComparer(columnStructure).isEqual(referenceStructure, sampleStructure);
			if (!equal) {
				return false;
			}
		}
		return true;
	}

	private Collection<IDbColumnStructure> getCommonColumnStructures(IDbEnumTableRowStructure referenceStructure, IDbEnumTableRowStructure sampleStructure) {

		return referenceStructure//
			.getTableStructure()
			.getColumns()
			.stream()
			.filter(column -> !column.isIgnored())
			.filter(column -> sampleStructure.getTableStructure().getColumnByPhysicalName(column.getNameOrThrow()) != null)
			.collect(Collectors.toList());
	}
}
