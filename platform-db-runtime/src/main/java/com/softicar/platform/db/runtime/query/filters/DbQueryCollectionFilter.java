package com.softicar.platform.db.runtime.query.filters;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.Collection;
import java.util.List;

public class DbQueryCollectionFilter<V> implements IDbQueryFilter {

	private final IDbQueryColumn<?, V> column;
	private final DataTableCollectionFilterOperator operator;
	private final Collection<? extends V> values;

	public DbQueryCollectionFilter(IDbQueryColumn<?, V> column, DataTableCollectionFilterOperator operator, Collection<? extends V> values) {

		this.column = column;
		this.operator = operator;
		this.values = values;
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
		switch (operator) {
		case IN:
			builder.addToken(SqlKeyword.IN);
			break;
		case NOT_IN:
			builder.addToken(SqlKeyword.NOT);
			builder.addToken(SqlKeyword.IN);
			break;
		}
		builder.addToken(SqlSymbol.LEFT_PARENTHESIS);
		builder.addParameters(values);
		builder.addToken(SqlSymbol.RIGHT_PARENTHESIS);
	}
}
