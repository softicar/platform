package com.softicar.platform.db.sql.expressions.array;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression1;
import com.softicar.platform.db.sql.expressions.numeric.SqlIntegerExpression1;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlByteArrayExpression1<T0> extends ISqlExpression1<byte[], T0>, ISqlByteArrayExpression<ISqlByteArrayExpression1<T0>, ISqlBooleanExpression<T0>> {
	@Override
	default ISqlByteArrayExpression1<T0> wrap(ISqlExpression<byte[]> expression) {
		return new SqlByteArrayExpression1<>(expression);
	}

	// -------------------------------- UNARY OPERATIONS -------------------------------- //

	default ISqlIntegerExpression1<T0> length() {
		return new SqlIntegerExpression1<>(SqlOperations.LENGTH.create(SqlValueTypes.INTEGER, this));
	}

	// -------------------------------- OPERATIONS 1 -------------------------------- //

	default <S0> ISqlBooleanExpression2<T0, S0> isEqual(ISqlExpression1<byte[], S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0> ISqlBooleanExpression2<T0, S0> isNotEqual(ISqlExpression1<byte[], S0> other) {
		return new SqlBooleanExpression2<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 2 -------------------------------- //

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isEqual(ISqlExpression2<byte[], S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1> ISqlBooleanExpression3<T0, S0, S1> isNotEqual(ISqlExpression2<byte[], S0, S1> other) {
		return new SqlBooleanExpression3<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	// -------------------------------- OPERATIONS 3 -------------------------------- //

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isEqual(ISqlExpression3<byte[], S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default <S0, S1, S2> ISqlBooleanExpression4<T0, S0, S1, S2> isNotEqual(ISqlExpression3<byte[], S0, S1, S2> other) {
		return new SqlBooleanExpression4<>(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}

