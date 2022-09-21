package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;
import java.util.function.Supplier;

public interface ISqlSelectLastTableExtension<Select, LastTable> extends ISqlSelectExtension<Select> {

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
	default Select where(ISqlExpression1<Boolean, LastTable> expression) {

		getCore().addExpressionOnLastTable(PartType.WHERE, expression);
		return getThis();
	}

	/**
	 * Conditionally calls the {@link #where(ISqlExpression1)} method.
	 * <p>
	 * If and only if the given predicate is <i>true</i>, the expression
	 * supplier is used to create the boolean expression, and the
	 * {@link #where(ISqlExpression1)} method is called with it.
	 *
	 * @param predicate
	 *            whether to add the expression or not
	 * @param expressionSupplier
	 *            a {@link Supplier} for the conditional expression
	 * @return this statement
	 */
	default Select whereIf(boolean predicate, Supplier<? extends ISqlExpression1<Boolean, LastTable>> expressionSupplier) {

		if (predicate) {
			where(expressionSupplier.get());
		}
		return getThis();
	}

	default Select groupBy(ISqlExpression1<?, LastTable> expression) {

		getCore().addExpressionOnLastTable(PartType.GROUP_BY, expression);
		return getThis();
	}

	/**
	 * Adds the given conditional expression to the HAVING clause of this
	 * statement.
	 * <p>
	 * All expressions added by this method are combined into a conjunction,
	 * that is, the boolean <i>and</i> operator is used.
	 *
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	default Select having(ISqlExpression1<Boolean, LastTable> expression) {

		getCore().addExpressionOnLastTable(PartType.HAVING, expression);
		return getThis();
	}

	/**
	 * Adds the given conditional expression to the HAVING clause of this
	 * statement.
	 * <p>
	 * The method works like {@link #having(ISqlExpression1)} but adds the given
	 * expression only of the given predicate is <i>true</i>.
	 *
	 * @param predicate
	 *            whether to add the expression or not
	 * @param expression
	 *            the conditional expression
	 * @return this statement
	 */
	default Select havingIf(boolean predicate, ISqlExpression1<Boolean, LastTable> expression) {

		if (predicate) {
			having(expression);
		}
		return getThis();
	}

	default Select orderBy(ISqlExpression1<?, LastTable> expression) {

		getCore().setOrderDirection(OrderDirection.ASCENDING);
		getCore().addExpressionOnLastTable(PartType.ORDER_BY, expression);
		return getThis();
	}

	default Select orderByDescending(ISqlExpression1<?, LastTable> expression) {

		getCore().setOrderDirection(OrderDirection.DESCENDING);
		getCore().addExpressionOnLastTable(PartType.ORDER_BY, expression);
		return getThis();
	}
}
