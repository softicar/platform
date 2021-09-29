package com.softicar.platform.db.sql.statement.builder;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.clause.SqlOrderByClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;

public class SqlDeleteBuilder extends SqlStatementBuilder {

	private SqlWhereClauseBuilder whereBuilder;
	private SqlOrderByClauseBuilder orderByBuilder;

	public SqlDeleteBuilder(ISqlTable<?> table) {

		super(DbConnections.getServerQuirks(), table, null);

		addClauseBuilder(this.whereBuilder = new SqlWhereClauseBuilder(this));
		addClauseBuilder(this.orderByBuilder = new SqlOrderByClauseBuilder(this));

		getHeadClauseBuilder().addText("DELETE FROM %s", table.getFullName().getQuoted());
	}

	public SqlWhereClauseBuilder getWhereBuilder() {

		return whereBuilder;
	}

	public SqlOrderByClauseBuilder getOrderByBuilder() {

		return orderByBuilder;
	}
}
