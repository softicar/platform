package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFloatAttribute<R extends IEmfTableRow<R, ?>> extends EmfDecimalAttribute<R, Float> {

	public EmfFloatAttribute(IDbFloatField<R> field) {

		super(field, BigDecimalMapper.FLOAT);
	}
}
