package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;
import com.softicar.platform.db.sql.statement.SqlSelectLock;

public interface ISqlSelectExtension<Select> {

	Select getThis();

	SqlSelectCore getCore();

	default Select setLock(SqlSelectLock lock) {

		getCore().setLock(lock);
		return getThis();
	}

	default <V> Select groupBy(ISqlExpression0<V> expression) {

		getCore().setPartType(PartType.GROUP_BY);
		getCore().addLiteralExpression(expression);
		return getThis();
	}

	default <V> Select orderBy(ISqlExpression0<V> expression) {

		getCore().setPartType(PartType.ORDER_BY);
		getCore().setOrderDirection(OrderDirection.ASCENDING);
		getCore().addLiteralExpression(expression);
		return getThis();
	}

	default <V> Select orderByDescending(ISqlExpression0<V> expression) {

		getCore().setPartType(PartType.ORDER_BY);
		getCore().setOrderDirection(OrderDirection.DESCENDING);
		getCore().addLiteralExpression(expression);
		return getThis();
	}
}
