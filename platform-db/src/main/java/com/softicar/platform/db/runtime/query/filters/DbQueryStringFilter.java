package com.softicar.platform.db.runtime.query.filters;

import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.runtime.select.IDbSqlBuilder;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.Arrays;
import java.util.List;

public class DbQueryStringFilter implements IDbQueryFilter {

	private final IDbQueryColumn<?, String> column;
	private final DataTableStringFilterOperator operator;
	private final String string;

	public DbQueryStringFilter(IDbQueryColumn<?, String> column, DataTableStringFilterOperator operator, String string) {

		this.column = column;
		this.operator = operator;
		this.string = string;
	}

	@Override
	public void buildCondition(IDbSqlBuilder builder) {

		if (column.getValueClass() == String.class) {
			builder.addIdentifier(column.getName());
		} else {
			// need CAST to string
			builder.addToken(SqlKeyword.CAST);
			builder.addToken(SqlSymbol.LEFT_PARENTHESIS);
			builder.addIdentifier(column.getName());
			builder.addToken(SqlKeyword.AS);
			builder.addToken(SqlKeyword.CHAR);
			builder.addToken(SqlSymbol.RIGHT_PARENTHESIS);
		}

		for (ISqlToken token: getOperatorTokens()) {
			builder.addToken(token);
		}

		builder.addParameter(string);
	}

	private List<ISqlToken> getOperatorTokens() {

		switch (operator) {
		case LIKE:
			return Arrays.asList(SqlKeyword.LIKE);
		case NOT_LIKE:
			return Arrays.asList(SqlKeyword.NOT, SqlKeyword.LIKE);
		case REGEXP:
			return Arrays.asList(SqlKeyword.REGEXP);
		case NOT_REGEXP:
			return Arrays.asList(SqlKeyword.NOT, SqlKeyword.REGEXP);
		}
		throw new SofticarUnknownEnumConstantException(operator);
	}
}
