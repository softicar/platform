package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDoubleAttribute<R extends IEmfTableRow<R, ?>> extends EmfDecimalAttribute<R, Double> {

	public EmfDoubleAttribute(IDbDoubleField<R> field) {

		super(field, BigDecimalMapper.DOUBLE);
	}
}
