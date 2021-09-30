package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser5_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T4> {

	protected SqlSelect5_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T5> table) {

		return _join(new SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>(other, expression), table, JoinType.JOIN);
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return joinOnTable4(foreignField);
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable1(ISqlForeignRowField<T1, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable2(ISqlForeignRowField<T2, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable3(ISqlForeignRowField<T3, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable4(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t4().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return joinReverseOnTable4(foreignField);
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T5, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t0(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable1(ISqlForeignRowField<T5, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t1(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable2(ISqlForeignRowField<T5, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t2(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable3(ISqlForeignRowField<T5, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t3(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable4(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return join(foreignField.getTable()).on().t5().t4(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T5> table) {

		return _join(new SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return joinLeftOnTable4(foreignField);
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable1(ISqlForeignRowField<T1, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable2(ISqlForeignRowField<T2, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable3(ISqlForeignRowField<T3, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable4(ISqlForeignRowField<T4, T5, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t4().t5(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return joinLeftReverseOnTable4(foreignField);
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T5, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t0(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable1(ISqlForeignRowField<T5, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t1(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable2(ISqlForeignRowField<T5, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t2(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable3(ISqlForeignRowField<T5, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t3(foreignField.isEqualToTargetField());
	}

	public <T5> SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable4(ISqlForeignRowField<T5, T4, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t5().t4(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser5_0<Boolean, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> on() {

		return new SqlTableChooser5_0<>(this, PartType.JOIN);
	}

	public SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T3, T4> condition) {

		return on().t3().t4(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser5_0<Boolean, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> where() {

		return new SqlTableChooser5_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser5_0<Object, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> groupBy() {

		return new SqlTableChooser5_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser5_0<Boolean, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> having() {

		return new SqlTableChooser5_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser5_0<Object, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser5_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser5_0<Object, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser5_0<Object, SqlSelect5_8<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

