package com.softicar.platform.emf.attribute.field.bool;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfBooleanAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, Boolean> {

	public EmfBooleanAttribute(IDbField<R, Boolean> field) {

		super(field);

		setDisplayFactory(EmfBooleanDisplay::new);
		setInputFactory(EmfBooleanInput::new);
		setColumnHandlerFactory(() -> new EmfBooleanAttributeColumnHandler<>(this));
	}
}
