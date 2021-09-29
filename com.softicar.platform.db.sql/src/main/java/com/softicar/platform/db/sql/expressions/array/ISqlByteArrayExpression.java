package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.base.ISqlBasicExpression;

public interface ISqlByteArrayExpression<SELF, BOOL> extends ISqlBasicExpression<BOOL, byte[]> {

	SELF wrap(ISqlExpression<byte[]> expression);
}
