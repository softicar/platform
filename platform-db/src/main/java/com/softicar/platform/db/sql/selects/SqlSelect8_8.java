package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.choosers.SqlTableChooser8_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T7> {

	protected SqlSelect8_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser8_0<Boolean, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> on() {

		return new SqlTableChooser8_0<>(this, PartType.JOIN);
	}

	public SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T6, T7> condition) {

		return on().t6().t7(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser8_0<Boolean, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> where() {

		return new SqlTableChooser8_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser8_0<Object, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> groupBy() {

		return new SqlTableChooser8_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser8_0<Boolean, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> having() {

		return new SqlTableChooser8_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser8_0<Object, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser8_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser8_0<Object, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser8_0<Object, SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6, T7> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

