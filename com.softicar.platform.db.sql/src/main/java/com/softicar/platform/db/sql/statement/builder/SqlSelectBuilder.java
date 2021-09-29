package com.softicar.platform.db.sql.statement.builder;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.clause.SqlClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlGroupByClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlOrderByClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;
import java.util.List;

public class SqlSelectBuilder extends SqlStatementBuilder {

	private SqlWhereClauseBuilder whereBuilder;
	private SqlGroupByClauseBuilder groupByBuilder;
	private SqlOrderByClauseBuilder orderByBuilder;
	private final String selectPrefix;

	public SqlSelectBuilder(ISqlTable<?> table) {

		this(table, table.getAllFields());
	}

	public SqlSelectBuilder(ISqlTable<?> table, List<? extends ISqlField<?, ?>> fields) {

		super(DbConnections.getServerQuirks(), table, "t");

		addClauseBuilder(this.whereBuilder = new SqlWhereClauseBuilder(this));
		addClauseBuilder(this.groupByBuilder = new SqlGroupByClauseBuilder(this));
		addClauseBuilder(this.orderByBuilder = new SqlOrderByClauseBuilder(this));

		// create select prefix
		SqlClauseBuilder tmp = new SqlClauseBuilder(this);
		tmp.addText("SELECT ");
		int n = fields.size();
		for (int i = 0; i < n; ++i) {
			if (i > 0) {
				tmp.addText(", ");
			}
			tmp.addField(fields.get(i));
		}
		this.selectPrefix = tmp.getText();

		// create head
		getHeadClauseBuilder().addText(" FROM %s %s", table.getFullName().getQuoted(), getAlias());
	}

	public StringBuilder getSelectText() {

		return super.getText().insert(0, selectPrefix);
	}

	public StringBuilder getCountText() {

		if (groupByBuilder.isEmpty()) {
			return super.getText().insert(0, "SELECT COUNT(*)");
		} else {
			return super.getText()//
				.insert(0, "SELECT COUNT(*) FROM (SELECT 1")
				.append(") s");
		}
	}

	public SqlWhereClauseBuilder getWhereBuilder() {

		return whereBuilder;
	}

	public SqlOrderByClauseBuilder getOrderByBuilder() {

		return orderByBuilder;
	}

	public SqlGroupByClauseBuilder getGroupByBuilder() {

		return groupByBuilder;
	}
}
