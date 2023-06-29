package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser1_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> {

	protected SqlSelect1_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T1> table) {

		return _join(new SqlSelect2_8<>(other, expression), table, JoinType.JOIN);
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return joinOnTable0(foreignField);
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t1(foreignField.isEqualToTargetField());
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return joinReverseOnTable0(foreignField);
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t1().t0(foreignField.isEqualToTargetField());
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T1> table) {

		return _join(new SqlSelect2_8<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return joinLeftOnTable0(foreignField);
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T1, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t1(foreignField.isEqualToTargetField());
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return joinLeftReverseOnTable0(foreignField);
	}

	public <T1> SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T1, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t1().t0(foreignField.isEqualToTargetField());
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser1_0<Boolean, SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> where() {

		return new SqlTableChooser1_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser1_0<Object, SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> groupBy() {

		return new SqlTableChooser1_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser1_0<Boolean, SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> having() {

		return new SqlTableChooser1_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser1_0<Object, SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser1_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser1_0<Object, SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser1_0<Object, SqlSelect1_8<T0, V0, V1, V2, V3, V4, V5, V6, V7>, T0> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

