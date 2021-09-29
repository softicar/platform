package com.softicar.platform.db.sql.statement;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import java.util.Optional;
import java.util.function.Supplier;

public interface ISqlConditionalStatement<R, This> {

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a conjunction,
	 * that is, the boolean <i>AND</i> operator is used.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This where(ISqlBooleanExpression0 expression);

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a conjunction,
	 * that is, the boolean <i>AND</i> operator is used.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This where(ISqlBooleanExpression<R> expression);

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement if the specified predicate is true.
	 *
	 * @param predicate
	 *            the predicate controlling if the expression is added or not
	 * @param expressionSupplier
	 *            the expression supplier
	 * @return this statement
	 */
	This whereIf(boolean predicate, Supplier<? extends ISqlBooleanExpression<R>> expressionSupplier);

	/**
	 * Adds the given optional conditional expression to the WHERE clause of
	 * this statement.
	 *
	 * @param optionalExpression
	 *            the optional expression
	 * @return this statement
	 */
	This where(Optional<? extends ISqlBooleanExpression<R>> optionalExpression);

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a conjunction,
	 * that is, the boolean <i>AND</i> operator is used.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This where(ISqlBooleanExpression2<R, R> expression);

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a conjunction,
	 * that is, the boolean <i>AND</i> operator is used.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This where(ISqlBooleanExpression3<R, R, R> expression);

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a conjunction,
	 * that is, the boolean <i>AND</i> operator is used.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This where(ISqlBooleanExpression4<R, R, R, R> expression);

	/**
	 * Adds the given conditional expression to the WHERE clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a disjunction,
	 * that is, the boolean <i>OR</i> operator is used. This method should
	 * usually be used together with {@link #beginWhere} and {@link #endWhere}
	 * to ensure proper setting of parentheses.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This orWhere(ISqlBooleanExpression<R> expression);

	/**
	 * Opens a parenthesis and adds the given conditional expression to the
	 * WHERE clause of this statement.
	 * <p>
	 * This method is useful to start a new disjunctive expression.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	This beginWhere(ISqlBooleanExpression<R> expression);

	/**
	 * Adds a closing parenthesis to the WHERE clause of this statement.
	 */
	This endWhere();
}
