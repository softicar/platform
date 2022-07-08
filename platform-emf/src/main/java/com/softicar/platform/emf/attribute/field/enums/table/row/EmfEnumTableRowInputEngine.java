package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;

public class EmfEnumTableRowInputEngine<R extends IDbEnumTableRow<R, ?>> extends DomAutoCompleteDefaultInputEngine<R> {

	public EmfEnumTableRowInputEngine(IDbEnumTable<R, ?> table) {

		addDependsOn(table);
		setLoader(() -> table.createSelect().list());
	}
}
