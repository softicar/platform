package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.sql.ISqlTableRow;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.List;

public class DbSqlFormatter {

	private final List<ISqlToken> tokens;
	private final List<Object> parameters;
	private boolean expandParameters;
	private int parameterIndex;

	public DbSqlFormatter(IDbSqlSelect select) {

		this(select.getTokens(), select.getParameters());
	}

	public DbSqlFormatter(List<ISqlToken> tokens, List<Object> parameters) {

		this.tokens = tokens;
		this.parameters = parameters;
		this.expandParameters = false;
	}

	public DbSqlFormatter setExpandParameters(boolean expandParameters) {

		this.expandParameters = expandParameters;
		return this;
	}

	public String format() {

		this.parameterIndex = 0;

		StringBuilder builder = new StringBuilder();
		ISqlToken previousToken = null;
		for (ISqlToken token: this.tokens) {
			String delimiter = getDelimiter(previousToken, token);
			builder.append(delimiter).append(getText(token));
			previousToken = token;
		}
		return builder.toString();
	}

	private String getText(ISqlToken token) {

		if (this.expandParameters && token.toString().equals("?")) {
			Object parameter = this.parameters.get(this.parameterIndex);
			String text = getParameterText(parameter);
			this.parameterIndex++;
			return text;
		} else {
			return token.toString();
		}
	}

	private String getParameterText(Object parameter) {

		if (parameter == null) {
			return "NULL";
		} else if (parameter instanceof Boolean) {
			return (Boolean) parameter? "TRUE" : "FALSE";
		} else if (parameter instanceof Number) {
			return parameter.toString();
		} else if (parameter instanceof ISqlTableRow) {
			return "" + ((ISqlTableRow<?, ?>) parameter).pk();
		} else {
			return "'" + parameter.toString() + "'";
		}
	}

	private String getDelimiter(ISqlToken token1, ISqlToken token2) {

		// token-wise checks
		if (token1 == null || token2 == null) {
			return "";
		}
		if (token1 == SqlSymbol.DOT || token2 == SqlSymbol.DOT) {
			return "";
		}
		if (token2 == SqlSymbol.COMMA) {
			return "";
		}
		if (token1 == SqlSymbol.LEFT_PARENTHESIS || token2 == SqlSymbol.RIGHT_PARENTHESIS) {
			return "";
		}
		if (!isSymbol(token1) && !isKeyword(token1) && token2 == SqlSymbol.LEFT_PARENTHESIS) {
			return "";
		}

		// textual checks
		if (token1.toString().endsWith("(") || token2.toString().startsWith(")")) {
			return "";
		}
		if (token1.toString().endsWith(".") || token2.toString().startsWith(".")) {
			return "";
		}
		if (token2.toString().startsWith(",")) {
			return "";
		}

		return " ";
	}

	private boolean isSymbol(ISqlToken token) {

		return token instanceof SqlSymbol;
	}

	private boolean isKeyword(ISqlToken token) {

		if (token instanceof SqlKeyword) {
			SqlKeyword keyword = (SqlKeyword) token;
			return !keyword.isFunction() && !keyword.isType();
		} else {
			return false;
		}
	}
}
