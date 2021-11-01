package com.softicar.platform.db.runtime.query.builder;

import com.softicar.platform.db.runtime.query.sorters.IDbQuerySorter;
import com.softicar.platform.db.runtime.select.IDbSqlClause;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import java.util.ArrayList;
import java.util.List;

public class DbQueryOrderByApplier {

	private final IDbQuerySqlBuilder builder;
	private final List<IDbQuerySorter> sorters;
	private IDbSqlClause orderByClause;
	private List<ISqlToken> originalTokens;
	private List<Object> originalParameters;

	public DbQueryOrderByApplier(IDbQuerySqlBuilder builder, List<IDbQuerySorter> sorters) {

		this.builder = builder;
		this.sorters = sorters;
	}

	public void apply() {

		if (!sorters.isEmpty()) {
			this.orderByClause = builder.getSelect().getOrderByClause();
			backupOriginalOrderBy();
			clearOrderBy();
			appendSorters();
			appendOriginalOrderBy();
		}
	}

	private void backupOriginalOrderBy() {

		this.originalTokens = new ArrayList<>(orderByClause.getTokens());
		this.originalParameters = new ArrayList<>(orderByClause.getParameters());
	}

	private void clearOrderBy() {

		orderByClause.clear();
	}

	private void appendSorters() {

		for (IDbQuerySorter sorter: sorters) {
			builder.ORDER_BY();
			sorter.buildExpression(builder);
		}
	}

	private void appendOriginalOrderBy() {

		if (!originalTokens.isEmpty()) {
			assert originalTokens.size() >= 2;
			assert originalTokens.get(0).equals(SqlKeyword.ORDER);
			assert originalTokens.get(1).equals(SqlKeyword.BY);

			builder.ORDER_BY();

			originalTokens//
				.subList(2, originalTokens.size())
				.stream()
				.forEach(token -> builder.addToken(token));

			originalParameters//
				.stream()
				.forEach(parameter -> builder.addParameter(parameter));
		}
	}
}
