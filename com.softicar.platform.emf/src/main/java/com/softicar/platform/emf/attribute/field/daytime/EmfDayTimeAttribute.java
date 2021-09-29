package com.softicar.platform.emf.attribute.field.daytime;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDayTimeAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, DayTime> {

	public EmfDayTimeAttribute(IDbField<R, DayTime> field) {

		super(field);

		setDisplayFactory(EmfDayTimeDisplay::new);
		setInputFactory(EmfDayTimeInput::new);
	}
}
