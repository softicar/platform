package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;

public class DbTableBasedDataTableStringFilter<R> implements IDbTableBasedDataTableFilter<R> {

	private final IDbStringField<R> field;
	private final DataTableStringFilterOperator operator;
	private final String string;

	public DbTableBasedDataTableStringFilter(IDbStringField<R> field, DataTableStringFilterOperator operator, String string) {

		this.field = field;
		this.operator = operator;
		this.string = string;
	}

	@Override
	public ISqlBooleanExpression<R> getExpression() {

		switch (operator) {
		case LIKE:
			return field.isLike(string);
		case NOT_LIKE:
			return field.isNotLike(string);
		case REGEXP:
			return field.isRegexp(string);
		case NOT_REGEXP:
			return field.isNotRegexp(string);
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}
}
