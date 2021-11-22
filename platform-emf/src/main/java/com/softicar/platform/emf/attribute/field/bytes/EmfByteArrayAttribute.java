package com.softicar.platform.emf.attribute.field.bytes;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfByteArrayAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, byte[]> {

	public EmfByteArrayAttribute(IDbField<R, byte[]> field) {

		super(field);
	}
}
