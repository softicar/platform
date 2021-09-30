package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression3;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression3;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlByteArrayExpression3<T0, T1, T2> extends ISqlExpression3<byte[], T0, T1, T2>, ISqlByteArrayExpression<ISqlByteArrayExpression3<T0, T1, T2>, ISqlBooleanExpression3<T0, T1, T2>> {
	@Override
	default ISqlByteArrayExpression3<T0, T1, T2> wrap(ISqlExpression<byte[]> expression) {
		return new SqlByteArrayExpression3<>(expression);
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression3<T0, T1, T2> length() {
		return new SqlIntegerExpression3<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isEqual(ISqlExpression1<byte[], S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression4<T0, T1, T2, S0> isNotEqual(ISqlExpression1<byte[], S0> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

