package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.choosers.SqlTableChooser4_0;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.ISqlExpression0;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.expressions.ISqlExpression2;
import com.softicar.platform.db.sql.expressions.ISqlExpression3;
import com.softicar.platform.db.sql.expressions.ISqlExpression4;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.selects.SqlSelectCore.JoinType;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartType;

public final class SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6> extends SqlSelectBase7<V0, V1, V2, V3, V4, V5, V6> implements ISqlSelectLastTableExtension<SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T3> {

	protected SqlSelect4_7(SqlSelectBase6<V0, V1, V2, V3, V4, V5> other, ISqlExpression<V6> expression) {

		super(other, expression);
	}

	@Override
	public SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6> getThis() {

		return this;
	}

	// -------------------------------- select -------------------------------- //

	public SelectChooser0 select() {

		return new SelectChooser0();
	}

	public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> select(ISqlExpression0<V> expression) {

		return select().x(expression);
	}

	public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> select(ISqlExpression1<V, T3> expression) {

		return select().t3(expression);
	}

	// -------------------------------- join -------------------------------- //

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> join(ISqlTable<T4> table) {

		return _join(new SqlSelect5_7<>(other, expression), table, JoinType.JOIN);
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> join(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return joinOnTable3(foreignField);
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinOnTable0(ISqlForeignRowField<T0, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t0().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinOnTable1(ISqlForeignRowField<T1, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t1().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinOnTable2(ISqlForeignRowField<T2, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t2().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinOnTable3(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return join(foreignField.getTargetField().getTable()).on().t3().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinReverse(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return joinReverseOnTable3(foreignField);
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinReverseOnTable0(ISqlForeignRowField<T4, T0, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t0(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinReverseOnTable1(ISqlForeignRowField<T4, T1, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t1(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinReverseOnTable2(ISqlForeignRowField<T4, T2, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t2(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinReverseOnTable3(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return join(foreignField.getTable()).on().t4().t3(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeft(ISqlTable<T4> table) {

		return _join(new SqlSelect5_7<>(other, expression), table, JoinType.LEFT_JOIN);
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeft(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return joinLeftOnTable3(foreignField);
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftOnTable0(ISqlForeignRowField<T0, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t0().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftOnTable1(ISqlForeignRowField<T1, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t1().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftOnTable2(ISqlForeignRowField<T2, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t2().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftOnTable3(ISqlForeignRowField<T3, T4, ?> foreignField) {

		return joinLeft(foreignField.getTargetField().getTable()).on().t3().t4(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftReverse(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return joinLeftReverseOnTable3(foreignField);
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftReverseOnTable0(ISqlForeignRowField<T4, T0, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t0(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftReverseOnTable1(ISqlForeignRowField<T4, T1, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t1(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftReverseOnTable2(ISqlForeignRowField<T4, T2, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t2(foreignField.isEqualToTargetField());
	}

	public <T4> SqlSelect5_7<T0, T1, T2, T3, T4, V0, V1, V2, V3, V4, V5, V6> joinLeftReverseOnTable3(ISqlForeignRowField<T4, T3, ?> foreignField) {

		return joinLeft(foreignField.getTable()).on().t4().t3(foreignField.isEqualToTargetField());
	}

	// -------------------------------- on -------------------------------- //

	public SqlTableChooser4_0<Boolean, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> on() {

		return new SqlTableChooser4_0<>(this, PartType.JOIN);
	}

	public SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6> on(ISqlBooleanExpression2<T2, T3> condition) {

		return on().t2().t3(condition);
	}

	// -------------------------------- where -------------------------------- //

	public SqlTableChooser4_0<Boolean, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> where() {

		return new SqlTableChooser4_0<>(this, PartType.WHERE);
	}

	// -------------------------------- group by -------------------------------- //

	public SqlTableChooser4_0<Object, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> groupBy() {

		return new SqlTableChooser4_0<>(this, PartType.GROUP_BY);
	}

	// -------------------------------- having -------------------------------- //

	public SqlTableChooser4_0<Boolean, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> having() {

		return new SqlTableChooser4_0<>(this, PartType.HAVING);
	}

	// -------------------------------- order by -------------------------------- //

	public SqlTableChooser4_0<Object, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> orderBy(OrderDirection direction) {

		getCore().setOrderDirection(direction);
		return new SqlTableChooser4_0<>(this, PartType.ORDER_BY);
	}

	public SqlTableChooser4_0<Object, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> orderBy() {

		return orderBy(OrderDirection.ASCENDING);
	}

	public SqlTableChooser4_0<Object, SqlSelect4_7<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6>, T0, T1, T2, T3> orderByDesc() {

		return orderBy(OrderDirection.DESCENDING);
	}

	// -------------------------------- select chooser -------------------------------- //

	public final class SelectChooser0 extends SelectChooserBase {

		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t0(ISqlExpression1<V, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t1(ISqlExpression1<V, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t2(ISqlExpression1<V, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t3(ISqlExpression1<V, T3> expression) { return t3().x(expression); }
		public SelectChooser1<T0> t0() { return new SelectChooser1<>(this, 0); }
		public SelectChooser1<T1> t1() { return new SelectChooser1<>(this, 1); }
		public SelectChooser1<T2> t2() { return new SelectChooser1<>(this, 2); }
		public SelectChooser1<T3> t3() { return new SelectChooser1<>(this, 3); }
		<V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> x(ISqlExpression0<V> expression) { return addExpression(new SqlSelect4_8<>(SqlSelect4_7.this, expression), expression); }
		SelectChooser0() { /* non-public */ }
	}

	public final class SelectChooser1<E0> extends SelectChooserBase {

		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t0(ISqlExpression2<V, E0, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t1(ISqlExpression2<V, E0, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t2(ISqlExpression2<V, E0, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t3(ISqlExpression2<V, E0, T3> expression) { return t3().x(expression); }
		public SelectChooser2<E0, T0> t0() { return new SelectChooser2<>(this, 0); }
		public SelectChooser2<E0, T1> t1() { return new SelectChooser2<>(this, 1); }
		public SelectChooser2<E0, T2> t2() { return new SelectChooser2<>(this, 2); }
		public SelectChooser2<E0, T3> t3() { return new SelectChooser2<>(this, 3); }
		<V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> x(ISqlExpression1<V, E0> expression) { return addExpression(new SqlSelect4_8<>(SqlSelect4_7.this, expression), expression); }
		SelectChooser1(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser2<E0, E1> extends SelectChooserBase {

		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t0(ISqlExpression3<V, E0, E1, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t1(ISqlExpression3<V, E0, E1, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t2(ISqlExpression3<V, E0, E1, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t3(ISqlExpression3<V, E0, E1, T3> expression) { return t3().x(expression); }
		public SelectChooser3<E0, E1, T0> t0() { return new SelectChooser3<>(this, 0); }
		public SelectChooser3<E0, E1, T1> t1() { return new SelectChooser3<>(this, 1); }
		public SelectChooser3<E0, E1, T2> t2() { return new SelectChooser3<>(this, 2); }
		public SelectChooser3<E0, E1, T3> t3() { return new SelectChooser3<>(this, 3); }
		<V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> x(ISqlExpression2<V, E0, E1> expression) { return addExpression(new SqlSelect4_8<>(SqlSelect4_7.this, expression), expression); }
		SelectChooser2(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser3<E0, E1, E2> extends SelectChooserBase {

		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t0(ISqlExpression4<V, E0, E1, E2, T0> expression) { return t0().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t1(ISqlExpression4<V, E0, E1, E2, T1> expression) { return t1().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t2(ISqlExpression4<V, E0, E1, E2, T2> expression) { return t2().x(expression); }
		public <V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> t3(ISqlExpression4<V, E0, E1, E2, T3> expression) { return t3().x(expression); }
		public SelectChooser4<E0, E1, E2, T0> t0() { return new SelectChooser4<>(this, 0); }
		public SelectChooser4<E0, E1, E2, T1> t1() { return new SelectChooser4<>(this, 1); }
		public SelectChooser4<E0, E1, E2, T2> t2() { return new SelectChooser4<>(this, 2); }
		public SelectChooser4<E0, E1, E2, T3> t3() { return new SelectChooser4<>(this, 3); }
		<V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> x(ISqlExpression3<V, E0, E1, E2> expression) { return addExpression(new SqlSelect4_8<>(SqlSelect4_7.this, expression), expression); }
		SelectChooser3(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}

	public final class SelectChooser4<E0, E1, E2, E3> extends SelectChooserBase {

		<V> SqlSelect4_8<T0, T1, T2, T3, V0, V1, V2, V3, V4, V5, V6, V> x(ISqlExpression4<V, E0, E1, E2, E3> expression) { return addExpression(new SqlSelect4_8<>(SqlSelect4_7.this, expression), expression); }
		SelectChooser4(SelectChooserBase other, int tableIndex) { super(other, tableIndex); }
	}
}

