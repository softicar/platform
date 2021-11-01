package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser2_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T1> {

	protected SqlSelect2_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T2> table) {

		return _join(new SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>(other, expression), table, JoinType.JOIN);
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return joinOnTable1(foreignField);
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T2, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable1(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return joinReverseOnTable1(foreignField);
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T2, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t2().t0(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable1(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t2().t1(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T2> table) {

		return _join(new SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return joinLeftOnTable1(foreignField);
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T2, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable1(ISqlForeignRowField<T1, T2, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t2(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return joinLeftReverseOnTable1(foreignField);
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T2, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t2().t0(foreignField.isEqualToTargetField());
	}

	public <T2> SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable1(ISqlForeignRowField<T2, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t2().t1(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser2_0<Boolean, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> on() {

		return new SqlTableChooser2_0<>(this, PartType.JOIN);
	}

	public SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T0, T1> condition) {

		return on().t0().t1(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser2_0<Boolean, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> where() {

		return new SqlTableChooser2_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser2_0<Object, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> groupBy() {

		return new SqlTableChooser2_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser2_0<Boolean, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> having() {

		return new SqlTableChooser2_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser2_0<Object, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser2_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser2_0<Object, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser2_0<Object, SqlSelect2_8<T0, T1, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

