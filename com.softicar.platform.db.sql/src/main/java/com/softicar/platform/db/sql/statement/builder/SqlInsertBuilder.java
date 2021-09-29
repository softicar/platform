package com.softicar.platform.db.sql.statement.builder;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.clause.SqlInsertIntoClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlInsertValuesClauseBuilder;

public class SqlInsertBuilder extends SqlStatementBuilder {

	private SqlInsertIntoClauseBuilder lhsBuilder;
	private SqlInsertValuesClauseBuilder rhsBuilder;

	public SqlInsertBuilder(ISqlTable<?> table) {

		super(DbConnections.getServerQuirks(), table, null);

		addClauseBuilder(this.lhsBuilder = new SqlInsertIntoClauseBuilder(this));
		addClauseBuilder(this.rhsBuilder = new SqlInsertValuesClauseBuilder(this));

		getHeadClauseBuilder().addText("INSERT INTO %s", table.getFullName().getQuoted());
	}

	public int getRowCount() {

		return rhsBuilder.getRowCount();
	}

	public <R, V> void addValue(ISqlField<R, V> field, V value) {

		lhsBuilder.addFieldToList(field);
		rhsBuilder.addValueToList(value);
	}

	public void finishTableRow() {

		lhsBuilder.finishFieldList();
		rhsBuilder.finishValueList();
	}
}
