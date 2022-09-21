package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbPrimitiveField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;

public class DbTableBasedDataTableValueFilter<R, V> implements IDbTableBasedDataTableFilter<R> {

	private final IDbField<R, V> field;
	private final DataTableValueFilterOperator operator;
	private final V value;

	public DbTableBasedDataTableValueFilter(IDbField<R, V> field, DataTableValueFilterOperator operator, V value) {

		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public ISqlBooleanExpression<R> getExpression() {

		switch (operator) {
		case EQUAL:
			return field.isEqual(value);
		case NOT_EQUAL:
			return field.isNotEqual(value);
		case LESS:
			return toPrimitiveFieldOrThrow().isLess(value);
		case LESS_EQUAL:
			return toPrimitiveFieldOrThrow().isLessEqual(value);
		case GREATER:
			return toPrimitiveFieldOrThrow().isGreater(value);
		case GREATER_EQUAL:
			return toPrimitiveFieldOrThrow().isGreaterEqual(value);
		case EMPTY:
			return field.isNull();
		case NOT_EMPTY:
			return field.isNotNull();
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}

	private IDbPrimitiveField<R, V> toPrimitiveFieldOrThrow() {

		return field//
			.cast()
			.toPrimitiveField()
			.orElseThrow(() -> new RuntimeException(String.format("Field '%s' is not a primitive field.", field)));
	}
}
