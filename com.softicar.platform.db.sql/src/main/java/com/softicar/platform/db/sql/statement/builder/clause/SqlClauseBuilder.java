package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.core.DbStatements;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Basic implementation of {@link ISqlClauseBuilder}.
 *
 * @author Oliver Richers
 */
public class SqlClauseBuilder implements ISqlClauseBuilder {

	private final SqlStatementBuilder statementBuilder;
	private final IDbServerQuirks serverQuirks;
	private final List<Object> parameters = new ArrayList<>();
	private StringBuilder textBuilder = null;

	public SqlClauseBuilder(SqlStatementBuilder statementBuilder) {

		this.statementBuilder = statementBuilder;
		this.serverQuirks = statementBuilder.getServerQuirks();
	}

	public boolean isEmpty() {

		return textBuilder == null;
	}

	public boolean endsWith(char character) {

		if (textBuilder != null && textBuilder.length() > 0) {
			return textBuilder.charAt(textBuilder.length() - 1) == character;
		} else {
			return false;
		}
	}

	@Override
	public SqlClauseBuilder fixTable() {

		// no need for this
		return this;
	}

	@Override
	public SqlClauseBuilder unfixTable() {

		// no need for this
		return this;
	}

	@Override
	public SqlClauseBuilder addText(String text, Object...args) {

		return addText(String.format(text, args));
	}

	@Override
	public SqlClauseBuilder addText(String text) {

		if (textBuilder == null) {
			textBuilder = new StringBuilder();
		}
		textBuilder.append(text);
		return this;
	}

	@Override
	public SqlClauseBuilder addParameter(Object parameter) {

		addToParameterList(parameter);
		return addText("?");
	}

	@Override
	public SqlClauseBuilder addParameterPack(Iterable<?> parameters) {

		for (Object parameter: parameters) {
			addToParameterList(parameter);
		}
		return addText(DbStatements.createQuestionMarkList(parameters));
	}

	@Override
	public SqlClauseBuilder addField(ISqlField<?, ?> field) {

		// prepend alias if not null
		String alias = statementBuilder.getAlias();
		if (alias != null) {
			addText(alias).addText(".");
		}

		return addText(getQuotedName(field));
	}

	@Override
	public String getQuotedName(ISqlField<?, ?> field) {

		return serverQuirks.getQuotedIdentifier(field.getName());
	}

	public void extractText(StringBuilder textBuilder) {

		if (this.textBuilder != null) {
			textBuilder.append(this.textBuilder);
		}
	}

	public String getText() {

		return textBuilder != null? textBuilder.toString() : "";
	}

	public void extractParameters(Collection<Object> parameters) {

		if (this.parameters != null) {
			parameters.addAll(this.parameters);
		}
	}

	public List<Object> getParameters() {

		return parameters;
	}

	private void addToParameterList(Object parameter) {

		SqlClauseParameters.addToParameterList(this.parameters, parameter);
	}
}
