package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser6_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T5> {

	protected SqlSelect6_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T6> table) {

		return _join(new SqlSelect7_8<>(other, expression), table, JoinType.JOIN);
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return joinOnTable5(foreignField);
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable1(ISqlForeignRowField<T1, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable2(ISqlForeignRowField<T2, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable3(ISqlForeignRowField<T3, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable4(ISqlForeignRowField<T4, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t4().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable5(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t5().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return joinReverseOnTable5(foreignField);
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T6, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t0(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable1(ISqlForeignRowField<T6, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t1(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable2(ISqlForeignRowField<T6, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t2(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable3(ISqlForeignRowField<T6, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t3(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable4(ISqlForeignRowField<T6, T4, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t4(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable5(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return join(foreignField.getTable()).on().t6().t5(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T6> table) {

		return _join(new SqlSelect7_8<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return joinLeftOnTable5(foreignField);
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable1(ISqlForeignRowField<T1, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable2(ISqlForeignRowField<T2, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable3(ISqlForeignRowField<T3, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable4(ISqlForeignRowField<T4, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t4().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable5(ISqlForeignRowField<T5, T6, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t5().t6(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return joinLeftReverseOnTable5(foreignField);
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T6, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t0(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable1(ISqlForeignRowField<T6, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t1(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable2(ISqlForeignRowField<T6, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t2(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable3(ISqlForeignRowField<T6, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t3(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable4(ISqlForeignRowField<T6, T4, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t4(foreignField.isEqualToTargetField());
	}

	public <T6> SqlSelect7_8<T0, T1, T2, T3, T4, T5, T6, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable5(ISqlForeignRowField<T6, T5, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t6().t5(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser6_0<Boolean, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> on() {

		return new SqlTableChooser6_0<>(this, PartType.JOIN);
	}

	public SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T4, T5> condition) {

		return on().t4().t5(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser6_0<Boolean, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> where() {

		return new SqlTableChooser6_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser6_0<Object, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> groupBy() {

		return new SqlTableChooser6_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser6_0<Boolean, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> having() {

		return new SqlTableChooser6_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser6_0<Object, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser6_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser6_0<Object, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser6_0<Object, SqlSelect6_8<T0, T1, T2, T3, T4, T5, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2, T3, T4, T5> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

