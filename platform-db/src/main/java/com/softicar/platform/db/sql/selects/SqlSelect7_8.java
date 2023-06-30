package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser7_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T6> {

	protected SqlSelect7_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T7> table) {

		return _join(new SqlSelect8_8<>(other, expression), table, JoinType.JOIN);
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T6, T7, ?> foreignField) {

		return joinOnTable6(foreignField);
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable1(ISqlForeignRowField<T1, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable2(ISqlForeignRowField<T2, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable3(ISqlForeignRowField<T3, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable4(ISqlForeignRowField<T4, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t4().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable5(ISqlForeignRowField<T5, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t5().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable6(ISqlForeignRowField<T6, T7, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t6().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T7, T6, ?> foreignField) {

		return joinReverseOnTable6(foreignField);
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T7, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t0(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable1(ISqlForeignRowField<T7, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t1(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable2(ISqlForeignRowField<T7, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t2(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable3(ISqlForeignRowField<T7, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t3(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable4(ISqlForeignRowField<T7, T4, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t4(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable5(ISqlForeignRowField<T7, T5, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t5(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable6(ISqlForeignRowField<T7, T6, ?> foreignField) {

		return join(foreignField.getTable()).on().t7().t6(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T7> table) {

		return _join(new SqlSelect8_8<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T6, T7, ?> foreignField) {

		return joinLeftOnTable6(foreignField);
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable1(ISqlForeignRowField<T1, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable2(ISqlForeignRowField<T2, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable3(ISqlForeignRowField<T3, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable4(ISqlForeignRowField<T4, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t4().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable5(ISqlForeignRowField<T5, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t5().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable6(ISqlForeignRowField<T6, T7, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t6().t7(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T7, T6, ?> foreignField) {

		return joinLeftReverseOnTable6(foreignField);
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T7, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t0(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable1(ISqlForeignRowField<T7, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t1(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable2(ISqlForeignRowField<T7, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t2(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable3(ISqlForeignRowField<T7, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t3(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable4(ISqlForeignRowField<T7, T4, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t4(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable5(ISqlForeignRowField<T7, T5, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t5(foreignField.isEqualToTargetField());
	}

	public <T7> SqlSelect8_8<T0, T1, T2, T3, T4, T5, T6, T7, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable6(ISqlForeignRowField<T7, T6, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t7().t6(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser7_0<Boolean, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> on() {

		return new SqlTableChooser7_0<>(this, PartType.JOIN);
	}

	public SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T5, T6> condition) {

		return on().t5().t6(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser7_0<Boolean, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> where() {

		return new SqlTableChooser7_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser7_0<Object, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> groupBy() {

		return new SqlTableChooser7_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser7_0<Boolean, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> having() {

		return new SqlTableChooser7_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser7_0<Object, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser7_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser7_0<Object, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser7_0<Object, SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5, T6> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

