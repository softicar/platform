package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.statement.ISqlConditionalStatement;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractSqlConditionalStatement<R, This> implements ISqlConditionalStatement<R, This> {

	@Override
	public final This where(ISqlBooleanExpression0 expression) {

		getWhereBuilder().where(expression);
		return getThis();
	}

	@Override
	public final This where(ISqlBooleanExpression<R> expression) {

		getWhereBuilder().where(expression);
		return getThis();
	}

	@Override
	public This whereIf(boolean predicate, Supplier<? extends ISqlBooleanExpression<R>> expressionSupplier) {

		if (predicate) {
			where(expressionSupplier.get());
		}
		return getThis();
	}

	@Override
	public This where(Optional<? extends ISqlBooleanExpression<R>> optionalExpression) {

		if (optionalExpression.isPresent()) {
			where(optionalExpression.get());
		}
		return getThis();
	}

	@Override
	public final This where(ISqlBooleanExpression2<R, R> expression) {

		getWhereBuilder().where(expression);
		return getThis();
	}

	@Override
	public final This where(ISqlBooleanExpression3<R, R, R> expression) {

		getWhereBuilder().where(expression);
		return getThis();
	}

	@Override
	public final This where(ISqlBooleanExpression4<R, R, R, R> expression) {

		getWhereBuilder().where(expression);
		return getThis();
	}

	@Override
	public final This orWhere(ISqlBooleanExpression<R> expression) {

		getWhereBuilder().orWhere(expression);
		return getThis();
	}

	@Override
	public final This beginWhere(ISqlBooleanExpression<R> expression) {

		getWhereBuilder().beginWhere(expression);
		return getThis();
	}

	@Override
	public final This endWhere() {

		getWhereBuilder().endWhere();
		return getThis();
	}

	protected abstract This getThis();

	protected abstract SqlWhereClauseBuilder getWhereBuilder();
}
