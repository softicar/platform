package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.container.derived.DerivedObject;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EmfEnumTableRowInputEngine<R extends IDbEnumTableRow<R, ?>> implements IDomAutoCompleteInputEngine<R> {

	private final IDbEnumTable<R, ?> targetTable;
	private final DerivedObject<Map<String, R>> rows;

	public EmfEnumTableRowInputEngine(IDbEnumTable<R, ?> table) {

		this.targetTable = table;
		this.rows = new DerivedObject<>(this::loadRows).addDependsOn(table);
	}

	@Override
	public IDisplayString getDisplayString(R row) {

		return EmfEnumTableRows.toDisplay(row);
	}

	@Override
	public Collection<R> findMatches(String pattern, int limit) {

		return rows//
			.get()
			.entrySet()
			.stream()
			.filter(entry -> entry.getKey().contains(pattern))
			.map(entry -> entry.getValue())
			.limit(limit)
			.collect(Collectors.toList());
	}

	private Map<String, R> loadRows() {

		Map<String, R> rows = new TreeMap<>();
		targetTable.createSelect().forEach(row -> rows.put(getDisplayString(row).toString().toLowerCase(), row));
		return rows;
	}
}
