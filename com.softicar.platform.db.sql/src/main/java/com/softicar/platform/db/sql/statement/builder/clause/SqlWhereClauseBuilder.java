package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;

public class SqlWhereClauseBuilder extends SqlClauseBuilder {

	private int whereNesting = 0;

	public SqlWhereClauseBuilder(SqlStatementBuilder statementBuilder) {

		super(statementBuilder);
	}

	public void where(ISqlExpression<Boolean> expression) {

		addText(isEmpty()? " WHERE " : " AND ");
		expression.build(this);
	}

	public void orWhere(ISqlBooleanExpression<?> expression) {

		assert (!isEmpty());
		addText(" OR ");
		expression.build(this);
	}

	public void where(ISqlBooleanExpression<?> expression, boolean doWhere) {

		if (doWhere) {
			where(expression);
		}
	}

	public void orWhere(ISqlBooleanExpression<?> expression, boolean doWhere) {

		if (doWhere) {
			orWhere(expression);
		}
	}

	public void beginWhere(ISqlBooleanExpression<?> expression) {

		addText(isEmpty()? " WHERE (" : "AND (");
		++whereNesting;
		expression.build(this);
	}

	public void endWhere() {

		// decrement nesting counter
		if (whereNesting <= 0) {
			throw new IllegalStateException("Called endWhere function without calling beginWhere function.");
		}
		--whereNesting;
		addText(")");
	}
}
