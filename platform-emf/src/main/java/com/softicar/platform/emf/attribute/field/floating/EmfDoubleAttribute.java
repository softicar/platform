package com.softicar.platform.emf.attribute.field.floating;

import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDoubleAttribute<R extends IEmfTableRow<R, ?>> extends EmfFloatingPointAttribute<R, Double> {

	public EmfDoubleAttribute(IDbDoubleField<R> field) {

		super(field, Double::valueOf);
	}
}
