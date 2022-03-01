package com.softicar.platform.emf.attribute.field.floating;

import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFloatAttribute<R extends IEmfTableRow<R, ?>> extends EmfFloatingPointAttribute<R, Float> {

	public EmfFloatAttribute(IDbFloatField<R> field) {

		super(field, Float::valueOf);
	}
}
