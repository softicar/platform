package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.sql.fields.ISqlBigDecimalField;
import java.math.BigDecimal;

public interface IDbBigDecimalField<R> extends IDbPrimitiveField<R, BigDecimal>, ISqlBigDecimalField<R> {

	// nothing to add
}
