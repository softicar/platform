package com.softicar.platform.db.runtime.query.filters;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilter;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DbQueryFilterList<R> implements IDbQueryFilterList<R> {

	private final List<IDbQueryFilter> filters;
	private final DataTableFilterListOperator filterOperator;

	public DbQueryFilterList(DataTableFilterListOperator operator) {

		this.filters = new ArrayList<>();
		this.filterOperator = operator;
	}

	@Override
	public Collection<? extends IDataTableFilter> getFilters() {

		return Collections.unmodifiableCollection(filters);
	}

	@Override
	public DataTableFilterListOperator getFilterOperator() {

		return filterOperator;
	}

	@Override
	public boolean isEmpty() {

		return filters.size() == 0;
	}

	@Override
	public void clear() {

		filters.clear();
	}

	@Override
	public IDataTableFilterList<R> addFilterList(DataTableFilterListOperator operator) {

		DbQueryFilterList<R> filterList = new DbQueryFilterList<>(operator);
		filters.add(filterList);
		return filterList;
	}

	@Override
	public <V> void addValueFilter(IDataTableColumn<R, V> column, DataTableValueFilterOperator operator, V value) {

		IDbQueryColumn<?, V> queryColumn = (IDbQueryColumn<?, V>) column;
		filters.add(new DbQueryValueFilter<>(queryColumn, operator, value));
	}

	@Override
	public <V> void addCollectionFilter(IDataTableColumn<R, V> column, DataTableCollectionFilterOperator operator, Collection<? extends V> values) {

		IDbQueryColumn<?, V> queryColumn = (IDbQueryColumn<?, V>) column;
		filters.add(new DbQueryCollectionFilter<>(queryColumn, operator, values));
	}

	@Override
	public void addStringFilter(IDataTableColumn<R, String> column, DataTableStringFilterOperator operator, String value) {

		IDbQueryColumn<?, String> queryColumn = (IDbQueryColumn<?, String>) column;
		filters.add(new DbQueryStringFilter(queryColumn, operator, value));
	}

	@Override
	public void buildCondition(IDbSqlBuilder builder) {

		if (filters.size() > 1) {
			builder.addToken(SqlSymbol.LEFT_PARENTHESIS);
			buildSubfilters(builder);
			builder.addToken(SqlSymbol.RIGHT_PARENTHESIS);
		} else if (filters.size() == 1) {
			buildSubfilters(builder);
		} else {
			throw new IllegalStateException("No filters added to filter list.");
		}
	}

	private void buildSubfilters(IDbSqlBuilder builder) {

		boolean first = true;
		for (IDbQueryFilter filter: filters) {
			if (first) {
				first = false;
			} else {
				builder.addToken(getOperatorToken(filterOperator));
			}
			filter.buildCondition(builder);
		}
	}

	private ISqlToken getOperatorToken(DataTableFilterListOperator operator) {

		switch (operator) {
		case AND:
			return SqlKeyword.AND;
		case OR:
			return SqlKeyword.OR;
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}
}
