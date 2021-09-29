package com.softicar.platform.emf.attribute.field.integer;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfIntegerAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, Integer> {

	public EmfIntegerAttribute(IDbField<R, Integer> field) {

		super(field);

		setDisplayFactory(EmfIntegerDisplay::new);
		setInputFactory(EmfIntegerInput::new);
	}
}
