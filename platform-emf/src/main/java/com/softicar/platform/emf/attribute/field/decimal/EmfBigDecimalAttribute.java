package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.math.BigDecimal;

public class EmfBigDecimalAttribute<R extends IEmfTableRow<R, ?>> extends EmfDecimalAttribute<R, BigDecimal> {

	public EmfBigDecimalAttribute(IDbBigDecimalField<R> field) {

		super(field, BigDecimalMapper.BIG_DECIMAL);

		setScale(field.getScale());
	}
}
