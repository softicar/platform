package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser4_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T3> {

	protected SqlSelect4_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T4> table) {

		return _join(new SqlSelect5_8<>(other, expression), table, JoinType.JOIN);
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return joinOnTable3(foreignField);
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable1(ISqlForeignRowField<T1, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable2(ISqlForeignRowField<T2, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable3(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return joinReverseOnTable3(foreignField);
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T4, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t0(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable1(ISqlForeignRowField<T4, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t1(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable2(ISqlForeignRowField<T4, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t2(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable3(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t3(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T4> table) {

		return _join(new SqlSelect5_8<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return joinLeftOnTable3(foreignField);
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable1(ISqlForeignRowField<T1, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable2(ISqlForeignRowField<T2, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable3(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return joinLeftReverseOnTable3(foreignField);
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T4, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t0(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable1(ISqlForeignRowField<T4, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t1(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable2(ISqlForeignRowField<T4, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t2(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable3(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t3(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser4_0<Boolean, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> on() {

		return new SqlTableChooser4_0<>(this, PartType.JOIN);
	}

	public SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T2, T3> condition) {

		return on().t2().t3(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser4_0<Boolean, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> where() {

		return new SqlTableChooser4_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser4_0<Object, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> groupBy() {

		return new SqlTableChooser4_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser4_0<Boolean, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> having() {

		return new SqlTableChooser4_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser4_0<Object, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser4_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser4_0<Object, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser4_0<Object, SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

