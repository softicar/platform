package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.expressions.numeric.ISqlBigDecimalExpression1;
import com.softicar.platform.db.sql.field.ISqlValueField;
import java.math.BigDecimal;

public interface ISqlBigDecimalField<R> extends ISqlValueField<R, BigDecimal>, ISqlBigDecimalExpression1<R> {

	int getPrecision();

	int getScale();
}
