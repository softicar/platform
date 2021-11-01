package com.softicar.platform.db.sql.statement.builder;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.clause.SqlOrderByClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlUpdateSetClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;

public class SqlUpdateBuilder extends SqlStatementBuilder {

	private SqlUpdateSetClauseBuilder setBuilder;
	private SqlWhereClauseBuilder whereBuilder;
	private SqlOrderByClauseBuilder orderByBuilder;

	public SqlUpdateBuilder(ISqlTable<?> table) {

		super(DbConnections.getServerQuirks(), table, null);

		addClauseBuilder(this.setBuilder = new SqlUpdateSetClauseBuilder(this));
		addClauseBuilder(this.whereBuilder = new SqlWhereClauseBuilder(this));
		addClauseBuilder(this.orderByBuilder = new SqlOrderByClauseBuilder(this));

		getHeadClauseBuilder().addText("UPDATE %s", table.getFullName().getQuoted());
	}

	public SqlUpdateSetClauseBuilder getSetBuilder() {

		return setBuilder;
	}

	public SqlWhereClauseBuilder getWhereBuilder() {

		return whereBuilder;
	}

	public SqlOrderByClauseBuilder getOrderByBuilder() {

		return orderByBuilder;
	}
}
