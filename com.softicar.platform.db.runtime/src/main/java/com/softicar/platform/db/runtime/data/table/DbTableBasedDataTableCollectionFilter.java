package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import java.util.Collection;

public class DbTableBasedDataTableCollectionFilter<R, V> implements IDbTableBasedDataTableFilter<R> {

	private final IDbField<R, V> field;
	private final DataTableCollectionFilterOperator operator;
	private final Collection<? extends V> values;

	public DbTableBasedDataTableCollectionFilter(IDbField<R, V> field, DataTableCollectionFilterOperator operator, Collection<? extends V> values) {

		this.field = field;
		this.operator = operator;
		this.values = values;
	}

	@Override
	public ISqlBooleanExpression<R> getExpression() {

		switch (operator) {
		case IN:
			return field.isIn(values);
		case NOT_IN:
			return field.isNotIn(values);
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}
}
