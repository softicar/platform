package com.softicar.platform.db.sql.statement.builder;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.clause.SqlClauseBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlHeadClauseBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SqlStatementBuilder {

	private final IDbServerQuirks serverQuirks;
	private final List<SqlClauseBuilder> clauseBuilders;
	private final SqlHeadClauseBuilder headClauseBuilder;
	private String alias;

	public SqlStatementBuilder(IDbServerQuirks serverQuirks, ISqlTable<?> table, String alias) {

		this.serverQuirks = serverQuirks;
		this.alias = alias;
		this.headClauseBuilder = new SqlHeadClauseBuilder(this, table);
		this.clauseBuilders = new ArrayList<>();

		addClauseBuilder(headClauseBuilder);
	}

	public IDbServerQuirks getServerQuirks() {

		return serverQuirks;
	}

	public Set<ISqlTable<?>> getTables() {

		return headClauseBuilder.getTables();
	}

	public void setAlias(String alias) {

		this.alias = alias;
	}

	public String getAlias() {

		return alias;
	}

	public SqlHeadClauseBuilder getHeadClauseBuilder() {

		return headClauseBuilder;
	}

	public StringBuilder getText() {

		StringBuilder textBuilder = new StringBuilder();
		for (SqlClauseBuilder clauseBuilder: clauseBuilders) {
			clauseBuilder.extractText(textBuilder);
		}
		return textBuilder;
	}

	public Collection<Object> getParameters() {

		ArrayList<Object> parameters = new ArrayList<>();
		for (SqlClauseBuilder clauseBuilder: clauseBuilders) {
			clauseBuilder.extractParameters(parameters);
		}
		return parameters;
	}

	protected void addClauseBuilder(SqlClauseBuilder clauseBuilder) {

		clauseBuilders.add(clauseBuilder);
	}
}
