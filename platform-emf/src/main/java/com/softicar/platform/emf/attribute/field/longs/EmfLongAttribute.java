package com.softicar.platform.emf.attribute.field.longs;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfLongAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, Long> {

	public EmfLongAttribute(IDbField<R, Long> field) {

		super(field);

		setDisplayFactory(EmfLongDisplay::new);
		setInputFactory(EmfLongInput::new);
	}
}
