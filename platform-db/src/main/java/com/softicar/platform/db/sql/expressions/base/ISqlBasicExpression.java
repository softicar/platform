package com.softicar.platform.db.sql.expressions.base;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.helper.SqlLiteralExpression;
import com.softicar.platform.db.sql.operations.SqlIsIn;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;
import java.util.Arrays;

public interface ISqlBasicExpression<BOOL, VALUE> extends ISqlExpression<VALUE> {

	BOOL wrapBool(ISqlExpression<Boolean> expression);

	default ISqlExpression0<VALUE> literal(VALUE value) {

		return SqlLiteralExpression.create(getValueType(), value);
	}

	default BOOL isNull() {

		return wrapBool(SqlOperations.IS_NULL.create(SqlValueTypes.BOOLEAN, this));
	}

	default BOOL isNotNull() {

		return wrapBool(SqlOperations.IS_NOT_NULL.create(SqlValueTypes.BOOLEAN, this));
	}

	@SuppressWarnings("unchecked")
	default BOOL isIn(VALUE...values) {

		return isIn(Arrays.asList(values));
	}

	@SuppressWarnings("unchecked")
	default BOOL isNotIn(VALUE...values) {

		return isNotIn(Arrays.asList(values));
	}

	default BOOL isIn(Iterable<? extends VALUE> values) {

		return wrapBool(SqlIsIn.Type.IS_IN.get(this, values));
	}

	default BOOL isNotIn(Iterable<? extends VALUE> values) {

		return wrapBool(SqlIsIn.Type.IS_NOT_IN.get(this, values));
	}

	default BOOL isEqual(VALUE other) {

		return other != null? isEqual(literal(other)) : isNull();
	}

	default BOOL isNotEqual(VALUE other) {

		return other != null? isNotEqual(literal(other)) : isNotNull();
	}

	default BOOL isEqual(ISqlExpression0<VALUE> other) {

		return wrapBool(SqlOperations.IS_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}

	default BOOL isNotEqual(ISqlExpression0<VALUE> other) {

		return wrapBool(SqlOperations.IS_NOT_EQUAL.create(SqlValueTypes.BOOLEAN, this, other));
	}
}
