package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser3_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> extends SqlSelectBase8<V0, V1, V2, V3, V4, V5, V6, V7> implements ISqlSelectLastTableExtension<SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T2> {

	protected SqlSelect3_8(SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> other, ISqlExpression<V7> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> getThis() {

		return this;
	}

	// -------------------------------- join -------------------------------- //

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlTable<T3> table) {

		return _join(new SqlSelect4_8<>(other, expression), table, JoinType.JOIN);
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> join(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return joinOnTable2(foreignField);
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable0(ISqlForeignRowField<T0, T3, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable1(ISqlForeignRowField<T1, T3, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinOnTable2(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinReverse(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return joinReverseOnTable2(foreignField);
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable0(ISqlForeignRowField<T3, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t3().t0(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable1(ISqlForeignRowField<T3, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t3().t1(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinReverseOnTable2(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t3().t2(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlTable<T3> table) {

		return _join(new SqlSelect4_8<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeft(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return joinLeftOnTable2(foreignField);
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable0(ISqlForeignRowField<T0, T3, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable1(ISqlForeignRowField<T1, T3, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftOnTable2(ISqlForeignRowField<T2, T3, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t3(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverse(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return joinLeftReverseOnTable2(foreignField);
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable0(ISqlForeignRowField<T3, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t3().t0(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable1(ISqlForeignRowField<T3, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t3().t1(foreignField.isEqualToTargetField());
	}

	public <T3> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V7> joinLeftReverseOnTable2(ISqlForeignRowField<T3, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t3().t2(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser3_0<Boolean, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> on() {

		return new SqlTableChooser3_0<>(this, PartType.JOIN);
	}

	public SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7> on(ISqlBooleanExpression2<T1, T2> condition) {

		return on().t1().t2(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser3_0<Boolean, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> where() {

		return new SqlTableChooser3_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser3_0<Object, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> groupBy() {

		return new SqlTableChooser3_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser3_0<Boolean, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> having() {

		return new SqlTableChooser3_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser3_0<Object, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser3_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser3_0<Object, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser3_0<Object, SqlSelect3_8<T0, T1, T2, V0, V1, V2, V3, V4, V5, V6, V7>, T0, T1, T2> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //
}

