package com.softicar.platform.emf.attribute.field.day;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDayAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, Day> {

	public EmfDayAttribute(IDbField<R, Day> field) {

		super(field);

		setDisplayFactory(EmfDayDisplay::new);
		setInputFactory(EmfDayInput::new);
	}
}
