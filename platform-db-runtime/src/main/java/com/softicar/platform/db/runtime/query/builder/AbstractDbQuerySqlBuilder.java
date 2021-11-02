package com.softicar.platform.db.runtime.query.builder;

import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.query.filters.IDbQueryFilterList;
import com.softicar.platform.db.runtime.query.sorters.IDbQuerySorter;
import com.softicar.platform.db.runtime.select.DbSqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDbQuerySqlBuilder extends DbSqlBuilder implements IDbQuerySqlBuilder {

	private IDbQueryFilterList<?> filters = null;
	private List<IDbQuerySorter> sorters = Collections.emptyList();
	private int limit = 0;
	private int offset = 0;

	// -------------------- ISqmlQueryStatementBuilder -------------------- //

	@Override
	public void setFilters(IDbQueryFilterList<?> filters) {

		this.filters = filters;
	}

	@Override
	public void setSorters(List<IDbQuerySorter> sorters) {

		this.sorters = sorters;
	}

	@Override
	public void setLimit(int limit) {

		this.limit = limit;
	}

	@Override
	public void setOffset(int offset) {

		this.offset = offset;
	}

	@Override
	public IDbSqlSelect buildSelect() {

		buildOriginalSelect();

		overrideHavingClause();
		new DbQueryOrderByApplier(this, sorters).apply();
		overrideLimitClause();

		return getSelect();
	}

	@Override
	public IDbSqlSelect buildCountAllSelect() {

		SELECT();
		addToken(SqlKeyword.COUNT);
		addToken(SqlSymbol.LEFT_PARENTHESIS);
		addToken(SqlSymbol.MULTIPLY);
		addToken(SqlSymbol.RIGHT_PARENTHESIS);

		FROM();
		addToken(SqlSymbol.LEFT_PARENTHESIS);

		startSubSelect();
		buildSelect();
		getSelect().getOrderByClause().clear();
		getSelect().getLimitClause().clear();
		finishSubSelect();

		addToken(SqlSymbol.RIGHT_PARENTHESIS);
		addIdentifier("x");

		return getSelect();
	}

	@Override
	public IDbSqlSelect buildDistinctColumnValuesSelect(IDbQueryColumn<?, ?> column, int limit) {

		SELECT(column);
		addToken(SqlKeyword.DISTINCT);
		if (column.isTable() && !column.isStub()) {
			addSubSelectTableColumns("x", column.getTable(), column.getName());
		} else {
			addIdentifier("x", column.getName());
		}

		FROM();
		addToken(SqlSymbol.LEFT_PARENTHESIS);
		startSubSelect();
		buildSelect();
		finishSubSelect();
		addToken(SqlSymbol.RIGHT_PARENTHESIS);
		addIdentifier("x");

		if (limit > 0) {
			LIMIT();
			addLiteral(limit);
		}

		return getSelect();
	}

	// -------------------- abstract -------------------- //

	protected abstract void buildOriginalSelect();

	// -------------------- private -------------------- //

	private void overrideHavingClause() {

		// TODO: some filters might be applied to WHERE clause to increase performance
		if (filters != null && !filters.isEmpty()) {
			HAVING();
			addToken(SqlSymbol.LEFT_PARENTHESIS);
			filters.buildCondition(this);
			addToken(SqlSymbol.RIGHT_PARENTHESIS);
		}
	}

	private void overrideLimitClause() {

		// check parameters
		if (offset > 0 && limit <= 0) {
			throw new IllegalArgumentException("Cannot use OFFSET without LIMIT.");
		}

		if (limit > 0) {
			// reset original LIMIT
			getSelect().getLimitClause().clear();

			// add LIMIT
			LIMIT();
			addLiteral(limit);

			if (offset > 0) {
				// add OFFSET
				addToken(SqlKeyword.OFFSET);
				addLiteral(offset);
			}
		}
	}
}
