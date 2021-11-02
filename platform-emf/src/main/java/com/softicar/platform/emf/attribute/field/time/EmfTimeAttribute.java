package com.softicar.platform.emf.attribute.field.time;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfTimeAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, Time> {

	public EmfTimeAttribute(IDbField<R, Time> field) {

		super(field);

		setDisplayFactory(EmfTimeDisplay::new);
		setInputFactory(EmfTimeInput::new);
	}
}
