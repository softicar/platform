package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression4;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression4;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlByteArrayExpression4<T0, T1, T2, T3> extends ISqlExpression4<byte[], T0, T1, T2, T3>, ISqlByteArrayExpression<ISqlByteArrayExpression4<T0, T1, T2, T3>, ISqlBooleanExpression4<T0, T1, T2, T3>> {
	@Override
	default ISqlByteArrayExpression4<T0, T1, T2, T3> wrap(ISqlExpression<byte[]> expression) {
		return new SqlByteArrayExpression4<>(expression);
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression4<T0, T1, T2, T3> length() {
		return new SqlIntegerExpression4<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}
}

