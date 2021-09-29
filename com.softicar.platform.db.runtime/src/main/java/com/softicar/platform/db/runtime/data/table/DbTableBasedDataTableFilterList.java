package com.softicar.platform.db.runtime.data.table;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilter;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DbTableBasedDataTableFilterList<R> implements IDataTableFilterList<R>, IDbTableBasedDataTableFilter<R> {

	private final DbTableBasedDataTable<R> table;
	private final DataTableFilterListOperator operator;
	private final List<IDbTableBasedDataTableFilter<R>> filters;

	public DbTableBasedDataTableFilterList(DbTableBasedDataTable<R> table, DataTableFilterListOperator operator) {

		this.table = table;
		this.operator = operator;
		this.filters = new ArrayList<>();
	}

	@Override
	public Collection<? extends IDataTableFilter> getFilters() {

		return filters;
	}

	@Override
	public DataTableFilterListOperator getFilterOperator() {

		return operator;
	}

	@Override
	public void clear() {

		filters.clear();
	}

	@Override
	public boolean isEmpty() {

		return filters.isEmpty();
	}

	@Override
	public IDataTableFilterList<R> addFilterList(DataTableFilterListOperator operator) {

		DbTableBasedDataTableFilterList<R> filterList = new DbTableBasedDataTableFilterList<>(table, operator);
		filters.add(filterList);
		return filterList;
	}

	@Override
	public <V> void addValueFilter(IDataTableColumn<R, V> column, DataTableValueFilterOperator operator, V value) {

		IDbField<R, V> field = table.getField(column);
		filters.add(new DbTableBasedDataTableValueFilter<>(field, operator, value));
	}

	@Override
	public void addStringFilter(IDataTableColumn<R, String> column, DataTableStringFilterOperator operator, String value) {

		IDbField<R, String> field = table.getField(column);
		if (field instanceof IDbStringField) {
			filters.add(new DbTableBasedDataTableStringFilter<>((IDbStringField<R>) field, operator, value));
		} else {
			throw new RuntimeException(String.format("String filter not supported for field '%s'.", field));
		}
	}

	@Override
	public <V> void addCollectionFilter(IDataTableColumn<R, V> column, DataTableCollectionFilterOperator operator, Collection<? extends V> values) {

		IDbField<R, V> field = table.getField(column);
		filters.add(new DbTableBasedDataTableCollectionFilter<>(field, operator, values));
	}

	@Override
	public ISqlBooleanExpression<R> getExpression() {

		if (isEmpty()) {
			return new SqlBooleanExpression1<>(Sql.literal(true));
		} else {
			ISqlBooleanExpression<R> expression = filters.get(0).getExpression();
			for (int i = 1; i < filters.size(); i++) {
				if (operator == DataTableFilterListOperator.AND) {
					expression = expression.and(filters.get(i).getExpression());
				} else {
					expression = expression.or(filters.get(i).getExpression());
				}
			}
			return expression;
		}
	}
}
