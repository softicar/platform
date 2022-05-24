package com.softicar.platform.db.runtime.query.filters;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.List;

public class DbQueryValueFilter<V> implements IDbQueryFilter {

	private final IDbQueryColumn<?, V> column;
	private final DataTableValueFilterOperator operator;
	private final V value;

	public DbQueryValueFilter(IDbQueryColumn<?, V> column, DataTableValueFilterOperator operator, V value) {

		this.column = column;
		this.operator = operator;
		this.value = value;
	}

	@Override
	public void buildCondition(IDbSqlBuilder builder) {

		if (column.isTable() && !column.isStub()) {
			List<IDbField<V, ?>> fields = column.getTable().getPrimaryKey().getFields();
			if (fields.size() == 1) {
				builder.addIdentifier(column.getName() + "$" + fields.get(0).getName());
			} else {
				throw new SofticarDeveloperException("Filtering not supported for foreign keys with more than one column.");
			}
		} else {
			builder.addIdentifier(column.getName());
		}
		if (operator.isEmpty() || operator.isNotEmpty()) {
			builder.addToken(SqlKeyword.IS);
			if (operator.isNotEmpty()) {
				builder.addToken(SqlKeyword.NOT);
			}
			builder.addToken(SqlKeyword.NULL);
		} else {
			builder.addToken(getOperatorToken());
			builder.addParameter(value);
		}
	}

	private ISqlToken getOperatorToken() {

		switch (operator) {
		case EQUAL:
			return SqlSymbol.EQUAL;
		case NOT_EQUAL:
			return SqlSymbol.NOT_EQUAL;
		case GREATER:
			return SqlSymbol.GREATER;
		case GREATER_EQUAL:
			return SqlSymbol.GREATER_EQUAL;
		case LESS:
			return SqlSymbol.LESS;
		case LESS_EQUAL:
			return SqlSymbol.LESS_EQUAL;
		case EMPTY:
		case NOT_EMPTY:
			throw new UnsupportedOperationException();
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}
}
