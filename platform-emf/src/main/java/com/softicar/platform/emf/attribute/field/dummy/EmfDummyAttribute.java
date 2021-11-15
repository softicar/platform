package com.softicar.platform.emf.attribute.field.dummy;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDummyAttribute<R extends IEmfTableRow<R, ?>, V> extends EmfFieldAttribute<R, V> {

	public EmfDummyAttribute(IDbField<R, V> field) {

		super(field);

		setDataTableStrategyFactory(EmfDummyAttributeDataTableStrategy::new);
	}
}
