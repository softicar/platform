package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.sql.statement.builder.clause.SqlClauseParameters;
import com.softicar.platform.db.sql.token.ISqlToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class DbSqlClause implements IDbSqlClause {

	private final List<ISqlToken> tokens;
	private final List<Object> parameters;

	public DbSqlClause() {

		this.tokens = new ArrayList<>();
		this.parameters = new ArrayList<>();
	}

	@Override
	public List<ISqlToken> getTokens() {

		return Collections.unmodifiableList(tokens);
	}

	@Override
	public List<Object> getParameters() {

		return Collections.unmodifiableList(parameters);
	}

	@Override
	public void clear() {

		tokens.clear();
		parameters.clear();
	}

	public void addToken(ISqlToken token) {

		tokens.add(token);
	}

	public void addTokens(Collection<ISqlToken> tokens) {

		this.tokens.addAll(tokens);
	}

	public void addParameter(Object parameter) {

		SqlClauseParameters.addToParameterList(parameters, parameter);
	}

	public void addParameters(Collection<Object> parameters) {

		this.parameters.addAll(parameters);
	}

	public boolean isEmpty() {

		return tokens.isEmpty();
	}
}
